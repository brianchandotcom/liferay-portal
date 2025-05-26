/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Page from '../../../components/Page';
import Label from '@clayui/label';
import {ComponentProps} from 'react';

import ListView from '../../../components/ListView';
import {ProductTypeVocabulary, ProductWorkflowDisplayType} from '../../../enums/Product';
import i18n from '../../../i18n';
import HeadlessCommerceAdminCatalog from '../../../services/rest/HeadlessCommerceAdminCatalog';
import {formatDate} from '../../../utils/date';
import SearchBuilder from '../../../core/SearchBuilder';
import {useNavigate} from 'react-router-dom';

export default function Solutions() {
	const navigate = useNavigate()
	return (
		<Page title="Solutions" pageRendererProps={{className: 'border py-2'}}>
			<ListView<Product>
				id="administrator-apps"
				resource={function getProducts({keywords, page, pageSize, sort, filters}) {
					const searchBuilder = new SearchBuilder().lambda(
						'categoryNames',
						ProductTypeVocabulary.SOLUTION
					)

					if (filters.filter) {
						for (const [key, value] of Object.entries(
							filters.filter
						)) {
							searchBuilder.and().eq(key, String(value));
						}
					}

					if (keywords) {
						searchBuilder.and().contains('name', keywords);
					}

					return HeadlessCommerceAdminCatalog.getProducts(
						new URLSearchParams({
							filter: searchBuilder.build(),
							'nestedFields': 'catalog,productSpecifications',
							'page': page.toString(),
							'pageSize': pageSize.toString(),
							'productSpecifications.pageSize': '-1',
							'sort': sort.key ? `${sort.key}:${sort.direction}` :'createDate:desc',					})
					);
				}}
				managementToolbarProps={{visible: true}}
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
							id: 'modifiedDate',
							name: i18n.translate('last-update'),
							render: (modifiedDate) => formatDate(modifiedDate),
							sortable: true,
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
					actions: [
						{
							name: i18n.translate('view-details'),
							onClick: (row) => {
								navigate(`/solutions/${row.productId}`)
							}
						}
					],
					navigateTo: ({productId}) => `/solutions/${productId}`,
				}}
			/>
		</Page>
	);
}
