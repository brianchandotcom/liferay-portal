/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useMemo, useState} from 'react';
import useSWRInfinite from 'swr/infinite';

import useDebounce from './useDebounce';

const getKey =
	(search: string, searchKey: string) =>
	(pageIndex: number, previousData: any) => {
		if (previousData && previousData.lastPage === pageIndex) {
			return null;
		}

		return {
			key: `/infinite-search/${pageIndex}`,
			pageIndex,
			search,
			searchKey,
		};
	};

export type FetcherParams = {pageIndex: string; search: string};

const useInfiniteSearch = <T>(
	searchId: string,
	fetcher: (params: FetcherParams) => Promise<APIResponse<T>>
) => {
	const [search, setSearch] = useState('');
	const debouncedSearch = useDebounce(search);

	const swrInfinite = useSWRInfinite(
		getKey(debouncedSearch, searchId),
		fetcher,
		{
			keepPreviousData: true,
			persistSize: false,
		}
	);

	const setSize = swrInfinite.setSize;

	useEffect(() => {
		setSize(1);
	}, [setSize, debouncedSearch]);

	const fetchMore = useCallback(() => {
		swrInfinite.setSize((size) => size + 1);
	}, [swrInfinite]);

	const lastcall = swrInfinite?.data?.at(-1) || {
		lastPage: -1,
		page: -1,
		totalCount: -1,
	};

	const allowFetching = lastcall?.page !== lastcall?.lastPage;

	const infiniteSearch = useMemo(
		() => ({
			allowFetching,
			displaySearch: lastcall.totalCount > 20,
			fetchMore,
			search,
			setSearch,
			totalCount: lastcall.totalCount,
		}),
		[allowFetching, lastcall.totalCount, fetchMore, search, setSearch]
	);

	return {
		infiniteSearch,
		items: swrInfinite?.data?.map(({items}) => items).flat() ?? [],
		swrInfinite,
	};
};

export default useInfiniteSearch;
