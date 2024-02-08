/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useOutletContext} from 'react-router-dom';
import SearchBuilder from '~/core/SearchBuilder';
import {TestrayBuild, testrayComponentImpl} from '~/services/rest';

import Container from '../../../../../components/Layout/Container';
import ListView from '../../../../../components/ListView';
import ProgressBar from '../../../../../components/ProgressBar';
import i18n from '../../../../../i18n';

type OutletContext = {
	testrayBuild: TestrayBuild;
};

const Components = () => {
	const {testrayBuild} = useOutletContext<OutletContext>();

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
					filterSchema: 'buildComponents',
					title: i18n.translate('component'),
				}}
				resource={`/components?filter=${SearchBuilder.eq(
					'componentToCaseResult/r_buildToCaseResult_c_buildId',
					testrayBuild.id
				)}`}
				tableProps={{
					columns: [
						{
							clickable: true,
							key: 'name',
							size: 'lg',
							value: i18n.translate('component'),
						},
						{
							clickable: true,
							key: 'caseResultUntested',
							size: 'md',
							value: i18n.translate('untested'),
						},
						{
							clickable: true,
							key: 'caseResultFailed',
							size: 'md',
							value: i18n.translate('failed'),
						},
						{
							clickable: true,
							key: 'caseResultBlocked',
							size: 'md',
							value: i18n.translate('blocked'),
						},
						{
							clickable: true,
							key: 'caseResultInProgress',
							size: 'md',
							value: i18n.translate('in-progress'),
						},
						{
							clickable: true,
							key: 'caseResultPassed',
							size: 'md',
							value: i18n.translate('passed'),
						},
						{
							clickable: true,
							key: 'caseResultTestFix',
							size: 'md',
							value: i18n.translate('test-fix'),
						},
						{
							clickable: true,
							key: 'total',
							render: (_, testrayComponent) =>
								[
									testrayComponent?.caseResultBlocked,
									testrayComponent?.caseResultFailed,
									testrayComponent?.caseResultInProgress,
									testrayComponent?.caseResultPassed,
									testrayComponent?.caseResultTestFix,
									testrayComponent?.caseResultUntested,
								].reduce(
									(previousValue, currentValue) =>
										previousValue + currentValue
								),
							size: 'md',
							value: i18n.translate('total'),
						},
						{
							key: 'metrics',
							render: (_, testrayComponent) => (
								<ProgressBar
									items={{
										blocked:
											testrayComponent?.caseResultBlocked,
										failed:
											testrayComponent?.caseResultFailed,
										incomplete:
											testrayComponent?.caseResultIncomplete,
										passed:
											testrayComponent?.caseResultPassed,
										test_fix:
											testrayComponent?.caseResultTestFix,
									}}
								/>
							),
							value: i18n.translate('metrics'),
							width: '300',
						},
					],

					navigateTo: (componet) =>
						`..?${new URLSearchParams({
							filter: JSON.stringify({
								'componentToCaseResult/id': [componet.id],
							}),
							filterSchema: 'buildResults',
						})}`,
				}}
				transformData={(response) =>
					testrayComponentImpl.transformDataFromList(response)
				}
			/>
		</Container>
	);
};

export default Components;
