/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {useOutletContext, useParams} from 'react-router-dom';

import JiraLink from '../../../components/JiraLink';
import Container from '../../../components/Layout/Container';
import PreviewInformation from '../../../components/Markdown/PreviewChangeType';
import QATable from '../../../components/Table/QATable';
import useIssuesFound from '../../../hooks/data/useIssuesFound';
import i18n from '../../../i18n';
import {TestrayCase} from '../../../services/rest';
import dayjs from '../../../util/date';
import useCaseResultActions from '../Routines/Builds/Inner/CaseResult/useCaseResultActions';
import CaseResultHistory from './CaseResultHistory';

type CaseOutlet = {
	testrayCase: TestrayCase;
};

const Case = () => {
	const {actions} = useCaseResultActions();
	const {projectId} = useParams();
	const {testrayCase}: CaseOutlet = useOutletContext();
	const issues = useIssuesFound({caseId: testrayCase.id});

	return (
		<>
			<Container collapsable title={i18n.translate('details')}>
				<QATable
					items={[
						{
							title: (
								<ClayIcon
									className="tr-qa-table__flaky-icon"
									symbol="flag-full"
								/>
							),
							value: i18n.translate(
								'this-is-a-possible-flaky-test'
							),
							visible: !!testrayCase.flaky,
						},
						{
							title: i18n.translate('type'),
							value: testrayCase.caseType?.name,
						},
						{
							title: i18n.translate('priority'),
							value: testrayCase.priority,
						},
						{
							title: i18n.translate('main-component'),
							value: testrayCase.component?.name,
						},
						{
							title: i18n.translate('description'),
							value: (
								<PreviewInformation
									data={
										testrayCase.description === 'null'
											? ''
											: testrayCase.description
									}
									displayType={testrayCase.descriptionType}
								/>
							),
						},
						{
							title: i18n.translate('estimated-duration'),
							value: testrayCase.estimatedDuration,
						},
						{
							title: i18n.translate('steps'),
							value: (
								<PreviewInformation
									data={testrayCase.steps}
									displayType={testrayCase.stepsType}
								/>
							),
						},
						{
							title: i18n.translate('date-created'),
							value: dayjs(testrayCase.dateCreated).format('lll'),
						},
						{
							title: i18n.translate('date-modified'),
							value: dayjs(testrayCase.dateModified).format(
								'lll'
							),
						},
						{
							title: i18n.translate('all-issues-found'),
							value: <JiraLink issue={issues} />,
						},
					]}
				/>
			</Container>

			<Container className="mt-3">
				<CaseResultHistory
					caseId={String(testrayCase.id)}
					tableProps={{
						actions,
						navigateTo: ({
							testrayBuildId,
							testrayCaseResultId,
							testrayRoutineId,
						}) =>
							`/project/${projectId}/routines/${testrayRoutineId}/build/${testrayBuildId}/case-result/${testrayCaseResultId}`,
					}}
				/>
			</Container>
		</>
	);
};

export default Case;
