/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useParams} from 'react-router-dom';
import { TestrayRoutine } from '~/services/rest';

import Container from '../../../components/Layout/Container';
import ListViewRest from '../../../components/ListView';
import ProgressBar from '../../../components/ProgressBar';
import i18n from '../../../i18n';
import {getTimeFromNow} from '../../../util/date';
import useRoutineActions from './useRoutineActions';

const Routines = () => {
	const {actions, navigate} = useRoutineActions();
	const {projectId} = useParams();

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
					addButton: () => navigate('create'),
					applyFilters: true,
					filterSchema: 'routines',
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
							clickable: true,
							key: 'dueDate',
							render: (_, testrayRoutine: TestrayRoutine) =>
								testrayRoutine.builds[0]?.dueDate
									? getTimeFromNow(
											testrayRoutine.builds[0]?.dueDate
									  )
									: null,
							value: i18n.translate('execution-date'),
						},
						{
							clickable: true,
							key: 'testrayStatusMetric',
							render: ({untested}) => untested,
							value: i18n.translate('untested'),
						},
						{
							clickable: true,
							key: 'testrayStatusMetric',
							render: ({inProgress}) => inProgress,
							value: i18n.translate('in-progress'),
						},
						{
							clickable: true,
							key: 'testrayStatusMetric',
							render: ({passed}) => passed,
							value: i18n.translate('passed'),
						},
						{
							clickable: true,
							key: 'testrayStatusMetric',
							render: ({failed}) => failed,
							value: i18n.translate('failed'),
						},
						{
							clickable: true,
							key: 'testrayStatusMetric',
							render: ({blocked}) => blocked,
							value: i18n.translate('blocked'),
						},
						{
							clickable: true,
							key: 'testrayStatusMetric',
							render: ({testfix}) => testfix,
							value: i18n.translate('test-fix'),
						},
						{
							clickable: true,
							key: 'testrayStatusMetric',
							render: ({total}) => total,
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
