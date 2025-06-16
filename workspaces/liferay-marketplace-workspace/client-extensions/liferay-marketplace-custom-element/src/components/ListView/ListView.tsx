/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {
	ComponentProps,
	memo,
	ReactNode,
	useCallback,
	useContext,
	useEffect,
	useMemo,
} from 'react';
import {KeyedMutator} from 'swr';

import i18n from '../../i18n';
import {PAGINATION, SortDirection} from '../../utils/constants';
import {
	FilterSchema as FilterSchemaType,
	filterSchema as filterSchemas,
} from '../../schema/filters';
import EmptyState from '../EmptyState';
import Loading from '../Loading';
import ManagementToolbar, {
	ManagementToolbarProps,
} from './components/ManagementToolbar';
import Table, {TableProps} from './components/Table';
import ListViewContextProvider, {
	AppActions,
	InitialState as ListViewContextState,
	ListViewContext,
	ListViewContextProviderProps,
	ListViewTypes,
	Sort,
} from './hooks/ListViewContext';
import {useFetch} from '../../hooks/useFetch';
import SearchBuilder from '../../core/SearchBuilder';
import {useSearchParams} from 'react-router-dom';
import useUpdateUrlParams from './hooks/useUpdateUrlParams';

type ChildrenOptions = {
	dispatch: React.Dispatch<AppActions>;
	listViewContext: ListViewContextState;
	mutate: KeyedMutator<APIResponse<any>>;
};

export type ListViewProps<T extends Record<string, any>> = {
	children?: (
		response: APIResponse<T>,
		options: ChildrenOptions
	) => ReactNode;

	defaultFilters?: {filter: string};

	emptyStateProps?: ComponentProps<typeof EmptyState>;

	/**
	 * The key of SWR Cache for the list view.
	 * It must be provided to avoid cache collisions.
	 *
	 * @default 'listView:{id}?page={page}&pageSize={pageSize}'
	 */
	id: string;

	initialContext?: ListViewContextProviderProps;

	managementToolbarProps?: {
		customFilterFields?: {[key: string]: string};
		visible?: boolean;
	} & Omit<
		ManagementToolbarProps,
		| 'actions'
		| 'tableProps'
		| 'totalItems'
		| 'onSelectAllRows'
		| 'rowSelectable'
	>;

	/**
	 * The options for the pagination.
	 *
	 * @default {displayType: 'auto'}
	 */
	paginationOptions?: {
		displayType: 'always' | 'auto' | 'never';
	};

	resource: string;

	tableProps: Omit<
		TableProps<T>,
		'items' | 'mutate' | 'onSelectAllRows' | 'onSort'
	>;
};

