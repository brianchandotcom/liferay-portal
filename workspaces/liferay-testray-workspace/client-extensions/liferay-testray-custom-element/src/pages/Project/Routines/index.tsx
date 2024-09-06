/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayTooltipProvider} from '@clayui/tooltip';
import {useNavigate, useParams} from 'react-router-dom';
import Button from '~/components/Button';
import TestrayIcons from '~/components/Icons/TestrayIcon';
import fetcher from '~/services/fetcher';
import {Liferay} from '~/services/liferay';
import {TestrayRoutine} from '~/services/rest';

import Container from '../../../components/Layout/Container';
import ListViewRest from '../../../components/ListView';
import ProgressBar from '../../../components/ProgressBar';
import i18n from '../../../i18n';
import {getTimeFromNow} from '../../../util/date';
import useRoutineActions from './useRoutineActions';

const Routines = () => {
	const {actions} = useRoutineActions();
	const {projectId} = useParams();
	const navigate = useNavigate();

	const handleCompareRuns = (testrayRoutineId: number) => {
		fetcher(
			`/testray-run-comparisons/by-testray-routineId/${testrayRoutineId}`
		)
			.then(({run1, run2}) =>
				navigate(`/compare-runs/${run1.id}/${run2.id}/teams`)
			)
			.catch((_) =>
				Liferay.Util.openToast({
					message: i18n.translate('unable-to-find-more-than-one-run'),
					type: 'danger',
				})
			);
	};

	return (
		<Container>
			<ListViewRest
				initialContext={{
					columns: {
						inprogress: false,
						passed: false,
						total: false,
						untested: false,
					},
					columnsFixed: ['name'],
				}}
				managementToolbarProps={{
					applyFilters: false,
					title: i18n.translate('routines'),
				}}
				resource={`/testray-status-metrics/by-testray-projectId/${projectId}/testray-routines-metrics`}
				tableProps={{
					actions,
					columns: [
						{
							clickable: true,
							key: 'testrayRoutineName',
							size: 'md',
							sorteable: true,
							value: i18n.translate('routine'),
						},
						{
							key: 'testrayRoutineId',
							render: (testrayRoutineId) => (
								<ClayTooltipProvider>
									<Button
										className="align-items-center d-flex p-0 rounded-circle tr-assign-to-me"
										data-tooltip-align="right"
										displayType="link"
										onClick={() =>
											handleCompareRuns(testrayRoutineId)
										}
										title={i18n.sub('compare-x', 'runs')}
									>
										<TestrayIcons
											fill="#acbcc7"
											size={30}
											symbol="drop"
										/>
									</Button>
								</ClayTooltipProvider>
							),
							value: '',
						},
						{
							clickable: true,
							key: 'dueDate',
							render: (_, testrayRoutine: TestrayRoutine) =>
								testrayRoutine?.testrayBuildDueDate
									? getTimeFromNow(
											testrayRoutine?.testrayBuildDueDate
										)
									: null,
							value: i18n.translate('execution-date'),
						},
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
							value: i18n.translate('metrics'),
							width: '300',
						},
					],
					navigateTo: ({testrayRoutineId}) => testrayRoutineId,
				}}
			/>
		</Container>
	);
};

export default Routines;
