/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useNavigate} from 'react-router-dom';
import useSWR from 'swr';

import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import SearchBuilder from '../../../core/SearchBuilder';
import {
	PartnershipType,
	ProductCategories,
	ProductType,
} from '../../../enums/Product';
import useModalContext from '../../../hooks/useModalContext';
import marketplaceOAuth2 from '../../../services/oauth/Marketplace';
import HeadlessCommerceAdminCatalog from '../../../services/rest/HeadlessCommerceAdminCatalog';

const baseSearchBuilder = new SearchBuilder()
	.group('OPEN')
	.lambdaContains('specificationValues', '2025 Q')
	.or()
	.lambdaContains('specificationValues', '2024 Q')
	.or()
	.lambdaContains('specificationValues', '2023 Q')
	.group('CLOSE');

const connectorQuartelyReleaseFilter = baseSearchBuilder
	.clone()
	.and()
	.lambda('categoryNames', ProductCategories.PAYMENT_METHODS)
	.build();

const lowCodeConfigurationsPublishedFilter = new SearchBuilder()
	.lambda('specificationValues', ProductType.LOW_CODE_CONFIGURATION)
	.build();

const partnershipIntegrationFilter = new SearchBuilder()
	.lambda('specificationValues', PartnershipType.TECHNOLOGY_PARTNERSHIP)
	.build();

const supportingQuartelyReleaseFilter = baseSearchBuilder.clone().build();

const getAnnualTargetValues = (kpiTarget: string, value: number) => {
	if (kpiTarget.includes('/')) {
		const [current, total] = kpiTarget.split('/');

		return {
			annualTargetCurrent: Number(current),
			annualTargetTotal: Number(total),
		};
	}

	return {
		annualTargetCurrent: Number(value),
		annualTargetTotal: Number(kpiTarget),
	};
};

const useKPI = () => {
	const modal = useModalContext();
	const navigate = useNavigate();

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
				{
					data: {
						metrics: {
							connectorQuartelyRelease,
							lowCodeConfigurationsPublished,
							partnerShipIntegration,
							supportingQuartelyRelease,
						},
					},
				},
				projectUsingMarketplaceApps,
			] = await Promise.all([
				HeadlessCommerceAdminCatalog.getProductsDashboardKPI({
					connectorQuartelyRelease: connectorQuartelyReleaseFilter,
					lowCodeConfigurationsPublished:
						lowCodeConfigurationsPublishedFilter,
					partnerShipIntegration: partnershipIntegrationFilter,
					supportingQuartelyRelease: supportingQuartelyReleaseFilter,
				}),
				marketplaceOAuth2.getMarketplaceProjectsKPI(),
			]);

			const newProjectsUsingMarketplaceApps = Object.keys(
				projectUsingMarketplaceApps
			).length;

			return [
				{
					...getAnnualTargetValues(
						kpiProjectUsingMarketplaceApps,
						newProjectsUsingMarketplaceApps
					),
					colors: ['#9CE269', '#D4F3BE'],
					onClick: newProjectsUsingMarketplaceApps
						? () => {
								modal.onOpenModal({
									body: (
										<ul>
											{Object.keys(
												projectUsingMarketplaceApps
											).map((project) => (
												<li key={project}>{project}</li>
											))}
										</ul>
									),
									header: 'New Projects Using Marketplace Apps',
								});
							}
						: null,
					title: 'New Projects Using Marketplace Apps',
				},
				{
					onClick: () =>
						navigate(
							`/publishers?filter=customFields/AccountType:${PartnershipType.TECHNOLOGY_PARTNERSHIP}`
						),
					...getAnnualTargetValues(
						kpiPartnershipIntegration,
						partnerShipIntegration.totalCount
					),
					colors: ['#FFB46E', '#FFE9D4'],
					title: 'Technology Partnership With Integrations',
				},
				{
					onClick: () =>
						navigate(
							`/apps?filter=${supportingQuartelyReleaseFilter}`
						),
					...getAnnualTargetValues(
						kpiQuartelyReleaseApps,
						supportingQuartelyRelease.totalCount
					),
					colors: ['#4B9BFF', '#B1D4FF'],
					title: 'Publisher With Apps Supporting Quarterly Release',
				},
				{
					...getAnnualTargetValues(
						kpiConnectorQuartelyRelease,
						connectorQuartelyRelease.totalCount
					),
					colors: ['#FF73C3', '#FFE1F0'],
					onClick: () =>
						navigate(
							`/apps?filter=${connectorQuartelyReleaseFilter}`
						),
					title: 'Apps & Connectors Supporting Quarterly Release',
				},
				{
					...getAnnualTargetValues(
						kpiLowCodePublishedApps,
						lowCodeConfigurationsPublished.totalCount
					),
					colors: ['#FFD76E', '#FFF3D4'],
					onClick: () =>
						navigate(
							`/apps?filter=${lowCodeConfigurationsPublishedFilter}`
						),
					title: 'Low Code Configurations Published',
				},
			];
		},
		{
			refreshInterval: 120000,
		}
	);

	return {data, ...swr};
};

export default useKPI;
