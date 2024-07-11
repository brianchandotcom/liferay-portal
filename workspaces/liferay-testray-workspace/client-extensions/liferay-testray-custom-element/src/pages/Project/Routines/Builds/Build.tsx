/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {useParams} from 'react-router-dom';
import Avatar from '~/components/Avatar';
import AssignToMe from '~/components/Avatar/AssignToMe/AssignToMe';
import Code from '~/components/Code';
import JiraLink from '~/components/JiraLink';
import Container from '~/components/Layout/Container';
import ListView from '~/components/ListView';
import StatusBadge from '~/components/StatusBadge';
import {StatusBadgeType} from '~/components/StatusBadge/StatusBadge';
import useMutate from '~/hooks/useMutate';
import i18n from '~/i18n';
import {testrayCaseResultImpl} from '~/services/rest';
import {getTruncateText} from '~/util/getTruncateText';

import useBuildTestActions from './useBuildTestActions';

const Build = () => {
	const {actions, form} = useBuildTestActions();
	const {buildId} = useParams();
	const {updateItemFromList} = useMutate();

	return (
		<Container className="mt-4">
			<ListView
				initialContext={{
					columns: {environment: false},
					pageSize: 200,
				}}
				managementToolbarProps={{
					applyFilters: true,
					filterSchema: 'buildResults',
					title: i18n.translate('tests'),
				}}
				resource={`/testray-case-result/${buildId}`}
				tableProps={{
					actions,
					columns: [
						{
							clickable: true,
							key: 'flaky',
							render: (_, {flaky, testrayCaseName}) => (
								<>
									{flaky && (
										<ClayTooltipProvider>
											<span
												className="tr-table__row__flaky-icon"
												data-tooltip-align="top"
												title={i18n.translate(
													'this-is-a-possible-flaky-test'
												)}
											>
												<ClayIcon symbol="flag-full" />
											</span>
										</ClayTooltipProvider>
									)}
									{testrayCaseName}
								</>
							),
							size: 'md',
							value: i18n.translate('case'),
							width: '350',
						},
						{
							clickable: true,
							key: 'testrayCaseTypeName',
							value: i18n.translate('case-type'),
						},
						{
							clickable: true,
							key: 'priority',
							value: i18n.translate('priority'),
						},
						{
							clickable: true,
							key: 'testrayTeamName',
							value: i18n.translate('team'),
						},
						{
							clickable: true,
							key: 'testrayComponentName',
							value: i18n.translate('component'),
						},
						{
							clickable: true,
							key: 'testrayRunNumber',
							render: (testrayRunNumber) =>
								testrayRunNumber?.toString().padStart(2, '0'),
							value: i18n.translate('run'),
						},
						{
							clickable: true,
							key: 'testrayRunName',
							value: i18n.translate('environment'),
							width: '250',
						},
						{
							key: 'user',
							render: (_: any, caseResult, mutate) => {
								if (caseResult.userName) {
									return (
										<Avatar
											className="text-capitalize"
											displayName
											name={caseResult.userName}
											size="sm"
											url={caseResult.userPortraitUrl}
										/>
									);
								}

								return (
									<AssignToMe
										onClick={() =>
											testrayCaseResultImpl
												.assignToMe(caseResult)
												.then(() => {
													updateItemFromList(
														mutate,
														0,
														{},
														{
															revalidate: true,
														}
													);
												})
												.then(form.onSuccess)
												.catch(form.onError)
										}
									/>
								);
							},
							truncate: false,
							value: i18n.translate('assignee'),
							width: '200',
						},
						{
							clickable: true,
							key: 'status',
							render: (dueStatus) => (
								<StatusBadge
									type={dueStatus as StatusBadgeType}
								>
									{dueStatus}
								</StatusBadge>
							),
							value: i18n.translate('status'),
						},
						{
							key: 'issues',
							render: (issues: string) => (
								<JiraLink
									displayViewInJira={false}
									issue={issues}
								/>
							),
							value: i18n.translate('issues'),
						},
						{
							key: 'error',
							render: (errors: string) =>
								errors && (
									<Code title={errors as string}>
										{getTruncateText(errors)}
									</Code>
								),
							size: 'xl',
							value: i18n.translate('errors'),
							width: '400',
						},
						{
							clickable: true,
							key: 'comment',
							size: 'lg',
							value: i18n.translate('comment'),
						},
					],
					navigateTo: ({testrayCaseResultId}) =>
						`case-result/${testrayCaseResultId}`,
					rowWrap: true,
				}}
			/>
		</Container>
	);
};

export default Build;
