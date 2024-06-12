/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useMemo} from 'react';
import SearchBuilder from '~/core/SearchBuilder';
import {useFetch} from '~/hooks/useFetch';
import {
	APIResponse,
	TestrayFactor,
	TestrayFactorOption,
	testrayFactorCategoryRest,
	testrayFactorRest,
} from '~/services/rest';

import {Category} from '../Stack/FactorStackList';

const useGetFactorsData = (
	update: any,
	routineId?: string,
	setFactorOptionsList?: (values: TestrayFactorOption[][]) => void
) => {
	const {data: factorsData, loading} = useFetch<APIResponse<TestrayFactor>>(
		testrayFactorRest.resource,
		{
			params: {
				filter: SearchBuilder.eq('routineId', routineId as string),
				pageSize: 100,
			},
			transformData: (response) =>
				testrayFactorRest.transformDataFromList(response),
		}
	);

	const factorItems = useMemo(
		() => factorsData?.items || [],
		[factorsData?.items]
	);

	useEffect(() => {
		if (factorItems.length) {
			testrayFactorCategoryRest
				.getFactorCategoryItems(factorItems)
				.then(setFactorOptionsList)
				.catch(console.error);

			const factorItem: Category = [];

			factorItems.forEach((item, index) => {
				factorItem[index] = {
					factorCategory: item.factorCategory?.name as string,
					factorCategoryId: item.factorCategory?.id as number,
					factorOption: item.factorOption?.name as string,
					factorOptionId: item.factorOption?.id as number,
				};
			});

			update(0, factorItem);
		}
	}, [factorItems, setFactorOptionsList, update]);

	return {factorItems, loading};
};

export default useGetFactorsData;
