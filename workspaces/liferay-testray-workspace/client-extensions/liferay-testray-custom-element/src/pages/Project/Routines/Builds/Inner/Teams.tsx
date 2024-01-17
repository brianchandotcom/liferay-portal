/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useParams} from 'react-router-dom';
import Container from '~/components/Layout/Container';
import ListView from '~/components/ListView';
import ProgressBar from '~/components/ProgressBar';
import SearchBuilder from '~/core/SearchBuilder';
import i18n from '~/i18n';
import {TestrayComponent} from '~/services/rest';
import {CaseResultStatuses} from '~/util/constants';

import {testrayTeamImpl} from '../../../../../services/rest/TestrayTeam';

type ProgressBarResults = {
	blocked: number;
	failed: number;
	incomplete: number;
	passed: number;
	test_fix: number;
};

const Teams = () => {
	const {buildId} = useParams();

	const getTotalResults = (components: TestrayComponent[]): number =>
		components.reduce(
			(accumulator, component) =>
				accumulator +
				Number(component?.caseResultBlocked) +
				Number(component?.caseResultFailed) +
				Number(component?.caseResultInProgress) +
				Number(component?.caseResultPassed) +
				Number(component?.caseResultTestFix) +
				Number(component?.caseResultUntested),
			0
		);

	const getProgressBarResults = (
		components: TestrayComponent[]
	): ProgressBarResults =>
		components.reduce(
			(accumulator, component) => {
				accumulator.blocked +=
					Number(component?.caseResultBlocked) || 0;
				accumulator.failed += Number(component?.caseResultFailed) || 0;
				accumulator.incomplete +=
					Number(
						component?.caseResultUntested &&
							component?.caseResultInProgress
					) || 0;
				accumulator.passed += Number(component?.caseResultPassed) || 0;
				accumulator.test_fix +=
					Number(component?.caseResultTestFix) || 0;

				return accumulator;
			},
			{
				blocked: 0,
				failed: 0,
				incomplete: 0,
				passed: 0,
				test_fix: 0,
			}
		);

	const getStatusesResults = (
		components: TestrayComponent[],
		statusKey: CaseResultStatuses
	): number =>
		components.reduce(
			(accumulator, component) =>
				accumulator + Number(component[statusKey]) || 0,
			0
		);

	return (
		<Container className="mt-4">
			<ListView
				initialContext={{
					columns: {
						blocked: false,
						in_progress: false,
						test_fix: false,
						untested: false,
					},
					columnsFixed: ['name'],
				}}
				managementToolbarProps={{
					filterSchema: 'buildTeams',
					title: i18n.translate('teams'),
				}}
				resource={testrayTeamImpl.resource}
				tableProps={{
					columns: [
						{
							key: 'name',
							size: 'md',
							value: i18n.translate('team'),
						},
						{
							clickable: true,
							key: 'untested',
							render: (_, {teamToComponents}) =>
								getStatusesResults(
									teamToComponents,
									CaseResultStatuses.UNTESTED
								),

							value: i18n.translate('untested'),
						},
						{
							clickable: true,
							key: 'in_progress',
							render: (_, {teamToComponents}) =>
								getStatusesResults(
									teamToComponents,
									CaseResultStatuses.INPROGRESS
								),

							value: i18n.translate('in-progress'),
						},
						{
							clickable: true,
							key: 'passed',
							render: (_, {teamToComponents}) =>
								getStatusesResults(
									teamToComponents,
									CaseResultStatuses.PASSED
								),
							value: i18n.translate('passed'),
						},
						{
							clickable: true,
							key: 'failed',
							render: (_, {teamToComponents}) =>
								getStatusesResults(
									teamToComponents,
									CaseResultStatuses.FAILED
								),

							value: i18n.translate('failed'),
						},
						{
							clickable: true,
							key: 'blocked',
							render: (_, {teamToComponents}) =>
								getStatusesResults(
									teamToComponents,
									CaseResultStatuses.BLOCKED
								),

							value: i18n.translate('blocked'),
						},
						{
							clickable: true,
							key: 'test_fix',
							render: (_, {teamToComponents}) =>
								getStatusesResults(
									teamToComponents,
									CaseResultStatuses.TEST_FIX
								),

							value: i18n.translate('test-fix'),
						},
						{
							clickable: true,
							key: 'total',
							render: (_, {teamToComponents}) =>
								getTotalResults(teamToComponents),

							value: i18n.translate('total'),
						},
						{
							clickable: true,
							key: 'metrics',
							render: (_, {teamToComponents}) => (
								<ProgressBar
									chartOrder={[
										'passed',
										'failed',
										'blocked',
										'test_fix',
										'incomplete',
									]}
									items={getProgressBarResults(
										teamToComponents
									)}
								/>
							),

							size: 'sm',
							value: i18n.translate('metrics'),
							width: '300',
						},
					],
				}}
				variables={{
					filter: SearchBuilder.eq(
						'teamToComponents/componentToCaseResult/r_buildToCaseResult_c_buildId',
						buildId as string
					),
				}}
			/>
		</Container>
	);
};

export default Teams;
