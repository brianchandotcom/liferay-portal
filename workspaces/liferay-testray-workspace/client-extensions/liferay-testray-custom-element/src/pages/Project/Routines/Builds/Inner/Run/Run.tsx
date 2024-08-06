/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import {useParams} from 'react-router-dom';
import ProgressBar from '~/components/ProgressBar';
import {useFetch} from '~/hooks/useFetch';

import Container from '../../../../../../components/Layout/Container';
import ListView from '../../../../../../components/ListView';
import SearchBuilder from '../../../../../../core/SearchBuilder';
import i18n from '../../../../../../i18n';
import {
	APIResponse,
	TestrayFactor,
	testrayFactorRest,
	testrayRunImpl,
} from '../../../../../../services/rest';
import RunFormModal from './RunFormModal';
import useRunActions from './useRunActions';

const getCategoryName = (name = '') =>
	name
		.replace(/(?:^\w|[A-Z]|\b\w)/g, (word, index) =>
			index === 0 ? word.toLowerCase() : word.toUpperCase()
		)
		.replace(/\s+/g, '');

const Runs = () => {
	const {actions, formModal} = useRunActions();
	const {buildId, routineId} = useParams();

	const {data: factorsData} = useFetch<APIResponse<TestrayFactor>>(
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

	const factorCategoryName = factorItems
		.map(({factorCategory}) => ({
			clickable: true,
			key: getCategoryName(factorCategory?.name) as string,
			value: i18n.translate(`${factorCategory?.name}`),
		}))
		.sort((a: any, b: any) => a?.value?.localeCompare(b?.value));

	return (
		<Container className="mt-4">
			<ListView
				forceRefetch={formModal.forceRefetch}
				initialContext={{
					columns: {
						inprogress: false,
						passed: false,
						total: false,
						untested: false,
					},
					columnsFixed: ['testrayRunNumber'],
				}}
				managementToolbarProps={{
					addButton: () => formModal.modal.open(),
					applyFilters: true,
					filterSchema: 'buildRuns',
					title: i18n.translate('runs'),
				}}
				resource={`/testray-status-metrics/by-testray-buildId/${buildId}/testray-runs-metrics`}
				tableProps={{
					actions,
					columns: [
						{
							clickable: true,
							key: 'testrayRunNumber',
							render: (number) =>
								number?.toString().padStart(2, '0'),
							value: i18n.translate('run'),
						},
						...factorCategoryName,
						{
							clickable: true,
							key: 'untested',
							render: (_, {testrayStatusMetric}) =>
								testrayStatusMetric.untested,

							value: i18n.translate('untested'),
						},
						{
							clickable: true,
							key: 'in-progress',
							render: (_, {testrayStatusMetric}) =>
								testrayStatusMetric.inProgress,
							value: i18n.translate('in-progress'),
						},
						{
							clickable: true,
							key: 'passed',
							render: (_, {testrayStatusMetric}) =>
								testrayStatusMetric.passed,
							value: i18n.translate('passed'),
						},
						{
							clickable: true,
							key: 'failed',
							render: (_, {testrayStatusMetric}) =>
								testrayStatusMetric.failed,
							value: i18n.translate('failed'),
						},
						{
							clickable: true,
							key: 'blocked',
							render: (_, {testrayStatusMetric}) =>
								testrayStatusMetric.blocked,
							value: i18n.translate('blocked'),
						},
						{
							clickable: true,
							key: 'test-fix',
							render: (_, {testrayStatusMetric}) =>
								testrayStatusMetric.testfix,
							value: i18n.translate('test-fix'),
						},
						{
							clickable: true,
							key: 'total',
							render: (_, {testrayStatusMetric}) =>
								testrayStatusMetric.total,
							value: i18n.translate('total'),
						},
						{
							clickable: true,
							key: 'testrayStatusMetric',
							render: (testrayStatusMetric) => (
								<ProgressBar
									chartOrder={[
										'passed',
										'failed',
										'blocked',
										'test_fix',
										'incomplete',
									]}
									items={{
										blocked: testrayStatusMetric?.blocked,
										failed: testrayStatusMetric?.failed,
										incomplete:
											testrayStatusMetric?.untested +
											testrayStatusMetric?.inProgress,
										passed: testrayStatusMetric?.passed,
										test_fix: testrayStatusMetric?.testfix,
									}}
								/>
							),
							size: 'sm',
							value: i18n.translate('metrics'),
							width: '300',
						},
					],
					navigateTo: ({testrayRunId}) =>
						`..?${new URLSearchParams({
							filter: JSON.stringify({
								testrayRunId: [testrayRunId],
							}),
							filterSchema: 'buildResults',
						})}`,
				}}
				transformData={(response) =>
					testrayRunImpl.transformDataFromList(response)
				}
			/>

			<RunFormModal modal={formModal.modal} />
		</Container>
	);
};

export default Runs;
