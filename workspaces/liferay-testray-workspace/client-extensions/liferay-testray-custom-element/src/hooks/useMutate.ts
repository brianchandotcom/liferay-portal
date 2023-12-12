/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback} from 'react';
import {KeyedMutator, MutatorOptions} from 'swr';
import TestrayError from '~/TestrayError';

import {APIResponse} from '../services/rest';

const useMutate = <T = any>(mutate?: KeyedMutator<T>) => {
	const mutatePartial = useCallback(
		(data: Partial<T>, options?: MutatorOptions) => {
			if (!mutate) {
				throw new TestrayError('Mutate is missing');
			}

			mutate(
				(currentData) =>
					currentData ? {...currentData, ...data} : undefined,
				{
					revalidate: false,
					...options,
				}
			);
		},
		[mutate]
	);

	const removeItemFromList = useCallback(
		(mutate: KeyedMutator<any>, id: number, options?: MutatorOptions) =>
			mutate(
				(response: APIResponse) => ({
					...response,
					items: response?.items?.filter((item) => item?.id !== id),
					totalCount: response?.totalCount - 1,
				}),
				{revalidate: false, ...options}
			),
		[]
	);

	const updateItemFromList = useCallback(
		(
			mutate: KeyedMutator<any>,
			id: number,
			data: any,
			options?: MutatorOptions
		) =>
			mutate(
				(response: APIResponse<any>) => ({
					...response,
					items: response?.items?.map((item) => {
						if (item?.id === id) {
							return {
								...item,
								...(typeof data === 'function'
									? data(item)
									: data),
							};
						}

						return item;
					}),
				}),
				{revalidate: false, ...options}
			),
		[]
	);

	return {
		mutatePartial,
		removeItemFromList,
		updateItemFromList,
	};
};

export default useMutate;
