/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {useMemo} from 'react';
import {Link, useOutletContext} from 'react-router-dom';
import {KeyedMutator} from 'swr';

import Avatar from '../../../../../../components/Avatar';
import AssignToMe from '../../../../../../components/Avatar/AssignToMe/AssignToMe';
import Code from '../../../../../../components/Code';
import JiraLink from '../../../../../../components/JiraLink';
import Container from '../../../../../../components/Layout/Container';
import StatusBadge from '../../../../../../components/StatusBadge';
import {StatusBadgeType} from '../../../../../../components/StatusBadge/StatusBadge';
import QATable, {Orientation} from '../../../../../../components/Table/QATable';
import i18n from '../../../../../../i18n';
import {
	MessageBoardMessage,
	TestrayAttachment,
	TestrayCaseResult,
	testrayCaseResultImpl,
} from '../../../../../../services/rest';
import {safeJSONParse} from '../../../../../../util';
import {getTimeFromNow} from '../../../../../../util/date';
import CaseResultHeaderActions from './CaseResultHeaderActions';

type OutletContext = {
	caseResult: TestrayCaseResult;
	mbMessage: MessageBoardMessage;
	mutateCaseResult: KeyedMutator<TestrayCaseResult>;
	projectId: string;
};

const CaseResult = () => {
	const {
		caseResult,
		mbMessage,
		mutateCaseResult,
		projectId,
	}: OutletContext = useOutletContext();

	const attachments = useMemo(
		() => safeJSONParse(caseResult.attachments, []) as TestrayAttachment[],
		[caseResult.attachments]
	);

	const hasCaseResultEditPermission = !!caseResult?.actions?.update;

	return (
		<>
			<CaseResultHeaderActions
				caseResult={caseResult}
				mutateCaseResult={mutateCaseResult}
			/>

			<ClayLayout.Row>
				<ClayLayout.Col xs={9}>
					<Container
						className="mt-4"
						collapsable
						title={i18n.translate('test-details')}
					>
						<QATable
							items={[
								{
									title: i18n.translate('status'),
									value: (
										<StatusBadge
											type={
												caseResult.dueStatus
													.key as StatusBadgeType
											}
										>
											{caseResult.dueStatus.name}
										</StatusBadge>
									),
								},
								{
									title: i18n.translate('errors'),
									value: caseResult.errors && (
										<Code>{caseResult.errors}</Code>
									),
								},
								{
									flexHeading: true,
									title: i18n.sub(
										'warnings-x',
										caseResult.warnings?.toString()
									),
									value: attachments.find(({name}) =>
										name.toLowerCase().includes('warning')
									)?.name,
								},
								{
									flexHeading: true,
									title: i18n.sub(
										'attachments-x',
										attachments.length.toString()
									),
									value: (
										<div className="d-flex flex-column mb-1">
											{attachments.map(
												(attachment, index) => (
													<a
														className="mt-2"
														href={attachment.url}
														key={index}
														rel="noopener noreferrer"
														target="_blank"
													>
														{attachment.name}

														<ClayIcon
															className="ml-2"
															fontSize={12}
															symbol="shortcut"
														/>
													</a>
												)
											)}
										</div>
									),
								},
								{
									title: i18n.translate('git-hash'),
									value: '',
								},
								{
									title: i18n.translate(
										'github-compare-urls'
									),
									value: '',
								},
							]}
						/>
					</Container>

					<Container
						className="mt-4"
						collapsable
						title={i18n.translate('case-details')}
					>
						<QATable
							items={[
								{
									title: i18n.translate('priority'),
									value: caseResult.case?.priority,
								},
								{
									title: i18n.translate('main-component'),
									value: caseResult.case?.component?.name,
								},
								{
									title: i18n.translate('subcomponents'),
									value: '',
								},
								{
									title: i18n.translate('type'),
									value: caseResult.case?.caseType?.name,
								},
								{
									title: i18n.translate('estimated-duration'),
									value:
										caseResult.case?.estimatedDuration || 0,
								},
								{
									title: i18n.translate('description'),
									value: caseResult.case?.description,
								},
								{
									title: i18n.translate('steps'),
									value: caseResult.case?.steps,
								},
							]}
						/>

						<Link
							to={`/project/${projectId}/cases/${caseResult.case?.id}`}
						>
							{i18n.translate('view-case')}
						</Link>
					</Container>
				</ClayLayout.Col>

				<ClayLayout.Col xs={3}>
					<Container collapsable title={i18n.translate('dates')}>
						<QATable
							items={[
								{
									title: i18n.translate('updated'),
									value: getTimeFromNow(
										caseResult.dateModified
									),
								},
								{
									title: '',
									value: '',
								},
								{
									divider: true,
									title: i18n.translate('execution-date'),
									value: getTimeFromNow(
										caseResult.dateModified
									),
								},
								{
									divider: true,
									title: i18n.translate('assignee'),
									value: caseResult?.user ? (
										<Avatar
											displayName
											name={caseResult.user.name}
											url={caseResult.user.image}
										/>
									) : (
										<AssignToMe
											onClick={() =>
												testrayCaseResultImpl
													.assignToMe(caseResult)
													.then(mutateCaseResult)
											}
										/>
									),
									visible:
										!!caseResult.user ||
										hasCaseResultEditPermission,
								},
								{
									divider: true,
									title: i18n.translate('issues'),
									value: caseResult.issues.length
										? caseResult.issues.map(
												(caseResultIssue, index) => (
													<JiraLink
														issue={caseResultIssue}
														key={index}
													/>
												)
										  )
										: '-',
								},
								{
									title: i18n.translate('comment'),
									value: mbMessage ? (
										<div className="d-flex flex-column">
											<cite>
												{mbMessage?.articleBody}
											</cite>

											<div className="align-items-center d-flex justify-center mt-2 text-gray">
												<Avatar
													name={
														mbMessage.creator?.name
													}
													url={
														mbMessage.creator?.image
													}
												/>

												<span className="ml-2">
													{`${
														mbMessage.creator?.name
													} · ${getTimeFromNow(
														mbMessage.dateCreated
													)}`}
												</span>
											</div>
										</div>
									) : (
										i18n.translate('none')
									),
								},
							]}
							orientation={Orientation.VERTICAL}
						/>
					</Container>
				</ClayLayout.Col>
			</ClayLayout.Row>
		</>
	);
};

export default CaseResult;
