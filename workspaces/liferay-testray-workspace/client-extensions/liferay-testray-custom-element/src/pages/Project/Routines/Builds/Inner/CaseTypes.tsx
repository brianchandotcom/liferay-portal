/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useOutletContext} from 'react-router-dom';
import {TestrayBuild, testrayCaseTypeImpl} from '~/services/rest';

import Container from '../../../../../components/Layout/Container';
import ListView from '../../../../../components/ListView';
import ProgressBar from '../../../../../components/ProgressBar';
import SearchBuilder from '../../../../../core/SearchBuilder';
import i18n from '../../../../../i18n';

type OutletContext = {
	testrayBuild: TestrayBuild;
};

const CaseTypes = () => {
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
					sort: {
						direction: 'ASC',
						key: 'name',
					},
				}}
				managementToolbarProps={{
					applyFilters: true,
					filterSchema: 'buildCaseTypes',
					title: i18n.translate('case-types'),
				}}
				resource={testrayCaseTypeImpl.resource}
				tableProps={{
					columns: [
						{
							clickable: true,
							key: 'name',
							size: 'md',
							value: i18n.translate('test-type'),
						},
						{
							clickable: true,
							key: 'total',
							render: (_, caseType) =>
								[
									caseType?.caseResultBlocked,
									caseType?.caseResultFailed,
									caseType?.caseResultInProgress,
									caseType?.caseResultIncomplete,
									caseType?.caseResultPassed,
									caseType?.caseResultTestFix,
									caseType?.caseResultUntested,
								].reduce(
									(prevCount, currentCount) =>
										prevCount + currentCount
								),
							value: i18n.translate('total'),
						},
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
							key: 'caseResultUntested',
							value: i18n.translate('untested'),
						},
						{
							clickable: true,
							key: 'caseResultInProgress',
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
							key: 'metrics',
							render: (_, caseType) => (
								<ProgressBar
									items={{
										blocked: caseType?.caseResultBlocked,
										failed: caseType?.caseResultFailed,
										incomplete:
											caseType?.caseResultIncomplete,
										passed: caseType?.caseResultPassed,
										test_fix: caseType?.caseResultTestFix,
									}}
								/>
							),
							size: 'sm',
							value: i18n.translate('metrics'),
						},
					],
					navigateTo: (caseType) =>
						`..?${new URLSearchParams({
							filter: JSON.stringify({
								'caseToCaseResult/r_caseTypeToCases_c_caseTypeId': [
									caseType.id,
								],
							}),
							filterSchema: 'buildResults',
						})}`,
				}}
				transformData={(response) =>
					testrayCaseTypeImpl.transformDataFromList(response)
				}
				variables={{
					filter: SearchBuilder.eq(
						'caseTypeToCases/caseToBuildsCases/r_buildToBuildsCases_c_buildId',
						testrayBuild.id
					),
				}}
			/>
		</Container>
	);
};

export default CaseTypes;
