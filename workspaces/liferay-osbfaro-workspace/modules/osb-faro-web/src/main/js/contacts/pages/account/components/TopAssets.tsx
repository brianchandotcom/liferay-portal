/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Text} from '@clayui/core';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayEmptyState from '@clayui/empty-state';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClaySticker from '@clayui/sticker';
import ClayTable from '@clayui/table';
import ClayTabs from '@clayui/tabs';
import classNames from 'classnames';
import React, {useState} from 'react';
import {useHistory, useParams} from 'react-router-dom';
import {getMimeType} from '~/assets/components/mime-type';
import * as API from '~/shared/api';
import {
	ITopAsset,
	TopAssetMetric,
	TopAssetObjectType,
} from '~/shared/api/assets';
import Card from '~/shared/components/Card';
import StatesRenderer from '~/shared/components/states-renderer/StatesRenderer';
import {useRequest} from '~/shared/hooks/useRequest';
import {toThousands} from '~/shared/util/numbers';
import {Routes, toRoute} from '~/shared/util/router';

interface ITopAssetsProps {
	className?: string;
}

enum GroupByMetric {
	DOWNLOADS = 'downloads',
	IMPRESSIONS = 'impressions',
	VIEWS = 'views',
}

const GROUP_BY_TO_METRIC: Record<GroupByMetric, TopAssetMetric> = {
	[GroupByMetric.DOWNLOADS]: 'downloadsMetric',
	[GroupByMetric.IMPRESSIONS]: 'impressionsMetric',
	[GroupByMetric.VIEWS]: 'viewsMetric',
};

const TABS = ['content', 'files'] as const;

// TODO(LPD-91217): confirm `objectType` values once backend lands.

const TAB_OBJECT_TYPES: Record<(typeof TABS)[number], TopAssetObjectType> = {
	content: 'content',
	files: 'file',
};

const TAB_GROUP_BY_METRICS: Record<(typeof TABS)[number], GroupByMetric[]> = {
	content: [GroupByMetric.IMPRESSIONS, GroupByMetric.VIEWS],
	files: [
		GroupByMetric.DOWNLOADS,
		GroupByMetric.IMPRESSIONS,
		GroupByMetric.VIEWS,
	],
};

const ASSET_ROUTE_MAP = {
	blog: Routes.ASSETS_BLOGS_OVERVIEW,
	document: Routes.ASSETS_DOCUMENTS_AND_MEDIA_OVERVIEW,
	form: Routes.ASSETS_FORMS_OVERVIEW,
	webContent: Routes.ASSETS_WEB_CONTENT_OVERVIEW,
} as const;

const getAssetRoute = (assetType?: string) =>
	ASSET_ROUTE_MAP[assetType as keyof typeof ASSET_ROUTE_MAP] ??
	Routes.ASSETS_OBJECT_ENTRY_OVERVIEW;

interface ITopAssetsTabContentProps {
	assets: ITopAsset[];
	groupBy: GroupByMetric;
	isFiles: boolean;
	loading: boolean;
	metrics: GroupByMetric[];
	setGroupBy: (metric: GroupByMetric) => void;
}

