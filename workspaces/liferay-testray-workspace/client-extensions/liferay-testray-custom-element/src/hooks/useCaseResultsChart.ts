/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useMemo, useState} from 'react';
import {useLocation} from 'react-router-dom';
import {APIParametersOptions} from '~/core/Rest';
import SearchBuilder from '~/core/SearchBuilder';
import i18n from '~/i18n';
import {
	APIResponse,
	testrayComponentImpl,
	testrayRunImpl,
	testrayTeamImpl,
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

enum statususes {
	PASSED = 'caseResultPassed',
	FAILED = 'caseResultFailed',
	BLOCKED = 'caseResultBlocked',
	TEST_FIX = 'caseResultTestFix',
	INCOMPLETE = 'caseResultIncomplete',
}

const fields =
	'caseResultBlocked,caseResultFailed,caseResultIncomplete,caseResultPassed,caseResultTestFix,name';

const teamsFields =
	'name,teamToComponents.caseResultBlocked,teamToComponents.caseResultFailed,teamToComponents.caseResultIncomplete,teamToComponents.caseResultPassed,teamToComponents.caseResultTestFix';

const chartSelectData = [
	{label: i18n.translate('runs'), value: 'runs'},
	{label: i18n.translate('teams'), value: 'teams'},
	{label: i18n.translate('components'), value: 'components'},
];

const useCaseResultsChart = ({buildId}: {buildId: number}) => {
	const [entity, setEntity] = useState(chartSelectData[0].value);
	const {pathname} = useLocation();

	useEffect(() => {
		const path = pathname.split('/').at(-1) as string;

		if (chartSelectData.some(({value}) => value === path)) {
			setEntity(path);
		}
	}, [pathname]);

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
					fields: teamsFields,
					filter: SearchBuilder.eq(
						'teamToComponents/componentToCaseResult/r_buildToCaseResult_c_buildId',
						buildId
					),
				},
				url: testrayTeamImpl.resource,
			},
		}),
		[buildId]
	);

	const {data, loading} = useFetch<APIResponse<any>>(
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
				...responseItems

					.flatMap((caseResult) => {
						if (caseResult.teamToComponents) {
							return caseResult.teamToComponents.reduce(
								(accumulator: any, component: any) => {
									accumulator.caseResultBlocked +=
										Number(component?.caseResultBlocked) ||
										0;

									accumulator.caseResultFailed +=
										Number(component?.caseResultFailed) ||
										0;

									accumulator.caseResultUntested +=
										Number(
											component?.caseResultUntested &&
												component?.caseResultInProgress
										) || 0;

									accumulator.caseResultPassed +=
										Number(component?.caseResultPassed) ||
										0;

									accumulator.caseResultTestFix +=
										Number(component?.caseResultTestFix) ||
										0;

									return accumulator;
								},
								{
									caseResultBlocked: 0,
									caseResultFailed: 0,
									caseResultInProgress: 0,
									caseResultPassed: 0,
									caseResultTestFix: 0,
									caseResultUntested: 0,
								}
							);
						}

						return caseResult;
					})

					.map((caseResult) => caseResult[value] ?? getRandom(1000)),
			]),
		[responseItems]
	);

	const columnNames = useMemo(() => responseItems.map((item) => item.name), [
		responseItems,
	]);

	return {
		chart: {
			colors: chartColors,
			columnNames,
			columns: chartData,
			statuses: Object.keys(statususes),
		},
		chartSelectData,
		entity,
		loading,
		setEntity,
	};
};

export {useCaseResultsChart};
