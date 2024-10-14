/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {
	ReactNode,
	memo,
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useRef,
} from 'react';
import {useSearchParams} from 'react-router-dom';
import {KeyedMutator} from 'swr';
import useUpdateUrlParams from '~/hooks/useUpdateUrlParams';

import ListViewContextProvider, {
	AppActions,
	InitialState as ListViewContextState,
	ListViewContext,
	ListViewContextProviderProps,
	ListViewTypes,
	Sort,
} from '../../context/ListViewContext';
import SearchBuilder from '../../core/SearchBuilder';
import {useFetch} from '../../hooks/useFetch';
import i18n from '../../i18n';
import {
	FilterSchema as FilterSchemaType,
	filterSchema as filterSchemas,
} from '../../schema/filter';
import {APIResponse, Results} from '../../services/rest';
import {SortDirection} from '../../types';
import {PAGINATION} from '../../util/constants';
import EmptyState from '../EmptyState';
import Loading from '../Loading';
import ManagementToolbar, {ManagementToolbarProps} from '../ManagementToolbar';
import Table, {TableProps} from '../Table';
import TableChart from '../TableChart';

type ChildrenOptions = {
	dispatch: React.Dispatch<AppActions>;
	listViewContext: ListViewContextState;
	mutate: KeyedMutator<any>;
};

export type ListViewProps<T = any> = {
	children?: (response: APIResponse, options: ChildrenOptions) => ReactNode;
	forceRefetch?: number;
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
	matrixProps?: {title?: string};
	normalizers?: {
		onSelectRow?: (item: T) => number | number[];
	};
	onContextChange?: (context: ListViewContextState) => void;
	pagination?: {
		displayTop?: boolean;
	};
	resource: string;
	tableProps: {visible?: boolean} & Omit<
		TableProps,
		| 'items'
		| 'mutate'
		| 'normalizers'
		| 'onSelectAllRows'
		| 'onSelectRow'
		| 'onSort'
	>;
	transformData?: (data: T) => APIResponse<T>;
	variables?: any;
};

const noop = (value: any) => {
	if (Array.isArray(value)) {
		return value;
	}

	return value.id;
};

