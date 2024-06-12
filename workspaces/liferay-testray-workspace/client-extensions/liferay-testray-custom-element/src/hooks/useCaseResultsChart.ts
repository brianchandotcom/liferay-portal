/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useMemo, useState} from 'react';
import {useLocation, useSearchParams} from 'react-router-dom';
import i18n from '~/i18n';
import {APIResponse} from '~/services/rest';
import {chartColors} from '~/util/constants';
import {getRandom} from '~/util/mock';

import {useFetch} from './useFetch';

enum statususes {
	PASSED = 'passed',
	FAILED = 'failed',
	BLOCKED = 'blocked',
	TEST_FIX = 'testfix',
	INCOMPLETE = 'incomplete',
}

const ColumnName = {
	'case-types': 'testrayCaseTypeName',
	'components': 'testrayComponentName',
	'runs': 'testrayRunName',
	'teams': 'testrayTeamName',
};

const chartSelectData = [
	{label: i18n.translate('runs'), value: 'runs'},
	{label: i18n.translate('teams'), value: 'teams'},
	{label: i18n.translate('components'), value: 'components'},
	{label: i18n.translate('case-types'), value: 'case-types'},
];

const useCaseResultsChart = ({buildId}: {buildId: number}) => {
	const [entity, setEntity] = useState('');
	const {pathname} = useLocation();
	const [searchParams] = useSearchParams();

	const serializedFilter = useMemo(() => {
		const filterString = searchParams.get('filter') as string;
		if (!filterString) {
			return '';
		}

		const filterObject = JSON.parse(filterString);

		for (const key in filterObject) {
			if (Array.isArray(filterObject[key])) {
				filterObject[key] = (filterObject[key] as string[]).join(',');
			}
		}

		return filterObject;
	}, [searchParams]);

	const params = serializedFilter
		? {
				params: {
					pageSize: -1,
					...serializedFilter,
				},
			}
		: {
				params: {
					pageSize: -1,
				},
			};

	useEffect(() => {
		const path = pathname.split('/').at(-1) as string;

		if (chartSelectData.some(({value}) => value === path)) {
			return setEntity(path);
		}

		setEntity('');
	}, [pathname]);

	const {data, loading} = useFetch<APIResponse<any>>(
		`/testray-status-metrics/by-testray-buildId/${buildId}/testray-${entity}-metrics`,
		{...params, swrConfig: {shouldFetch: !!entity}}
	);

	const responseItems = useMemo(() => data?.items || [], [data?.items]);

	const chartData = useMemo(() => {
		return Object.entries(statususes).map(([key, value]) => {
			return [
				key,
				...responseItems.map(({testrayStatusMetric}) =>
					key === 'INCOMPLETE'
						? (testrayStatusMetric.untested || 0) +
							(testrayStatusMetric.inProgress || 0)
						: testrayStatusMetric[value] ?? getRandom(1000)
				),
			];
		});
	}, [responseItems]);

	const columnNames = useMemo(
		() =>
			responseItems.map(
				(item) => item[ColumnName[entity as keyof typeof ColumnName]]
			),
		[entity, responseItems]
	);

	const testrayRunNumber = useMemo(() => {
		if (entity === 'runs') {
			return responseItems.map((item) => item['testrayRunNumber']);
		}

		return [];
	}, [responseItems, entity]);

	return {
		chart: {
			colors: chartColors,
			columnNames,
			columns: chartData,
			statuses: Object.keys(statususes),
			testrayRunNumber,
		},
		entity,
		loading,
	};
};

export {useCaseResultsChart};
