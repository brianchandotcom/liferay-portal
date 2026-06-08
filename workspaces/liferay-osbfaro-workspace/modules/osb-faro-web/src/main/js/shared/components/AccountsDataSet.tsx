/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {
	LifecycleStages,
	lifecycleStagesLabelMap,
} from '~/contacts/pages/account/utils/constants';
import * as API from '~/shared/api';
import Card from '~/shared/components/Card';
import {
	FrontendDataSet,
	columns,
	pagination,
} from '~/shared/components/FrontendDataSet';
import {useRequest} from '~/shared/hooks/useRequest';
import {RangeSelectors} from '~/shared/types';
import {RangeKeyTimeRanges} from '~/shared/util/constants';
import {toThousands} from '~/shared/util/numbers';
import {Routes} from '~/shared/util/router';

const activityStatusItems = [
	{label: Liferay.Language.get('active'), value: 'ACTIVE'},
	{label: Liferay.Language.get('inactive'), value: 'INACTIVE'},
];

interface IAccountsDataSetProps {
	accountLifecycleId?: string;
	activityStatusFilter?: string;
	apiURL: string;
	channelId: string;
	countryFilter?: string;
	groupId: string;
	industryFilter?: string;
	lifecycleStageFilter?: LifecycleStages;
	rangeSelectors?: RangeSelectors;
}

interface ILifecycleStageFieldValue {
	id: string;
	stageType: LifecycleStages;
}

const buildSelectionPreloadedData = (value?: string, label?: string) =>
	value
		? {
				exclude: false,
				selectedItems: [{label: label ?? value, value}],
			}
		: undefined;

