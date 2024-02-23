/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useParams} from 'react-router-dom';

import Container from '../../../components/Layout/Container';
import ListViewRest from '../../../components/ListView';
import ProgressBar from '../../../components/ProgressBar';
import SearchBuilder from '../../../core/SearchBuilder';
import i18n from '../../../i18n';
import {TestrayRoutine, testrayRoutineImpl} from '../../../services/rest';
import {getTimeFromNow} from '../../../util/date';
import useRoutineActions from './useRoutineActions';

const getBuildDate = (buildName: string) => {
	return buildName
		?.split(' ')
		?.at(-1)
		?.replace('[', ' ')
		.replace(']', '') as string;
};
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
					filterSchema: 'routines',
					title: i18n.translate('routines'),
				}}
				resource={`/testray-rest/project/${projectId}/routines-metrics`}
				tableProps={{
					actions,
					columns: [
						{
							clickable: true,
							key: 'name',
							size: 'md',
							sorteable: true,
							value: i18n.translate('routine'),
						},
						{
							clickable: true,
							key: 'dateCreated',
							render: (_, testrayRoutine: TestrayRoutine) =>
								testrayRoutine.caseResultAggregation?.buildName
									? getTimeFromNow(
											getBuildDate(
												testrayRoutine
													.caseResultAggregation
													?.buildName
											)
									  )
									: null,
							value: i18n.translate('execution-date'),
						},
						{
							clickable: true,
							key: 'untested',
							render: (_, testrayRoutine: TestrayRoutine) =>
								testrayRoutine.caseResultAggregation
									?.caseResultUntested ?? 0,
							value: i18n.translate('untested'),
						},
						{
							clickable: true,
							key: 'inprogress',
							render: (_, testrayRoutine: TestrayRoutine) =>
								testrayRoutine.caseResultAggregation
									?.caseResultInProgress ?? 0,
							value: i18n.translate('in-progress'),
						},
						{
							clickable: true,
							key: 'passed',
							render: (_, testrayRoutine: TestrayRoutine) =>
								testrayRoutine.caseResultAggregation
									?.caseResultPassed ?? 0,
							value: i18n.translate('passed'),
						},
						{
							clickable: true,
							key: 'failed',
							render: (_, testrayRoutine: TestrayRoutine) =>
								testrayRoutine.caseResultAggregation
									?.caseResultFailed ?? 0,
							value: i18n.translate('failed'),
						},
						{
							clickable: true,
							key: 'blocked',
							render: (_, testrayRoutine: TestrayRoutine) =>
								testrayRoutine.caseResultAggregation
									?.caseResultBlocked ?? 0,
							value: i18n.translate('blocked'),
						},
						{
							clickable: true,
							key: 'testfix',
							render: (_, testrayRoutine: TestrayRoutine) =>
								testrayRoutine.caseResultAggregation
									?.caseResultTestFix ?? 0,
							value: i18n.translate('test-fix'),
						},
						{
							clickable: true,
							key: 'total',
							render: (_, testrayRoutine: TestrayRoutine) =>
								[
									testrayRoutine.caseResultAggregation
										?.caseResultBlocked,
									testrayRoutine.caseResultAggregation
										?.caseResultFailed,
									testrayRoutine.caseResultAggregation
										?.caseResultInProgress,
									testrayRoutine.caseResultAggregation
										?.caseResultPassed,
									testrayRoutine.caseResultAggregation
										?.caseResultTestFix,
									testrayRoutine.caseResultAggregation
										?.caseResultUntested,
								]
									.map((count) => (count ? Number(count) : 0))
									.reduce(
										(previousValue, currentValue) =>
											previousValue + currentValue
									),
							value: i18n.translate('total'),
						},
						{
							clickable: true,
							key: 'metrics',
							render: (_, testrayRoutine: TestrayRoutine) => (
								<ProgressBar
									items={{
										blocked: Number(
											testrayRoutine.caseResultAggregation
												?.caseResultBlocked
										),
										failed: Number(
											testrayRoutine.caseResultAggregation
												?.caseResultFailed
										),
										passed: Number(
											testrayRoutine.caseResultAggregation
												?.caseResultPassed
										),
										test_fix: Number(
											testrayRoutine.caseResultAggregation
												?.caseResultTestFix
										),
									}}
								/>
							),
							value: i18n.translate('metrics'),
							width: '300',
						},
					],
					navigateTo: ({id}) => id,
				}}
				transformData={(response) =>
					testrayRoutineImpl.transformDataFromList(response)
				}
				variables={{
					filter: SearchBuilder.eq('projectId', projectId as string),
				}}
			/>
		</Container>
	);
};

export default Routines;
