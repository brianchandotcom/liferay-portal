/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Label from '@clayui/label';
import {ComponentProps} from 'react';
import {useSearchParams} from 'react-router-dom';

import ListView, {ListViewProps} from '../../../../components/ListView';
import {
	AppActions,
	ListViewTypes,
} from '../../../../components/ListView/hooks/ListViewContext';
import Page from '../../../../components/Page';
import SearchBuilder from '../../../../core/SearchBuilder';
import {
	ProductType,
	ProductTypeVocabulary,
	ProductWorkflowDisplayType,
} from '../../../../enums/Product';
import useListTypeDefinition from '../../../../hooks/useListTypeDefinition';
import i18n from '../../../../i18n';
import HeadlessCommerceAdminCatalog from '../../../../services/rest/HeadlessCommerceAdminCatalog';
import {formatDate} from '../../../../utils/date';
import {LIFERAY_VERSION_PICKLIST} from '../../../PublisherDashboard/pages/NewAppFlow/constants';

type AdministratorAppsListViewProps = {
	filter?: string;
	listViewProps?: Partial<ListViewProps<Product>>;
};

export function AdministratorAppsListView({
	filter,
	listViewProps,
}: AdministratorAppsListViewProps) {
	return (
		<ListView<Product>
			id="administrator-apps"
			resource={function getProducts({
				filters,
				keywords,
				page,
				pageSize,
				sort,
			}) {
				const searchBuilder = new SearchBuilder().lambda(
					'categoryNames',
					ProductTypeVocabulary.APP
				);

				if (filters.filter) {
					for (const [key, value] of Object.entries(filters.filter)) {
						searchBuilder.and().lambdaContains(key, String(value));
					}
				}

				if (keywords) {
					searchBuilder.and().contains('name', keywords);
				}

				return HeadlessCommerceAdminCatalog.getProducts(
					new URLSearchParams({
						'filter': filter
							? `${filter} and ${searchBuilder.build()}`
							: `${searchBuilder.build()}`,
						'nestedFields': 'catalog,productSpecifications',
						'page': page.toString(),
						'pageSize': pageSize.toString(),
						'productSpecifications.pageSize': '-1',
						'sort': sort.key
							? `${sort.key}:${sort.direction}`
							: 'createDate:desc',
					})
				);
			}}
			tableProps={{
				columns: [
					{
						clickable: true,
						id: 'name',
						name: i18n.translate('name'),
						render: (name, {thumbnail}) => (
							<div>
								<img
									alt="App Image"
									className="app-details-page-table-icon"
									src={thumbnail}
								/>

								<span className="font-weight-semi-bold ml-2 text-nowrap">
									{name?.en_US}
								</span>
							</div>
						),
						sortable: true,
					},
					{
						id: '__marketplaceProduct',
						name: i18n.translate('app-type'),
						render: (marketplaceProduct) => (
							<div className="text-capitalize">
								{marketplaceProduct.appType}
							</div>
						),
					},
					{
						id: 'catalog',
						name: i18n.translate('publisher-name'),
						render: (catalog) => catalog.name,
					},
					{
						id: 'modifiedDate',
						name: i18n.translate('last-update'),
						render: (modifiedDate) => formatDate(modifiedDate),
						sortable: true,
					},
					{
						id: 'createDate',
						name: i18n.translate('published-at'),
						render: (createDate) => formatDate(createDate),
					},
					{
						id: 'workflowStatusInfo',
						name: i18n.translate('status'),
						render: (
							workflowStatusInfo: Product['workflowStatusInfo']
						) => (
							<Label
								displayType={
									ProductWorkflowDisplayType[
										workflowStatusInfo.code as keyof typeof ProductWorkflowDisplayType
									] as ComponentProps<
										typeof Label
									>['displayType']
								}
							>
								{workflowStatusInfo.label}
							</Label>
						),
					},
				],
				navigateTo: ({productId}) => `/apps/${productId}`,
			}}
			{...listViewProps}
		/>
	);
}

export default function Apps() {
	const {data} = useListTypeDefinition(LIFERAY_VERSION_PICKLIST);
	const [searchParams] = useSearchParams();

	const pageFilter = new URLSearchParams(searchParams).get('filter');

	const LiferayVersion =
		data?.listTypeEntries?.map((version) => version.name) ?? [];

	return (
		<Page
			pageRendererProps={{className: 'border py-2 rounded-lg'}}
			title="Apps"
		>
			<AdministratorAppsListView
				filter={pageFilter as string}
				listViewProps={{
					managementToolbarProps: {
						filterItems: [
							{
								children: Object.keys(ProductType).map(
									(key) => ({
										name: ProductType[
											key as keyof typeof ProductType
										],
										onClick: (
											dispatch: React.Dispatch<AppActions>
										) =>
											dispatch({
												payload: {
													filters: {
														filter: {
															specificationValues:
																ProductType[
																	key as keyof typeof ProductType
																],
														},
													},
												},
												type: ListViewTypes.SET_FILTERS,
											}),
									})
								),
								name: i18n.translate('app-type'),
							},
							{
								children: LiferayVersion?.map((version) => ({
									name: version,
									onClick: (
										dispatch: React.Dispatch<AppActions>
									) => {
										dispatch({
											payload: {
												filters: {
													filter: {
														specificationValues:
															version,
													},
												},
											},
											type: ListViewTypes.SET_FILTERS,
										});
									},
								})),
								name: i18n.translate('liferay-version'),
							},
						],
						visible: true,
					},
					paginationOptions: {displayType: 'always'},
				}}
			/>
		</Page>
	);
}
