import {NetworkState} from 'shared/util/constants';
import {useCallback, useEffect, useRef, useState} from 'react';
import {useDebounce} from 'shared/hooks/useDebounce';

export const DEFAULT_PAGE_SIZE = 20;

export interface IPaginatedDataSourceParams {
	page: number;
	pageSize: number;
	query: string;
}

export interface IPaginatedDataSourceResult<T = string> {
	items: T[];
	total: number;
}

export type PaginatedDataSourceFn<T = string> = (
	params: IPaginatedDataSourceParams
) => Promise<IPaginatedDataSourceResult<T>>;

/**
 * Loads a list page by page. The first page is requested whenever the query
 * or `dataSourceKey` changes, and `onLoadMore` (undefined once the list is
 * complete) appends the next one.
 */
export const usePaginatedRequest = <T>({
	dataSourceFn,
	dataSourceKey,
	debounceDelay,
	pageSize,
	query,
	skip = false,
}: {
	dataSourceFn?: PaginatedDataSourceFn<T>;
	dataSourceKey?: string;
	debounceDelay: number;
	pageSize: number;
	query: string;
	skip?: boolean;
}) => {
	const [state, setState] = useState({
		error: false,
		items: [] as T[],
		lastPageFull: false,
		networkState: NetworkState.Unused,
		page: 0,
		total: 0,
	});

	const dataSourceFnRef = useRef(dataSourceFn);
	const requestIdRef = useRef(0);
	const loadingRef = useRef(false);

	dataSourceFnRef.current = dataSourceFn;

	const debounced = useDebounce({dataSourceKey, query}, debounceDelay);

	const fetchPage = useCallback(
		(page: number) => {
			const requestId = ++requestIdRef.current;

			loadingRef.current = true;

			setState((prevState) => ({
				...prevState,
				networkState:
					page === 1 ? NetworkState.Loading : NetworkState.Refetch,
			}));

			return dataSourceFnRef.current!({
				page,
				pageSize,
				query: debounced.query,
			})
				.then(({items, total}) => {
					if (requestId !== requestIdRef.current) {
						return;
					}

					loadingRef.current = false;

					setState((prevState) => ({
						error: false,
						items:
							page === 1 ? items : [...prevState.items, ...items],
						lastPageFull: items.length >= pageSize,
						networkState: NetworkState.Unused,
						page,
						total,
					}));
				})
				.catch(() => {
					if (requestId !== requestIdRef.current) {
						return;
					}

					loadingRef.current = false;

					setState((prevState) => ({
						...prevState,
						error: true,
						networkState: NetworkState.Unused,
					}));
				});
		},
		[debounced.query, pageSize]
	);

	useEffect(() => {
		if (!skip && dataSourceFn) {
			fetchPage(1);
		}
	}, [debounced.dataSourceKey, fetchPage, skip]);

	// Endpoints that group values can report a total above the distinct
	// values they return, so a short page also ends the list.

	const hasMore =
		!state.error && state.lastPageFull && state.items.length < state.total;

	const onLoadMore = useCallback(() => {
		if (loadingRef.current) {
			return null;
		}

		return fetchPage(state.page + 1);
	}, [fetchPage, state.page]);

	return {
		items: state.items,
		loaded: state.page > 0 || state.error,
		networkState: state.networkState,
		onLoadMore: hasMore ? onLoadMore : undefined,
	};
};
