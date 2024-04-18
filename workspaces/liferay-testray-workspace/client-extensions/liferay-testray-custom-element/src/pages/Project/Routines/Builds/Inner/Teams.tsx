/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useParams} from 'react-router-dom';
import Container from '~/components/Layout/Container';
import ListView from '~/components/ListView';
import ProgressBar from '~/components/ProgressBar';
import i18n from '~/i18n';

const Teams = () => {
	const {buildId} = useParams();

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
					columnsFixed: ['testrayTeamName'],
				}}
				managementToolbarProps={{
					applyFilters: true,
					filterSchema: 'buildTeams',
					title: i18n.translate('teams'),
				}}
				resource={`/testray-status-metrics/by-testray-buildId/${buildId}/testray-teams-metrics`}
				tableProps={{
					columns: [
						{
							clickable: true,
							key: 'testrayTeamName',
							size: 'md',
							value: i18n.translate('team'),
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

					navigateTo: ({testrayTeamId}) =>
						`..?${new URLSearchParams({
							filter: JSON.stringify({
								'componentToCaseResult/r_teamToComponents_c_teamId': [
									testrayTeamId,
								],
							}),
							filterSchema: 'buildResults',
						})}`,
				}}
			/>
		</Container>
	);
};

export default Teams;
