/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo, useState} from 'react';
import {APIParametersOptions} from '~/core/Rest';
import SearchBuilder from '~/core/SearchBuilder';
import i18n from '~/i18n';
import {
	APIResponse,
	testrayComponentImpl,
	testrayRunImpl,
} from '~/services/rest';
import {chartColors} from '~/util/constants';
import {getRandom} from '~/util/mock';

import {useFetch} from './useFetch';

type TestrayChartResources = {
	components: {
		fetchParameters: APIParametersOptions;
		url: string;
	};
	runs: {
		fetchParameters: APIParametersOptions;
		url: string;
	};
	teams: {
		fetchParameters: APIParametersOptions;
		url: string;
	};
};

const statususes = {
	BLOCKED: 'caseResultBlocked',
	FAILED: 'caseResultFailed',
	INCOMPLETE: 'caseResultIncomplete',
	PASSED: 'caseResultPassed',
	TEST_FIX: 'caseResultTestFix',
};

const fields =
	'caseResultBlocked,caseResultFailed,caseResultIncomplete,caseResultPassed,caseResultTestFix';

const chartSelectData = [
	{label: i18n.translate('runs'), value: 'runs'},
	{label: i18n.translate('teams'), value: 'teams'},
	{label: i18n.translate('components'), value: 'components'},
];

const useCaseResultsChart = ({buildId}: {buildId: number}) => {
	const [entity, setEntity] = useState(chartSelectData[0].value);

	const resources: TestrayChartResources = useMemo(
		() => ({
			components: {
				fetchParameters: {
					fields,
					filter: SearchBuilder.eq(
						'componentToCaseResult/r_buildToCaseResult_c_buildId',
						buildId
					),
				},
				url: testrayComponentImpl.resource,
			},
			runs: {
				fetchParameters: {
					fields,
					filter: SearchBuilder.eq(
						'r_buildToRuns_c_buildId',
						buildId
					),
				},
				url: testrayRunImpl.resource,
			},
			teams: {
				fetchParameters: {
					fields,
					filter: SearchBuilder.eq(
						'componentToCaseResult/r_buildToCaseResult_c_buildId',
						buildId
					),
				},
				url: testrayComponentImpl.resource,
			},
		}),
		[buildId]
	);

	const {data} = useFetch<APIResponse<any>>(
		resources[entity as keyof TestrayChartResources].url,
		{
			params: {
				...resources[entity as keyof TestrayChartResources]
					.fetchParameters,
			},
		}
	);

	const responseItems = useMemo(() => data?.items || [], [data?.items]);

	const chartData = useMemo(
		() =>
			Object.entries(statususes).map(([key, value]) => [
				key,
				...responseItems.map(
					(caseResult) => caseResult[value] ?? getRandom(1000)
				),
			]),
		[responseItems]
	);

	return {
		chart: {
			colors: chartColors,
			columns: chartData,
			statuses: Object.keys(statususes),
		},
		chartSelectData,
		setEntity,
	};
};

export {useCaseResultsChart};
