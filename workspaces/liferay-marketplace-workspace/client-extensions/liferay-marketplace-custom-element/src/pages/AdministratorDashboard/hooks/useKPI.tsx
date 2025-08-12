/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Label from '@clayui/label';
import {useNavigate} from 'react-router-dom';
import useSWR from 'swr';

import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import SearchBuilder from '../../../core/SearchBuilder';
import {AccountType} from '../../../enums/Account';
import {MarketplaceCategory} from '../../../enums/Categories';
import {orderTypeLabel} from '../../../enums/Order';
import {ProductType} from '../../../enums/Product';
import useListTypeDefinition from '../../../hooks/useListTypeDefinition';
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
	.lambda('categoryNames', MarketplaceCategory.PAYMENT_METHODS)
	.build();

const lowCodeConfigurationsPublishedFilter = new SearchBuilder()
	.lambda('specificationValues', ProductType.LOW_CODE_CONFIGURATION)
	.build();

const partnershipIntegrationFilter = new SearchBuilder()
	.lambda('specificationValues', AccountType.TECHNOLOGY_PARTNER)
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

type ProjectsUsingMarketplaceModalBodyProps = {
	projectsUsingMarkeplaceApps: [
		string,
		{
			accountName: string;
			orders: {
				creatorEmailAddress: string;
				id: number;
				orderTypeExternalReferenceCode: string;
				projects: {
					key: string;
					name: string;
				}[];
			}[];
		},
	][];
};

function ProjectsUsingMarketplaceModalBody({
	projectsUsingMarkeplaceApps,
}: ProjectsUsingMarketplaceModalBodyProps) {
	return (
		<ul className="list-group list-group-flush">
			{projectsUsingMarkeplaceApps.map(([key, project], index) => (
				<li className="list-group-item" key={index}>
					<div className="mb-1">
						<strong className="text-dark">
							[{index + 1}] {key}
						</strong>{' '}
						–{' '}
						<span className="text-dark">{project.accountName}</span>
					</div>

					{project.orders?.map((order, index) => {
						const exactMatch =
							order.orderTypeExternalReferenceCode.startsWith(
								'KOR-'
							) || order.projects.length === 1;

						return (
							<details
								className="border-0 list-group-item py-1"
								key={index}
							>
								<summary>
									<span className="fw-semibold">
										<Label
											displayType={
												exactMatch
													? 'success'
													: 'warning'
											}
										>
											{exactMatch
												? 'Exact Match'
												: 'Multiple projects'}
										</Label>{' '}
										Order #{order.id}
									</span>{' '}
									–{' '}
									<span className="text-muted">
										{
											orderTypeLabel[
												order.orderTypeExternalReferenceCode as keyof typeof orderTypeLabel
											]
										}
									</span>
								</summary>

								<p>Created by: {order.creatorEmailAddress}</p>

								{!exactMatch && (
									<p>
										Projects:{' '}
										{order.projects.map(
											(customerProject, index) => (
												<Label key={index}>
													{customerProject.name}
												</Label>
											)
										)}
									</p>
								)}
							</details>
						);
					})}
				</li>
			))}
		</ul>
	);
}

const useKPI = () => {
	const {data: liferayVersionsPicklist} =
		useListTypeDefinition('LIFERAY-VERSIONS');

	const liferayQuarterlyVersionEntries =
		liferayVersionsPicklist?.listTypeEntries.filter((entry) =>
			entry.externalReferenceCode.includes('Q')
		);

	const liferayQuarterlyVersions = JSON.stringify({
		'specificationValues|liferayVersion':
			liferayQuarterlyVersionEntries?.map((entry) => entry.name),
	});

	const liferayQuarterlyVersionsAndConnectors = JSON.stringify({
		'categoryNames': MarketplaceCategory.PAYMENT_METHODS,
		'specificationValues|liferayVersion':
			liferayQuarterlyVersionEntries?.map((entry) => entry.name),
	});

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

	const {data, ...swr} = useSWR('metrics/kpi', async () => {
		const [
			{
				data: {
					metrics: {
						connectorQuartelyRelease,
						lowCodeConfigurationsPublished,
						partnershipIntegration,
						supportingQuartelyRelease,
					},
				},
			},
			projectsKPI,
		] = await Promise.all([
			HeadlessCommerceAdminCatalog.getProductsDashboardKPI({
				connectorQuartelyRelease: connectorQuartelyReleaseFilter,
				lowCodeConfigurationsPublished:
					lowCodeConfigurationsPublishedFilter,
				partnershipIntegration: partnershipIntegrationFilter,
				supportingQuartelyRelease: supportingQuartelyReleaseFilter,
			}),
			marketplaceOAuth2.getMarketplaceProjectsKPI(),
		]);

		const projectsUsingMarkeplaceApps = Object.entries(
			projectsKPI?.projectsUsingMarketplace ?? {}
		);

		return {
			kpis: [
				{
					...getAnnualTargetValues(
						kpiProjectUsingMarketplaceApps,
						projectsUsingMarkeplaceApps.length
					),
					colors: ['#9CE269', '#D4F3BE'],
					onClick: projectsUsingMarkeplaceApps.length
						? () =>
								modal.onOpenModal({
									body: (
										<ProjectsUsingMarketplaceModalBody
											projectsUsingMarkeplaceApps={
												projectsUsingMarkeplaceApps
											}
										/>
									),
									header: 'New Projects Using Marketplace Apps',
									size: 'lg',
								})
						: null,
					title: 'New Projects Using Marketplace Apps',
				},
				{
					onClick: () =>
						navigate(
							`/publishers?filter={"customFields/AccountType":["${AccountType.TECHNOLOGY_PARTNER}"]}&filterSchema=administratorPublishers`
						),
					...getAnnualTargetValues(
						kpiPartnershipIntegration,
						partnershipIntegration.totalCount
					),
					colors: ['#FFB46E', '#FFE9D4'],
					title: 'Technology Partnership With Integrations',
				},
				{
					onClick: () =>
						navigate(
							`/apps?filter=${liferayQuarterlyVersions}&filterSchema=administratorApps`
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
							`/apps?filter=${liferayQuarterlyVersionsAndConnectors}&filterSchema=administratorApps`
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
							`/apps?filter={"specificationValues|appType":"${ProductType.LOW_CODE_CONFIGURATION}"}&filterSchema=administratorApps`
						),
					title: 'Low Code Configurations Published',
				},
			],
			projectsKPI,
		};
	});

	return {data, ...swr};
};

export default useKPI;