const TopAssetsTabContent: React.FC<ITopAssetsTabContentProps> = ({
	assets,
	groupBy,
	isFiles,
	loading,
	metrics,
	setGroupBy,
}) => {
	const {channelId, groupId} = useParams<{
		channelId: string;
		groupId: string;
	}>();

	const groupByLabels: Record<GroupByMetric, string> = {
		[GroupByMetric.DOWNLOADS]: Liferay.Language.get('downloads'),
		[GroupByMetric.IMPRESSIONS]: Liferay.Language.get('impressions'),
		[GroupByMetric.VIEWS]: Liferay.Language.get('views'),
	};

	const groupByLabel = groupByLabels[groupBy];
	const selectedMetric = GROUP_BY_TO_METRIC[groupBy];

	const isEmpty = !loading && !assets.length;

	return (
		<StatesRenderer empty={isEmpty} loading={loading}>
			<StatesRenderer.Loading />
			<StatesRenderer.Empty>
				<ClayEmptyState
					className="py-3 text-center"
					description={
						isFiles
							? Liferay.Language.get(
									'files-will-appear-here-when-available'
								)
							: Liferay.Language.get(
									'assets-will-appear-here-when-available'
								)
					}
					small
					title={
						isFiles
							? Liferay.Language.get('no-files-available')
							: Liferay.Language.get('no-assets-available')
					}
				/>
			</StatesRenderer.Empty>
			<StatesRenderer.Success>
				<ClayDropDown
					alignmentPosition={Align.BottomRight}
					closeOnClick
					trigger={
						<ClayButton
							borderless
							className="align-items-baseline d-inline-flex"
							displayType="unstyled"
							size="sm"
						>
							<div className="font-weight-semi-bold mr-3">
								<Text size={3}>
									{Liferay.Language.get('group-by')}
								</Text>
							</div>

							<div className="font-weight-semi-bold text-secondary">
								<Text size={3}>
									{groupByLabel}
									<ClayIcon
										className="ml-1"
										symbol="caret-bottom"
									/>
								</Text>
							</div>
						</ClayButton>
					}
				>
					<ClayDropDown.ItemList>
						{metrics.map((key) => (
							<ClayDropDown.Item
								key={key}
								onClick={() => setGroupBy(key)}
								symbolRight={
									groupBy === key ? 'check' : undefined
								}
							>
								{groupByLabels[key]}
							</ClayDropDown.Item>
						))}
					</ClayDropDown.ItemList>
				</ClayDropDown>

				<ClayTable className="mt-3">
					<ClayTable.Head>
						<ClayTable.Row>
							<ClayTable.Cell headingCell>
								{Liferay.Language.get('title')}
							</ClayTable.Cell>
							<ClayTable.Cell headingCell>
								{groupByLabel}
							</ClayTable.Cell>
						</ClayTable.Row>
					</ClayTable.Head>
					<ClayTable.Body>
						{assets.map((asset) => {
							const mimeType = getMimeType({
								assetType: asset.assetType,
								mimeType: asset.mimeType,
							});

							return (
								<ClayTable.Row key={asset.id}>
									<ClayTable.Cell expanded>
										<div className="align-items-center d-flex">
											<div className="mr-3">
												<ClaySticker
													className={classNames(
														mimeType.className
													)}
													displayType="unstyled"
												>
													<ClayIcon
														symbol={mimeType.icon}
													/>
												</ClaySticker>
											</div>
											<ClayLink
												className="font-weight-semi-bold text-dark"
												href={toRoute(
													getAssetRoute(
														asset.assetType
													),
													{
														assetId: asset.id,
														channelId,
														groupId,
														touchpoint: 'overview',
														...(asset.assetType && {
															type: encodeURIComponent(
																asset.assetType
															),
														}),
														...(asset.assetTitle && {
															title: encodeURIComponent(
																asset.assetTitle
															),
														}),
													}
												)}
											>
												{asset.assetTitle}
											</ClayLink>
										</div>
									</ClayTable.Cell>
									<ClayTable.Cell>
										{toThousands(
											asset[selectedMetric].value
										)}
									</ClayTable.Cell>
								</ClayTable.Row>
							);
						})}
					</ClayTable.Body>
				</ClayTable>
			</StatesRenderer.Success>
		</StatesRenderer>
	);
};

const TopAssets: React.FC<ITopAssetsProps> = ({className}) => {
	const history = useHistory();
	const {
		channelId,
		groupId,
		id: accountId,
	} = useParams<{
		channelId: string;
		groupId: string;
		id: string;
	}>();

	const [activeTab, setActiveTab] = useState(0);
	const [groupBy, setGroupBy] = useState<GroupByMetric>(
		GroupByMetric.IMPRESSIONS
	);

	const selectedMetric = GROUP_BY_TO_METRIC[groupBy];

	const metrics = TAB_GROUP_BY_METRICS[TABS[activeTab]];

	const handleActiveTabChange = (index: number) => {
		setActiveTab(index);

		if (!TAB_GROUP_BY_METRICS[TABS[index]].includes(groupBy)) {
			setGroupBy(GroupByMetric.IMPRESSIONS);
		}
	};

	const {data, loading} = useRequest<
		Parameters<typeof API.assets.fetchAccountTopAssets>[0],
		{items: ITopAsset[]}
	>({
		dataSourceFn: API.assets.fetchAccountTopAssets,
		variables: {
			accountId,
			channelId,
			groupId,
			objectType: TAB_OBJECT_TYPES[TABS[activeTab]],
			selectedMetric,
		},
	});

	const assets = data?.items ?? [];

	const tabContent = (
		<TopAssetsTabContent
			assets={assets}
			groupBy={groupBy}
			isFiles={TABS[activeTab] === 'files'}
			loading={loading}
			metrics={metrics}
			setGroupBy={setGroupBy}
		/>
	);

	return (
		<Card className={classNames('top-assets', className)} minHeight={260}>
			<Card.Title className="p-3">
				<Text weight="semi-bold">
					{Liferay.Language.get('top-assets').toUpperCase()}
				</Text>
			</Card.Title>
			<Card.Body className="p-0">
				<ClayTabs
					active={activeTab}
					onActiveChange={handleActiveTabChange}
				>
					<ClayTabs.Item>
						{Liferay.Language.get('content')}
					</ClayTabs.Item>
					<ClayTabs.Item>
						{Liferay.Language.get('files')}
					</ClayTabs.Item>
				</ClayTabs>

				<ClayTabs.Content activeIndex={activeTab} fade>
					<ClayTabs.TabPane className="pb-0">
						{tabContent}
					</ClayTabs.TabPane>
					<ClayTabs.TabPane className="pb-0">
						{tabContent}
					</ClayTabs.TabPane>
				</ClayTabs.Content>

				{!!assets.length && (
					<div className="d-flex p-3">
						<ClayButton
							borderless
							className="ml-auto rounded-lg"
							onClick={() =>
								history.push(
									toRoute(Routes.ASSETS, {channelId, groupId})
								)
							}
							size="sm"
						>
							{Liferay.Language.get('view-all')}
						</ClayButton>
					</div>
				)}
			</Card.Body>
		</Card>
	);
};

export default TopAssets;
