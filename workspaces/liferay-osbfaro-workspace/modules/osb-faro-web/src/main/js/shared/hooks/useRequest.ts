/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {debounce} from 'lodash/fp';
import {useCallback, useRef, useState} from 'react';
import {useDeepEqualEffect} from '~/shared/hooks/useDeepEqualEffect';

export const useRequest = function useRequest<TParams extends object, TData>({
	dataSourceFn,
	debounceDelay = 0,
	initialState = {
		data: null,
		error: false,
		loading: true,
	},
	normalize = (val) => val,
	resetStateIfSkipingRequest = false,
	skipRequest = false,
	variables,
}: {
	dataSourceFn?: (params: TParams) => Promise<TData> | undefined;
	debounceDelay?: number;
	initialState?: {data: TData | null; error: boolean; loading: boolean};
	normalize?: (params: any) => any;
	resetStateIfSkipingRequest?: boolean;
	skipRequest?: boolean;
	variables: TParams;
}) {
	const requestAbortControllerRef = useRef<AbortController>();
	const debounceRef = useRef<ReturnType<typeof debounce>>();

	// eslint-disable-next-line react-hooks/exhaustive-deps
	const debouncedDataSourceFn = useCallback<any>(
		debounce(debounceDelay)((vars) => {
			if (!dataSourceFn) {
				return;
			}

			requestAbortControllerRef.current = new AbortController();

			const promise = dataSourceFn(vars);
			if (!promise) {
				return;
			}

			promise
				.then((result) => {
					if (requestAbortControllerRef.current?.signal.aborted) {
						return;
					}

					setState({
						...state,
						data: normalize(result),
						loading: false,
					});
				})
				.catch(
					(error) =>
						!error.IS_CANCELLATION_ERROR &&
						setState({...state, error: true, loading: false})
				);
		}),
		[]
	);

	const getData = () => {
		setState({...state, loading: true});

		debounceRef.current = debouncedDataSourceFn(variables);
	};

	const [state, setState] = useState({
		...initialState,
		refetch: getData,
	});

	useDeepEqualEffect(() => {
		if (!skipRequest) {
			getData();
		}
		else if (resetStateIfSkipingRequest) {
			setState({...state, ...initialState});
		}

		return () => {
			debounceRef.current?.cancel?.();
			requestAbortControllerRef.current?.abort();
		};
	}, [skipRequest, variables]);

	return state;
};
