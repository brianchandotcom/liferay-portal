/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import useSWR, {SWRConfiguration} from 'swr';

type APIParametersOptions = {
	aggregationTerms?: string;
	customParams?: {[key: string]: unknown};
	fields?: string;
	filter?: string;
	nestedFields?: string;
	nestedFieldsDepth?: number | string;
	page?: number | string;
	pageSize?: number | string;
	sort?: string;
};

type FetchOptions<Data> = {
	params?: APIParametersOptions;
	swrConfig?: SWRConfiguration & {shouldFetch?: boolean | string | number};
	transformData?: (data: Data) => Data;
};

function getPageParameter(
	parameters: APIParametersOptions = {},
	baseURL?: string
) {
	const getBaseSearchParams = (resource?: string) => {
		if (resource && resource.includes('?')) {
			return resource.slice(resource.indexOf('?'));
		}
	};

	const searchParams = new URLSearchParams(getBaseSearchParams(baseURL));

	if (parameters.customParams) {
		parameters = {
			...parameters,
			...parameters.customParams,
		};

		delete parameters.customParams;
	}

	for (const key in parameters) {
		const value = (parameters as any)[key] as string | number | undefined;

		if (value) {
			searchParams.set(key, value.toString());
		}
	}

	return searchParams.toString();
}

const getBaseURL = (url: string | null, options?: APIParametersOptions) => {
	if (!url) {
		return null;
	}

	const searchParams = getPageParameter(options, url);

	let baseURL = url;

	if (url.includes('?')) {
		baseURL = url.slice(0, url.indexOf('?'));
	}

	if (searchParams.length) {
		baseURL += `?${searchParams}`;
	}

	return baseURL;
};

export function useFetch<Data = any, Error = any>(
	url: string | null,
	fetchParameters?: FetchOptions<Data>
) {
	const {params, swrConfig, transformData} = fetchParameters ?? {};

	const shouldFetch = swrConfig?.shouldFetch ?? true;

	const {data, error, isLoading, isValidating, mutate} = useSWR<Data, Error>(
		() => (shouldFetch ? getBaseURL(url, params) : null),
		swrConfig
	);

	const memoizedData = useMemo(() => {
		if (data && transformData) {
			return transformData(data || ({} as Data));
		}

		return data;
	}, [data, transformData]);

	return {
		called: data && url,
		data: memoizedData,
		error,
		isValidating,
		loading: isLoading,
		mutate,
		revalidate: () => mutate((response) => response, {revalidate: true}),
	};
}
