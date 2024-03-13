/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext, useMemo} from 'react';
import {useParams} from 'react-router-dom';
import ProgressBar from '~/components/ProgressBar';
import {TestrayContext, TestrayTypes} from '~/context/TestrayContext';
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
	const [, dispatch] = useContext(TestrayContext);

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

	const factorItems = useMemo(() => factorsData?.items || [], [
		factorsData?.items,
	]);

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
					columnsFixed: ['number'],
				}}
				managementToolbarProps={{
					addButton: () => formModal.modal.open(),
					applyFilters: true,
					filterSchema: 'buildRuns',
					title: i18n.translate('runs'),
				}}
				resource="/runs"
				tableProps={{
					actions,
					columns: [
						{
							clickable: true,
							key: 'number',
							render: (number) =>
								number?.toString().padStart(2, '0'),
							value: i18n.translate('run'),
						},
						...factorCategoryName,
						{
							clickable: true,
							key: 'caseResultFailed',
							value: i18n.translate('failed'),
						},
						{
							clickable: true,
							key: 'caseResultBlocked',
							value: i18n.translate('blocked'),
						},
						{
							clickable: true,
							key: 'caseResultsInProgress',
							value: i18n.translate('in-progress'),
						},
						{
							clickable: true,
							key: 'caseResultPassed',
							value: i18n.translate('passed'),
						},
						{
							clickable: true,
							key: 'caseResultTestFix',
							value: i18n.translate('test-fix'),
						},
						{
							clickable: true,
							key: 'total',
							render: (_, testrayRun) =>
								[
									testrayRun?.caseResultBlocked,
									testrayRun?.caseResultFailed,
									testrayRun?.caseResultInProgress,
									testrayRun?.caseResultIncomplete,
									testrayRun?.caseResultPassed,
									testrayRun?.caseResultTestFix,
									testrayRun?.caseResultUntested,
								].reduce(
									(previousValue, currentValue) =>
										previousValue + currentValue
								),
							size: 'sm',
							value: i18n.translate('total'),
						},
						{
							key: 'metrics',
							render: (_, testrayRun) => (
								<ProgressBar
									items={{
										blocked: testrayRun?.caseResultBlocked,
										failed: testrayRun?.caseResultFailed,
										incomplete:
											testrayRun?.caseResultIncomplete,
										passed: testrayRun?.caseResultPassed,
										test_fix: testrayRun?.caseResultTestFix,
									}}
								/>
							),
							value: i18n.translate('metrics'),
							width: '300',
						},
					],
					navigateTo: (run) =>
						`..?${new URLSearchParams({
							filter: JSON.stringify({
								'runToCaseResult/id': [run.id],
							}),
							filterSchema: 'buildResults',
						})}`,
					onClickRow(item) {
						dispatch({
							payload: item.number,
							type: TestrayTypes.SET_RUN,
						});
					},
				}}
				transformData={(response) =>
					testrayRunImpl.transformDataFromList(response)
				}
				variables={{
					filter: SearchBuilder.eq('buildId', buildId as string),
				}}
			/>

			<RunFormModal modal={formModal.modal} />
		</Container>
	);
};

export default Runs;
