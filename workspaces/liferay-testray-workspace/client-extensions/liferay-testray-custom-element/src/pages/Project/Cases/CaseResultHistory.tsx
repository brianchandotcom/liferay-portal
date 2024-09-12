/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useParams} from 'react-router-dom';
import {getTruncateText} from '~/util/getTruncateText';

import Code from '../../../components/Code';
import JiraLink from '../../../components/JiraLink';
import ListView, {ListViewProps} from '../../../components/ListView';
import StatusBadge from '../../../components/StatusBadge';
import {StatusBadgeType} from '../../../components/StatusBadge/StatusBadge';
import {TableProps} from '../../../components/Table';
import i18n from '../../../i18n';
import dayjs, {getDurationTime} from '../../../util/date';

type CaseResultHistoryProps = {
	caseId: string;
	listViewProps?: Partial<ListViewProps>;
	tableProps?: Partial<TableProps>;
};

const CaseResultHistory: React.FC<CaseResultHistoryProps> = ({
	caseId,
	listViewProps,
	tableProps,
}) => {
	const {caseResultId} = useParams();

	return (
		<ListView
			initialContext={{
				pageSize: 200,
			}}
			managementToolbarProps={{
				applyFilters: true,
				filterSchema: 'buildResultsHistory',
				title: i18n.translate('test-history'),
				visible: true,
			}}
			resource={`/testray-case-result-history/${caseId}`}
			tableProps={{
				columns: [
					{
						clickable: true,
						key: 'executionDate',
						render: (date) => (
							<p style={{maxWidth: '11ch'}}>
								{dayjs(date).format('lll')}
							</p>
						),
						value: i18n.translate('execution-date'),
					},
					{
						clickable: true,
						key: 'gitHash',
						render: (gitHash) =>
							gitHash === 'null' || '' ? '-' : gitHash,
						value: i18n.translate('git-hash'),
					},
					{
						clickable: true,
						key: 'duration',
						render: (duration) => getDurationTime(duration),
						value: i18n.translate('duration'),
					},
					{
						clickable: true,
						key: 'testrayProductVersionName',
						value: i18n.translate('product-version'),
					},
					{
						clickable: true,
						key: 'testrayRunName',
						value: i18n.translate('environment'),
						width: '250',
					},
					{
						clickable: true,
						key: 'testrayRoutineName',
						value: i18n.translate('routine'),
					},
					{
						key: 'status',
						render: (dueStatus) => (
							<StatusBadge type={dueStatus as StatusBadgeType}>
								{dueStatus}
							</StatusBadge>
						),
						value: i18n.translate('status'),
					},
					{
						clickable: true,
						key: 'testrayTeamName',
						value: i18n.translate('team'),
					},
					{
						key: 'warning',
						value: i18n.translate('warnings'),
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
				],
				highlight: ({testrayCaseResultId}) =>
					testrayCaseResultId === Number(caseResultId),
				responsive: true,
				rowWrap: true,
				...tableProps,
			}}
			{...listViewProps}
		/>
	);
};

export default CaseResultHistory;