const AccountsDataSet: React.FC<IAccountsDataSetProps> = ({
	accountLifecycleId,
	activityStatusFilter,
	apiURL,
	channelId,
	countryFilter,
	groupId,
	industryFilter,
	lifecycleStageFilter,
	rangeSelectors = {
		rangeEnd: null,
		rangeKey: RangeKeyTimeRanges.Last30Days,
		rangeStart: null,
	},
}) => {
	const {data: lifecycleStageFieldValues} = useRequest({
		dataSourceFn: API.accounts.fetchLifecycleStageFieldValues,
		skipRequest: !accountLifecycleId,
		variables: {
			accountLifecycleId,
			channelId,
			groupId,
		},
	});

	const lifecycleStages: ILifecycleStageFieldValue[] =
		lifecycleStageFieldValues?.items ?? [];

	const lifecycleStageItems = lifecycleStages.map(({id, stageType}) => ({
		label: lifecycleStagesLabelMap[stageType].label,
		value: id,
	}));

	const preloadedLifecycleStage = lifecycleStageFilter
		? lifecycleStages.find(
				({stageType}) => stageType === lifecycleStageFilter
			)
		: undefined;

	let rangeSelectorParams = `rangeKey=${rangeSelectors.rangeKey}`;

	if (rangeSelectors.rangeEnd) {
		rangeSelectorParams += `&rangeEnd=${rangeSelectors.rangeEnd}`;
	}

	if (rangeSelectors.rangeStart) {
		rangeSelectorParams += `&rangeStart=${rangeSelectors.rangeStart}`;
	}

	const rangeApiURL = `${apiURL}?${rangeSelectorParams}`;

	return (
		<Card minHeight={300}>
			<FrontendDataSet
				apiURL={rangeApiURL}
				customDataRenderers={{
					accountActivityStatusRenderer: ({value}: {value: string}) =>
						value &&
						columns.cmsLabelRenderer({
							displayType:
								value === 'ACTIVE' ? 'success' : 'secondary',
							label:
								value === 'ACTIVE'
									? Liferay.Language.get('active')
									: Liferay.Language.get('inactive'),
						}),
					accountLifecycleStageRenderer: ({
						value,
					}: {
						value: LifecycleStages;
					}) =>
						value &&
						columns.cmsLabelRenderer({
							displayType:
								lifecycleStagesLabelMap[value].displayType,
							label: lifecycleStagesLabelMap[value].label,
						}),
					accountNameRenderer: ({
						itemData,
						value,
					}: {
						itemData: {id: string | number};
						value: string;
					}) =>
						columns.nameAndLinkRenderer({
							channelId,
							groupId,
							itemData,
							route: Routes.CONTACTS_ACCOUNT,
							value,
						}),
					annualRevenueRenderer: ({value}: {value: number}) => (
						<div>{toThousands(value)}</div>
					),
					dateRenderer: ({value}: {value: string}) =>
						columns.dateRenderer({itemData: {}, value}),
				}}
				emptyState={{
					description: Liferay.Language.get(
						'no-accounts-were-synced-from-the-connected-data-sources'
					),
					image: '/states/satellite.svg',
					title: Liferay.Language.get('no-accounts-found'),
				}}
				filters={[
					{
						id: 'activityStatus',
						items: activityStatusItems,
						label: Liferay.Language.get('activity-status'),
						name: 'activityStatus',
						preloadedData: buildSelectionPreloadedData(
							activityStatusFilter,
							activityStatusItems.find(
								({value}) => value === activityStatusFilter
							)?.label
						),
						type: 'selection',
					},
					...(accountLifecycleId
						? [
								{
									id: 'lifecycleStatus',
									items: lifecycleStageItems,
									label: Liferay.Language.get('status'),
									name: 'status',
									preloadedData: buildSelectionPreloadedData(
										preloadedLifecycleStage?.id,
										lifecycleStageFilter
											? lifecycleStagesLabelMap[
													lifecycleStageFilter
												].label
											: undefined
									),
									type: 'selection' as const,
								},
							]
						: []),
					{
						apiURL: `/o/faro/contacts/${groupId}/account/fds_field_values?channelId=${channelId}&fieldMappingFieldName=industry`,
						entityFieldType: 'string',
						id: 'industry',
						itemKey: 'name',
						itemLabel: 'name',
						label: Liferay.Language.get('industry'),
						multiple: true,
						preloadedData:
							buildSelectionPreloadedData(industryFilter),
						type: 'selection',
					},
					{
						apiURL: `/o/faro/contacts/${groupId}/account/fds_field_values?channelId=${channelId}&fieldMappingFieldName=country`,
						entityFieldType: 'string',
						id: 'country',
						itemKey: 'name',
						itemLabel: 'name',
						label: Liferay.Language.get('country'),
						multiple: true,
						preloadedData:
							buildSelectionPreloadedData(countryFilter),
						type: 'selection',
					},
				]}
				id="accounts-list-dataset"
				key={[
					activityStatusFilter,
					countryFilter,
					industryFilter,
					lifecycleStageFilter,
					lifecycleStages.length,
					...Object.values(rangeSelectors),
				].join()}
				pagination={pagination}
				showPagination
				snapshotsEnabled
				sorts={[
					{
						active: true,
						direction: 'asc',
						key: 'accountName',
						label: Liferay.Language.get('account'),
					},
				]}
				views={[
					{
						contentRenderer: 'table',
						default: true,
						label: Liferay.Language.get('default-view'),
						name: 'table',
						schema: {
							fields: [
								{
									contentRenderer: 'accountNameRenderer',
									fieldName: 'accountName',
									label: Liferay.Language.get('account'),
									sortable: true,
									truncate: true,
								},
								{
									fieldName: 'industry',
									label: Liferay.Language.get('industry'),
									sortable: true,
								},
								{
									contentRenderer:
										'accountLifecycleStageRenderer',
									fieldName: 'lifecycleStage',
									label: Liferay.Language.get(
										'lifecycle-stage'
									),
									sortable: true,
								},
								{
									contentRenderer: 'annualRevenueRenderer',
									fieldName: 'annualRevenue',
									label: Liferay.Language.get(
										'annual-revenue'
									),
									sortable: true,
								},
								{
									fieldName: 'country',
									label: Liferay.Language.get('country'),
									sortable: true,
								},
								{
									contentRenderer: 'dateRenderer',
									fieldName: 'lastActive',
									label: Liferay.Language.get('last-active'),
									sortable: true,
								},
								{
									contentRenderer: 'dateRenderer',
									fieldName: 'lastEnriched',
									label: Liferay.Language.get(
										'last-enriched'
									),
									sortable: true,
								},
								{
									contentRenderer:
										'accountActivityStatusRenderer',
									fieldName: 'activityStatus',
									label: Liferay.Language.get(
										'activity-status'
									),
									sortable: true,
								},
							],
						},
						thumbnail: 'table',
					},
				]}
			/>
		</Card>
	);
};

export default AccountsDataSet;
