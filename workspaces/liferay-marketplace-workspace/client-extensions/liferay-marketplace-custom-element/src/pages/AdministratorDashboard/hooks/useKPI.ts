/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import SearchBuilder from '../../../core/SearchBuilder';
import {
	PartnershipType,
	ProductCategories,
	ProductType,
} from '../../../enums/Product';
import HeadlessCommerceAdminCatalog from '../../../services/rest/HeadlessCommerceAdminCatalog';

const baseSearchBuilder = new SearchBuilder()
	.group('OPEN')
	.lambdaContains('specificationValues', '2025 Q')
	.or()
	.lambdaContains('specificationValues', '2024 Q')
	.or()
	.lambdaContains('specificationValues', '2023 Q')
	.group('CLOSE');

const projectUsingMarketplaceAppsFilter = new URLSearchParams({
	fields: 'totalCount',
	pageSize: '-1',
});

const partnershipIntegrationFilter = new URLSearchParams({
	fields: 'totalCount,productSpecifications',
	filter: new SearchBuilder()
		.lambda('specificationValues', PartnershipType.TECHNOLOGY_PARTNERSHIP)
		.build(),
	nestedFields: 'productSpecifications',
	pageSize: '-1',
});

const supportingQuartelyReleaseFilter = new URLSearchParams({
	fields: 'totalCount,productSpecifications',
	filter: baseSearchBuilder.clone().build(),
	nestedFields: 'productSpecifications',
	pageSize: '-1',
});

const connectorQuartelyReleaseFilter = new URLSearchParams({
	fields: 'totalCount,productSpecifications',
	filter: baseSearchBuilder
		.clone()
		.and()
		.lambda('categoryNames', ProductCategories.PAYMENT_METHODS)
		.build(),
	nestedFields: 'productSpecifications',
	pageSize: '-1',
});

const lowCodeConfigurationsPublishedFilter = new URLSearchParams({
	fields: 'totalCount,productSpecifications,categories',
	filter: new SearchBuilder()
		.lambda('specificationValues', ProductType.LOW_CODE_CONFIGURATION)
		.build(),
	nestedFields: 'productSpecifications',
	pageSize: '-1',
});

const percentage = (total: number, partial: number): number => {
	if (!total) {
		return 0;
	}

	return Math.round((partial / total) * 100);
};

const useKPI = () => {
	const {
		properties: {kpi: anualTargetKPIs},
	} = useMarketplaceContext();

	const {
		kpiConnectorQuartelyRelease,
		kpiLowCodePublishedApps,
		kpiPartnershipIntegration,
		kpiProjectUsingMarketplaceApps,
		kpiQuartelyReleaseApps,
	} = anualTargetKPIs;

	const {data, ...swr} = useSWR(
		'metrics/kpi',
		async () => {
			const [
				connectorQuartelyRelease,
				lowcodeConfigurationsPublished,
				partnerShipIntegration,
				projectUsingMarketplaceApps,
				supportingQuartelyRelease,
			] = await Promise.all(
				[
					connectorQuartelyReleaseFilter,
					lowCodeConfigurationsPublishedFilter,
					partnershipIntegrationFilter,
					projectUsingMarketplaceAppsFilter,
					supportingQuartelyReleaseFilter,
				].map((searchParams) =>
					HeadlessCommerceAdminCatalog.getProducts(searchParams)
				)
			);

			return [
				{
					annualTargetCurrent: projectUsingMarketplaceApps.totalCount,
					annualTargetTotal: kpiProjectUsingMarketplaceApps,
					colors: ['#9CE269', '#D4F3BE'],
					monthlyIncreasePct: 0,
					monthlyIncreaseValue: 0,
					monthlyIncreaseValueIsGrowing: 0 > 0,
					percentage: percentage(
						Number(kpiProjectUsingMarketplaceApps),
						projectUsingMarketplaceApps.totalCount
					),
					title: 'New Projects Using Marketplace Apps	',
				},
				{
					annualTargetCurrent: partnerShipIntegration.totalCount,
					annualTargetTotal: kpiPartnershipIntegration,
					colors: ['#FFB46E', '#FFE9D4'],
					monthlyIncreasePct: 0,
					monthlyIncreaseValue: 0,
					monthlyIncreaseValueIsGrowing: 0 > 0,
					percentage: percentage(
						Number(kpiPartnershipIntegration),
						partnerShipIntegration.totalCount
					),
					title: 'Technology Partnership With Integrations',
					viewDetailsPath: `/apps?filter=${partnershipIntegrationFilter.get('filter')}`,
				},
				{
					annualTargetCurrent: supportingQuartelyRelease.totalCount,
					annualTargetTotal: kpiQuartelyReleaseApps,
					colors: ['#4B9BFF', '#B1D4FF'],
					monthlyIncreasePct: 0,
					monthlyIncreaseValue: 0,
					monthlyIncreaseValueIsGrowing: 0 > 0,
					percentage: percentage(
						Number(kpiQuartelyReleaseApps),
						supportingQuartelyRelease.totalCount
					),
					title: 'Publisher With Apps Supporting Quarterly Release',
					viewDetailsPath: `/apps?filter=${supportingQuartelyReleaseFilter.get('filter')}`,
				},
				{
					annualTargetCurrent: connectorQuartelyRelease.totalCount,
					annualTargetTotal: kpiConnectorQuartelyRelease,
					colors: ['#FF73C3', '#FFE1F0'],
					monthlyIncreasePct: 0,
					monthlyIncreaseValue: 0,
					monthlyIncreaseValueIsGrowing: 0 > 0,
					percentage: percentage(
						Number(kpiConnectorQuartelyRelease),
						connectorQuartelyRelease.totalCount
					),
					title: 'Apps & Connectors Supporting Quarterly Release',
					viewDetailsPath: `/apps?filter=${connectorQuartelyReleaseFilter.get('filter')}`,
				},
				{
					annualTargetCurrent:
						lowcodeConfigurationsPublished.totalCount,
					annualTargetTotal: kpiLowCodePublishedApps,
					colors: ['#FFD76E', '#FFF3D4'],
					monthlyIncreasePct: 0,
					monthlyIncreaseValue: 0,
					monthlyIncreaseValueIsGrowing: 0 > 0,
					percentage: percentage(
						Number(kpiLowCodePublishedApps),
						lowcodeConfigurationsPublished.totalCount
					),
					title: 'Low Code Configurations Published',
					viewDetailsPath: `/apps?filter=${lowCodeConfigurationsPublishedFilter.get('filter')}`,
				},
			];
		},
		{
			refreshInterval: 60000,
		}
	);

	return {data, ...swr};
};

export default useKPI;