const ListView: React.FC<ListViewProps> = ({
	children,
	forceRefetch,
	managementToolbarProps: {
		customFilterFields,
		visible: managementToolbarVisible = true,
		...managementToolbarProps
	} = {},
	matrixProps: {title} = {},
	normalizers = {onSelectRow: noop},
	onContextChange,
	pagination = {displayTop: true},
	resource,
	tableProps: {visible: tableVisible = true, ...tableProps},
	transformData,
	variables,
}) => {
	const [listViewContext, dispatch] = useContext(ListViewContext);
	const updateUrlParams = useUpdateUrlParams();

	const [searchParams] = useSearchParams();

	const currentPage = searchParams.get('page');

	const currentPageSize = searchParams.get('pageSize');

	let isRowSelectable = false;

	const onSelectRowNormalizer = useMemo(
		() => normalizers.onSelectRow ?? noop,
		[normalizers.onSelectRow]
	);

	const {
		columns: columnsContext,
		filters,
		search,
		selectedRows,
		sort,
	} = listViewContext;

	const filterSchemaName = managementToolbarProps.filterSchema ?? '';

	const filterSchema = (filterSchemas as any)[
		filterSchemaName
	] as FilterSchemaType;

	const onContextChangeRef = useRef<
		((context: ListViewContextState) => void) | undefined
	>(onContextChange);

	const onApplyFilterMemo = useMemo(
		() => filterSchema?.onApply?.bind(filterSchema),
		[filterSchema]
	);

	const filterVariables = useMemo(
		() => ({
			appliedFilter: filters.filter,
			defaultFilter: variables?.filter,
			filterSchema,
		}),
		[filters, variables?.filter, filterSchema]
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
		if (!managementToolbarProps.applyFilters) {
			return;
		}

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
	}, [
		filterSchema?.fields,
		filterVariables,
		managementToolbarProps.applyFilters,
		onApplyFilterMemo,
	]);

	const getURLSearchParams = useCallback(
		() => ({
			...filter,
			forceRefetch,
			page:
				managementToolbarProps.applyFilters && currentPage
					? Number(currentPage)
					: listViewContext.page,
			pageSize:
				managementToolbarProps.applyFilters && currentPageSize
					? Number(currentPageSize)
					: listViewContext.pageSize,
			search,
			sort: buildSort(sort),
		}),
		[
			currentPage,
			currentPageSize,
			filter,
			forceRefetch,
			listViewContext.page,
			listViewContext.pageSize,
			managementToolbarProps.applyFilters,
			search,
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
		transformData,
	});

	const {
		actions = {},
		items = [],
		lastPage = 1,
		page = 1,
		pageSize,
		results,
		totalCount = 0,
	} = response || {};

	const matrixData = useMemo(
		() => (results && results[0][title as keyof Results]) || [],
		[results, title]
	);

	const itemsMemoized = useMemo(
		() => (results ? matrixData : items),
		[items, matrixData, results]
	);

	const isCompareRunsMatrix = title === 'Runs';

	const columns = useMemo(
		() =>
			tableProps.columns?.filter(({key}) => {
				const columns = columnsContext || {};

				if (columns[key] === undefined) {
					return true;
				}

				return columns[key];
			}),
		[columnsContext, tableProps.columns]
	);

	const onSelectRow = useCallback(
		(row: any) => {
			dispatch({
				payload: onSelectRowNormalizer(row),
				type: ListViewTypes.SET_CHECKED_ROW,
			});
		},
		[dispatch, onSelectRowNormalizer]
	);

	const onSort = useCallback(
		(key: string, direction: SortDirection) => {
			dispatch({
				payload: {direction, key},
				type: ListViewTypes.SET_SORT,
			});
		},
		[dispatch]
	);

	const onSelectAllRows = useCallback(() => {
		onSelectRow(itemsMemoized.map((item) => onSelectRowNormalizer(item)));
	}, [itemsMemoized, onSelectRow, onSelectRowNormalizer]);

	useEffect(() => {
		const shouldCurrentPageBeChanged =
			!loading &&
			!itemsMemoized.length &&
			lastPage > 1 &&
			page === lastPage;

		if (shouldCurrentPageBeChanged) {
			dispatch({payload: page - 1, type: ListViewTypes.SET_PAGE});
		}
	}, [dispatch, itemsMemoized.length, lastPage, loading, page]);

	const listViewContextString = JSON.stringify(listViewContext);

	useEffect(() => {
		if (onContextChangeRef.current) {
			onContextChangeRef.current(JSON.parse(listViewContextString));
		}
	}, [listViewContextString]);

	useEffect(() => {
		if (customFilterFields) {
			dispatch({
				payload: {customFilterFields},
				type: ListViewTypes.SET_CUSTOM_FILTER_FIELDS,
			});
		}
	}, [customFilterFields, dispatch]);

	if (tableProps.rowSelectable) {
		isRowSelectable = itemsMemoized.every((item) =>
			selectedRows.includes(onSelectRowNormalizer(item))
		);
	}

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
					tableProps={tableProps}
					totalItems={
						matrixData && !isCompareRunsMatrix
							? Object.keys(itemsMemoized).length
							: itemsMemoized.length
					}
				/>
			)}

			{!isCompareRunsMatrix &&
				!Object.keys(itemsMemoized).length &&
				!itemsMemoized.length && (
					<EmptyState
						description={error?.message}
						type={error ? 'EMPTY_SEARCH' : 'EMPTY_STATE'}
					/>
				)}

			{children &&
				children(response as APIResponse, {
					dispatch,
					listViewContext,
					mutate,
				})}

			{!!items.length && !isCompareRunsMatrix ? (
				<>
					{pagination?.displayTop && (
						<div className="mt-4">{Pagination}</div>
					)}

					{tableVisible && (
						<Table
							{...tableProps}
							allRowsChecked={listViewContext.checkAll}
							columns={columns}
							items={itemsMemoized}
							mutate={mutate}
							normalizers={{
								onSelectRow: onSelectRowNormalizer,
							}}
							onSelectAllRows={onSelectAllRows}
							onSelectRow={onSelectRow}
							onSort={onSort}
							selectedRows={selectedRows}
							sort={sort}
						/>
					)}

					{Pagination}
				</>
			) : null}

			{!items.length &&
				(results && isCompareRunsMatrix ? (
					<ClayLayout.Col lg={12} md={12}>
						<TableChart matrixData={matrixData} title={title} />
					</ClayLayout.Col>
				) : (
					<div className="d-flex flex-wrap">
						{Object.entries(itemsMemoized)
							.sort(([nameA], [nameB]) =>
								nameA.localeCompare(nameB)
							)
							.map(([name, data], index) => (
								<div className="my-4" key={index}>
									<TableChart
										fieldName={title}
										matrixData={data}
										title={name}
									/>
								</div>
							))}
					</div>
				))}
		</>
	);
};

const ListViewMemoized = memo(ListView);

const ListViewWithContext: React.FC<
	ListViewProps & {
		initialContext?: ListViewContextProviderProps;
	}
> = ({initialContext, ...otherProps}) => (
	<ListViewContextProvider {...initialContext} id={otherProps.resource}>
		<ListViewMemoized {...otherProps} />
	</ListViewContextProvider>
);

export default ListViewWithContext;
