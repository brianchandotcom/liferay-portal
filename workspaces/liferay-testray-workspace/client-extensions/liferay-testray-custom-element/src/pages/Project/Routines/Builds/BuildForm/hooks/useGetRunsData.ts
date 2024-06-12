/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useMemo} from 'react';
import SearchBuilder from '~/core/SearchBuilder';
import {useFetch} from '~/hooks/useFetch';
import {
	APIResponse,
	TestrayFactorCategory,
	TestrayRun,
	testrayFactorCategoryRest,
	testrayRunImpl,
} from '~/services/rest';

const useGetRunsData = (
	buildId?: string,
	setValue?: any,
	setRunOptionsList?: any
) => {
	const {data: runsData, loading} = useFetch<APIResponse<TestrayRun>>(
		testrayRunImpl.resource,
		{
			params: {
				filter: SearchBuilder.eq('buildId', buildId as string),
				pageSize: 100,
			},
			transformData: (response) =>
				testrayRunImpl.transformDataFromList(response),
		}
	);

	const runItems = useMemo(() => runsData?.items || [], [runsData?.items]);

	const {data: categories} = useFetch<APIResponse<TestrayFactorCategory>>(
		testrayFactorCategoryRest.resource,
		{
			transformData: (response) =>
				testrayFactorCategoryRest.transformDataFromList(response),
		}
	);

	const categoryItems = useMemo(
		() => categories?.items || [],
		[categories?.items]
	);

	useEffect(() => {
		if (categoryItems.length) {
			testrayFactorCategoryRest
				.getOptionsByCategoryItems(categoryItems)
				.then(setRunOptionsList)
				.catch(console.error);
		}
		const runOptions: any = [];

		runItems?.forEach((item) => {
			runOptions.push({
				applicationServer: item?.applicationServer as string,
				browser: item?.browser as string,
				database: item?.database as string,
				javaJDK: item?.javaJDK as string,
				operatingSystem: item?.operatingSystem as string,
				runId: item?.id,
			});
		});

		setValue('runOptions', runOptions);
	}, [categoryItems, runItems, setRunOptionsList, setValue]);

	return {
		loading,
		runItems,
	};
};

export default useGetRunsData;