const ListView = <T extends Record<string, any>>({
	children,
	emptyStateProps,
	managementToolbarProps: {
		customFilterFields,
		visible: managementToolbarVisible = false,
		...managementToolbarProps
	} = {},
	paginationOptions = {displayType: 'auto'},
	resource,
	tableProps,
	defaultFilters,
}: ListViewProps<T>) => {
	const [listViewContext, dispatch] = useContext(ListViewContext);
	const updateUrlParams = useUpdateUrlParams();

	const [searchParams] = useSearchParams();

	const currentPage = searchParams.get('page');

	const currentPageSize = searchParams.get('pageSize');

	let isRowSelectable = false;

	const {filters, keywords, sort} = listViewContext;

	const filterSchemaName = managementToolbarProps.filterSchema ?? '';

	const filterSchema = (filterSchemas as any)[
		filterSchemaName
	] as FilterSchemaType;

	const onApplyFilterMemo = useMemo(
		() => filterSchema?.onApply?.bind(filterSchema),
		[filterSchema]
	);

	const filterVariables = useMemo(
		() => ({
			appliedFilter: filters.filter,
			defaultFilter: defaultFilters?.filter,
			filterSchema,
		}),
		[filters, defaultFilters?.filter, filterSchema]
	);

	const buildSort = (sort: Sort | Sort[]) => {
		if (Array.isArray(sort)) {
			return sort
				.reduce(
					(prevSort, newSort) =>
						prevSort +
						`${newSort.key}:${newSort.direction.toLowerCase()},`,
					''
				)
				.slice(0, -1);
		}

		return sort.key ? `${sort.key}:${sort.direction.toLowerCase()}` : '';
	};

	const filter = useMemo(() => {
		const appliedFilters: {[key: string]: string} = {
			...filterVariables.appliedFilter,
		};

		const filters: {[key: string]: string | undefined | boolean} = {};

		Object.entries(appliedFilters).forEach(([key, value]) => {
			const matchingField = filterSchema.fields.find(
				(field) => field.name === key && field.isCustomFilter
			);

			if (matchingField) {
				if (
					value.includes(`No ${matchingField.label}`) &&
					!matchingField.requestOperator
				) {
					const newKey = `no${key.charAt(0).toUpperCase() + key.slice(1)}`;

					filters[newKey] = true;
				}
				else {
					filters[key] = SearchBuilder.createCustomFilter(
						matchingField,
						value
					);
				}
				delete appliedFilters[key];
			}
		});

		const filterVariablesCopy = {
			...filterVariables,
			appliedFilter: {...appliedFilters},
		};

		const baseFilter = onApplyFilterMemo
			? onApplyFilterMemo(filterVariablesCopy)
			: SearchBuilder.createFilter(filterVariablesCopy) || '';

		const filter = {filter: baseFilter, ...filters};

		return filter;
	}, [filterSchema?.fields, filterVariables, onApplyFilterMemo]);

	const getURLSearchParams = useCallback(
		() => ({
			...filter,
			page:
				managementToolbarProps.applyFilters && currentPage
					? Number(currentPage)
					: listViewContext.page,
			pageSize:
				managementToolbarProps.applyFilters && currentPageSize
					? Number(currentPageSize)
					: listViewContext.pageSize,
			search: keywords,
			sort: buildSort(sort),
		}),
		[
			currentPage,
			currentPageSize,
			filter,
			listViewContext.page,
			listViewContext.pageSize,
			managementToolbarProps.applyFilters,
			keywords,
			sort,
		]
	);

	const {
		data: response,
		error,
		isValidating,
		loading,
		mutate,
	} = useFetch(resource, {
		params: getURLSearchParams(),
	});

	const {
		actions = {},
		items = [],
		lastPage = 1,
		page = 1,
		pageSize,
		totalCount = 0,
	} = response || {};

	const onSort = useCallback(
		(key: string, direction: SortDirection) => {
			dispatch({
				payload: {direction, key},
				type: ListViewTypes.SET_SORT,
			});
		},
		[dispatch]
	);

	useEffect(() => {
		const shouldCurrentPageBeChanged =
			!loading && lastPage > 1 && page === lastPage;

		if (shouldCurrentPageBeChanged) {
			dispatch({payload: page - 1, type: ListViewTypes.SET_PAGE});
		}
	}, [dispatch, lastPage, loading, page]);

	useEffect(() => {
		if (customFilterFields) {
			dispatch({
				payload: {customFilterFields},
				type: ListViewTypes.SET_CUSTOM_FILTER_FIELDS,
			});
		}
	}, [customFilterFields, dispatch]);

	useEffect(() => {
		dispatch({
			payload: isRowSelectable,
			type: ListViewTypes.SET_CHECKED_ALL_ROWS,
		});
	}, [dispatch, isRowSelectable]);

	useEffect(() => {
		if (managementToolbarProps.applyFilters) {
			dispatch({
				payload: true,
				type: ListViewTypes.SET_APPLY_FILTERS,
			});
		}
	}, [dispatch, managementToolbarProps.applyFilters]);

	if (loading || (isValidating && searchParams.get('filter'))) {
		return <Loading />;
	}

	const Pagination = (
		<ClayPaginationBarWithBasicItems
			activeDelta={pageSize}
			activePage={page}
			deltas={PAGINATION.delta.map((label) => ({label}))}
			ellipsisBuffer={PAGINATION.ellipsisBuffer}
			labels={{
				paginationResults: i18n.translate('showing-x-to-x-of-x'),
				perPageItems: i18n.translate('x-items'),
				selectPerPageItems: i18n.translate('x-items'),
			}}
			onDeltaChange={(delta) => {
				if (managementToolbarProps.applyFilters) {
					updateUrlParams({pageSize: delta});
				}

				dispatch({payload: delta, type: ListViewTypes.SET_PAGE_SIZE});
			}}
			onPageChange={(page) => {
				if (managementToolbarProps.applyFilters) {
					updateUrlParams({page});
				}

				dispatch({payload: page, type: ListViewTypes.SET_PAGE});
			}}
			totalItems={totalCount || 0}
		/>
	);

	return (
		<>
			{managementToolbarVisible && (
				<ManagementToolbar
					{...managementToolbarProps}
					actions={actions}
					customFilterFields={customFilterFields}
					totalItems={totalCount}
				/>
			)}

			{!items.length && (
				<EmptyState
					description={error?.message}
					type={error ? 'EMPTY_SEARCH' : 'EMPTY_STATE'}
					{...emptyStateProps}
				/>
			)}
			{!!items.length && (
				<>
					<Table
						{...tableProps}
						items={items}
						mutate={mutate}
						onSort={onSort}
						sort={sort}
					/>

					{/* {paginationOptions.displayType === 'always' && Pagination} */}

					{children &&
						children(response!, {
							dispatch,
							listViewContext,
							mutate,
						})}
				</>
			)}
		</>
	);
};

const ListViewWithContext = <T extends Record<string, any>>({
	initialContext,
	...otherProps
}: ListViewProps<T>): React.ReactElement => (
	<ListViewContextProvider {...initialContext} id={otherProps.id}>
		<ListView {...otherProps} />
	</ListViewContextProvider>
);

export default ListViewWithContext;
