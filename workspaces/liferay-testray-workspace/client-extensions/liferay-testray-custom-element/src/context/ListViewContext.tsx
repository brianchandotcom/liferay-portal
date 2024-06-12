/* eslint-disable no-case-declarations */

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode, createContext, useEffect, useReducer} from 'react';
import {useSearchParams} from 'react-router-dom';
import TestrayStorage, {STORAGE_KEYS} from '~/core/Storage';
import useFilterUrlParams from '~/hooks/useFilterUrlParams';
import useStorage from '~/hooks/useStorage';
import {ActionMap, SortDirection, SortOption} from '~/types';
import {getUniqueList, safeJSONParse} from '~/util';
import {PAGINATION_DELTA} from '~/util/constants';
import {CONSENT_TYPE} from '~/util/enum';
import isDeepEqual from '~/util/object';

const testrayStorage = TestrayStorage.getInstance().getStorage('persisted');

export type Entry = {
	label: string;
	name: string;
	value: number | string | null;
};

export type Sort = {
	direction: SortDirection;
	key: string;
};

export type ListViewFilter = {
	entries: Entry[];
	filter: {
		[key: string]: string;
	};
};

type ListViewColumns = {
	[key: string]: boolean;
};

export type InitialState = {
	appliedFilter: boolean;
	checkAll: boolean;
	columns: ListViewColumns;
	columnsFixed: string[];
	customFilterFields: {[key: string]: string};
	filters: ListViewFilter;
	id: string;
	page: number;
	pageSize: number;
	pin: boolean;
	search: string;
	selectedRows: number[];
	sort: Sort | Sort[];
};

const initialState: InitialState = {
	appliedFilter: false,
	checkAll: false,
	columns: {},
	columnsFixed: [],
	customFilterFields: {key: ''},
	filters: {
		entries: [],
		filter: {},
	},
	id: '',
	page: 1,
	pageSize: PAGINATION_DELTA[0],
	pin: false,
	search: '',
	selectedRows: [],
	sort: {direction: SortOption.ASC, key: ''},
};

export enum ListViewTypes {
	SET_APPLY_FILTERS = 'SET_APPLY_FILTERS',
	SET_CHECKED_ALL_ROWS = 'SET_CHECKED_ALL_ROWS',
	SET_CHECKED_ROW = 'SET_CHECKED_ROW',
	SET_CLEAR = 'SET_CLEAR',
	SET_CLEAR_CHECKED_ROW = 'SET_CLEAR_CHECKED_ROW',
	SET_COLUMNS = 'SET_COLUMNS',
	SET_CUSTOM_FILTER_FIELDS = 'SET_CUSTOM_FILTER_FIELD',
	SET_FILTERS = 'SET_FILTERS',
	SET_PAGE = 'SET_PAGE',
	SET_PAGE_SIZE = 'SET_PAGE_SIZE',
	SET_PIN = 'SET_PIN',
	SET_REMOVE_FILTER = 'SET_REMOVE_FILTER',
	SET_SEARCH = 'SET_SEARCH',
	SET_SORT = 'SET_SORT',
}

type ListViewPayload = {
	[ListViewTypes.SET_APPLY_FILTERS]: boolean;
	[ListViewTypes.SET_CHECKED_ALL_ROWS]: boolean;
	[ListViewTypes.SET_CHECKED_ROW]: number | number[];
	[ListViewTypes.SET_CLEAR]: null;
	[ListViewTypes.SET_CLEAR_CHECKED_ROW]: [];
	[ListViewTypes.SET_COLUMNS]: {columns: any};
	[ListViewTypes.SET_CUSTOM_FILTER_FIELDS]: {customFilterFields: any};
	[ListViewTypes.SET_FILTERS]: {filters?: any; pin?: any};
	[ListViewTypes.SET_PAGE]: number;
	[ListViewTypes.SET_PAGE_SIZE]: number;
	[ListViewTypes.SET_PIN]: string;
	[ListViewTypes.SET_REMOVE_FILTER]: string;
	[ListViewTypes.SET_SEARCH]: string;
	[ListViewTypes.SET_SORT]: Sort;
};

export type AppActions =
	ActionMap<ListViewPayload>[keyof ActionMap<ListViewPayload>];

export const ListViewContext = createContext<
	[InitialState, (param: AppActions) => void]
>([initialState, () => null]);

const getPinState = (state: InitialState, newFilter: ListViewFilter) => {
	const appliedFilters = testrayStorage.getItem(
		(STORAGE_KEYS.LIST_VIEW_PIN + state.id) as STORAGE_KEYS,
		CONSENT_TYPE.NECESSARY
	);

	const appliedFilterJSON = safeJSONParse(appliedFilters, {});

	return isDeepEqual(newFilter, appliedFilterJSON);
};

