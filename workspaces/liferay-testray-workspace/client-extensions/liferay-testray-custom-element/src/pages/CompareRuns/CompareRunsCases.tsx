/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {memo} from 'react';
import {Link, useOutletContext, useParams} from 'react-router-dom';
import Code from '~/components/Code';
import JiraLink from '~/components/JiraLink';
import Container from '~/components/Layout/Container';
import ListView from '~/components/ListView';
import StatusBadge from '~/components/StatusBadge';
import {StatusBadgeType} from '~/components/StatusBadge/StatusBadge';
import i18n from '~/i18n';
import {TestrayRun} from '~/services/rest';
import {getTruncateText} from '~/util/getTruncateText';
import {CaseResultStatuses} from '~/util/statuses';

type RunStatusProps = {
	caseResultId: number;
	dueStatusApplied?: string | null;
	run: TestrayRun;
};

type RunErrorProps = {
	caseResultId: number;
	error: string;
	run: TestrayRun;
};

type CompareRunsOutlet = {
	runs: TestrayRun[];
};

const RunStatus: React.FC<RunStatusProps> = ({
	caseResultId,
	dueStatusApplied,
	run,
}) => {
	const didNotRunStatus = dueStatusApplied || CaseResultStatuses.DID_NOT_RUN;

	const LinkWrapper =
		didNotRunStatus === CaseResultStatuses.DID_NOT_RUN
			? ({children}: {children: React.ReactNode}) => <>{children}</>
			: Link;

	return (
		<StatusBadge type={didNotRunStatus as StatusBadgeType}>
			<LinkWrapper
				to={`/project/${run?.build?.project?.id}/routines/${run?.build?.routine?.id}/build/${run?.build?.id}/case-result/${caseResultId}`}
			>
				{didNotRunStatus === CaseResultStatuses.DID_NOT_RUN
					? i18n.translate('dnr')
					: didNotRunStatus}
			</LinkWrapper>
		</StatusBadge>
	);
};

const RunError: React.FC<RunErrorProps> = ({caseResultId, error, run}) => {
	const LinkWrapper = Link;

	return (
		<LinkWrapper
			to={`/project/${run?.build?.project?.id}/routines/${run?.build?.routine?.id}/build/${run?.build?.id}/case-result/${caseResultId}`}
		>
			<Code title={error as string}>{getTruncateText(error, 200)}</Code>
		</LinkWrapper>
	);
};

const RunStatusMemoized = memo(RunStatus);
const RunErrorMemoized = memo(RunError);

const CompareRunsCases = () => {
	const {runA: runAId, runB: runBId} = useParams();

	const {
		runs: [runA, runB],
	} = useOutletContext<CompareRunsOutlet>();

	return (
		<Container>
			<ListView
				initialContext={{
					pageSize: 100,
				}}
				managementToolbarProps={{
					applyFilters: true,
					display: {columns: false},
					filterSchema: 'compareRunsCases',
				}}
				resource={`/testray-run-comparisons/${runAId}/${runBId}/testray-case-result-comparisons`}
				tableProps={{
					columns: [
						{
							key: 'priority',
							value: i18n.translate('priority'),
							width: '100',
						},
						{
							key: 'testrayComponentName',
							value: i18n.translate('component'),
						},
						{
							key: 'name',
							size: 'xl',
							value: i18n.translate('case'),
						},
						{
							key: 'status1',
							render: (_, data: any & {rowIndex: number}) => (
								<RunStatusMemoized
									caseResultId={data.id1}
									dueStatusApplied={data.status1}
									run={runA}
								/>
							),
							size: 'md',
							value: i18n.sub('status-in-x', 'run-a'),
						},
						{
							key: 'status2',
							render: (_, data: any & {rowIndex: number}) => (
								<RunStatusMemoized
									caseResultId={data.id2}
									dueStatusApplied={data.status2}
									run={runB}
								/>
							),
							size: 'md',
							value: i18n.sub('status-in-x', 'run-b'),
						},
						{
							key: 'issue1',
							render: (issues: string) => (
								<JiraLink
									displayViewInJira={false}
									issue={issues}
								/>
							),
							size: 'md',
							value: i18n.sub('issues-in-x', 'run-a'),
						},
						{
							key: 'issue2',
							render: (issues: string) => (
								<JiraLink
									displayViewInJira={false}
									issue={issues}
								/>
							),
							size: 'md',
							value: i18n.sub('issues-in-x', 'run-b'),
						},
						{
							key: 'error1',
							render: (error1: string, data: any) =>
								error1 && (
									<RunErrorMemoized
										caseResultId={data?.id1}
										error={error1}
										run={runA}
									/>
								),
							size: 'lg',
							value: i18n.sub('error-in-x', 'run-a'),
						},
						{
							key: 'error2',
							render: (error2: string, data: any) =>
								error2 && (
									<RunErrorMemoized
										caseResultId={data?.id2}
										error={error2}
										run={runB}
									/>
								),
							size: 'lg',
							value: i18n.sub('error-in-x', 'run-b'),
						},
					],
					rowWrap: true,
				}}
			/>
		</Container>
	);
};
export default CompareRunsCases;