const reducer = (state: InitialState, action: AppActions) => {
	switch (action.type) {
		case ListViewTypes.SET_APPLY_FILTERS:
			return {
				...state,
				appliedFilter: action.payload,
			};

		case ListViewTypes.SET_CHECKED_ROW:
			const rowIds = action.payload;

			let selectedRows = [...state.selectedRows];

			if (Array.isArray(rowIds)) {
				selectedRows = state.checkAll
					? selectedRows.filter((row) => !rowIds.includes(row))
					: getUniqueList([...rowIds, ...selectedRows]);
			}
			else {
				const rowAlreadyInserted = state.selectedRows.includes(
					rowIds as number
				);

				rowAlreadyInserted
					? (selectedRows = selectedRows.filter(
							(row) => row !== rowIds
						))
					: (selectedRows = [...selectedRows, rowIds as number]);
			}

			return {
				...state,
				selectedRows,
			};

		case ListViewTypes.SET_CHECKED_ALL_ROWS:
			return {
				...state,
				checkAll: action.payload,
			};

		case ListViewTypes.SET_CLEAR:
			return {
				...state,
				filters: initialState.filters,
				search: '',
			};

		case ListViewTypes.SET_CLEAR_CHECKED_ROW:
			return {
				...state,
				selectedRows: initialState.selectedRows,
			};

		case ListViewTypes.SET_COLUMNS:
			const columns = action.payload.columns;
			const storageColumnsName =
				STORAGE_KEYS.LIST_VIEW_COLUMNS + state.id;

			testrayStorage.setItem(
				storageColumnsName,
				JSON.stringify(columns),
				CONSENT_TYPE.NECESSARY
			);

			return {
				...state,
				columns,
			};

		case ListViewTypes.SET_CUSTOM_FILTER_FIELDS:
			return {
				...state,
				customFilterFields: action.payload.customFilterFields,
			};

		case ListViewTypes.SET_PAGE:
			return {
				...state,
				page: action.payload,
			};

		case ListViewTypes.SET_PAGE_SIZE:
			return {
				...state,
				page: 1,
				pageSize: action.payload,
			};

		case ListViewTypes.SET_PIN:
			if (!state.filters.entries.length) {
				return state;
			}

			const pin = !state.pin;

			const storageName = STORAGE_KEYS.LIST_VIEW_PIN + state.id;

			const schemaName = STORAGE_KEYS.FILTER_SCHEMA + state.id;

			if (pin) {
				testrayStorage.setItem(
					storageName,
					JSON.stringify(state.filters),
					CONSENT_TYPE.NECESSARY
				);
				testrayStorage.setItem(
					STORAGE_KEYS.FILTER_SCHEMA + state.id,
					JSON.stringify(action.payload),
					CONSENT_TYPE.NECESSARY
				);
			}
			else {
				testrayStorage.removeItem(storageName);
				testrayStorage.removeItem(schemaName);
			}

			return {
				...state,
				pin,
			};

		case ListViewTypes.SET_REMOVE_FILTER: {
			const filterKey = action.payload;
			const updatedFilters = {...state.filters};

			delete updatedFilters.filter[filterKey];

			const filterEntries = updatedFilters.entries.filter(
				({name}) => name !== filterKey
			);

			const filters = {
				entries: filterEntries,
				filter: updatedFilters.filter,
			};

			return {
				...state,
				filters,
				pin: getPinState(state, filters),
			};
		}

		case ListViewTypes.SET_SEARCH: {
			return {
				...state,
				page: 1,
				search: action.payload,
			};
		}
		case ListViewTypes.SET_SORT:
			return {
				...state,
				sort: action.payload,
			};

		case ListViewTypes.SET_FILTERS: {
			state.pin = getPinState(state, action.payload.filters);

			return {
				...state,
				filters: action.payload.filters || state.filters,
				page: 1,
			};
		}

		default:
			return state;
	}
};

export type ListViewContextProviderProps = Partial<InitialState>;

type Option = {label: string; value: string};

const ListViewContextProvider: React.FC<
	ListViewContextProviderProps & {children: ReactNode; id: string}
> = ({children, id, ...initialStateProps}) => {
	const [searchParams, setSearchParams] = useSearchParams();

	const filter = searchParams.get('filter');

	const [columnsStorage] = useStorage<ListViewColumns>(
		(STORAGE_KEYS.LIST_VIEW_COLUMNS + id) as STORAGE_KEYS,
		{consentType: CONSENT_TYPE.NECESSARY, storageType: 'persisted'}
	);
	const [filterPinnedStorage] = useStorage<ListViewFilter>(
		(STORAGE_KEYS.LIST_VIEW_PIN + id) as STORAGE_KEYS,
		{consentType: CONSENT_TYPE.NECESSARY, storageType: 'persisted'}
	);

	const [filterSchemaStorage] = useStorage(
		(STORAGE_KEYS.FILTER_SCHEMA + id) as STORAGE_KEYS,
		{consentType: CONSENT_TYPE.NECESSARY, storageType: 'persisted'}
	);

	useEffect(() => {
		const filters =
			filterPinnedStorage?.filter &&
			Object.keys(filterPinnedStorage?.filter).map((key) => {
				if (Array.isArray(filterPinnedStorage?.filter[key])) {
					return {
						name: key,
						value: (filterPinnedStorage?.filter as any)[key].map(
							(options: Option) => options.value || options
						),
					};
				}
				else {
					return {
						name: key,
						value: filterPinnedStorage?.filter[key],
					};
				}
			});

		const formattedFilter = filters?.reduce(
			(previousValue, currentValue) => {
				return {
					...previousValue,
					[currentValue.name]: currentValue.value,
				};
			},
			{}
		);

		if (!filter && filterSchemaStorage && formattedFilter) {
			setSearchParams(
				new URLSearchParams({
					filter: JSON.stringify(formattedFilter),
					filterSchema: filterSchemaStorage as string,
					page: '1',
					pageSize: '20',
				})
			);
		}
	}, [
		filter,
		filterPinnedStorage?.filter,
		filterSchemaStorage,
		setSearchParams,
	]);

	const [state, dispatch] = useReducer(reducer, {
		...initialState,
		...initialStateProps,
		...(filterPinnedStorage && {
			filters: filterPinnedStorage,
			pin: !!filterPinnedStorage.entries.length,
		}),

		...(columnsStorage && {columns: columnsStorage}),
		id,
	});

	const {filterInitialContext} = useFilterUrlParams(state.customFilterFields);

	return (
		<ListViewContext.Provider
			value={[
				{
					...state,
					...(filter && {
						filters: filterInitialContext as ListViewFilter,
					}),
				},
				dispatch,
			]}
		>
			{children}
		</ListViewContext.Provider>
	);
};

export default ListViewContextProvider;
