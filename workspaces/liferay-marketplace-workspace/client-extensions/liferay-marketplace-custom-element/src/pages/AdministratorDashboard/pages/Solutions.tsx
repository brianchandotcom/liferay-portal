/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Label from '@clayui/label';
import {ComponentProps} from 'react';
import {useNavigate} from 'react-router-dom';

import ListView from '../../../components/ListView';
import {FilterOption} from '../../../components/ListView/components/ManagementToolbar';
import {ListViewTypes} from '../../../components/ListView/hooks/ListViewContext';
import Page from '../../../components/Page';
import SearchBuilder from '../../../core/SearchBuilder';
import {
	ProductTypeVocabulary,
	ProductWorkflowDisplayType,
	ProductWorkflowStatusCode,
	ProductWorkflowStatusLabel,
} from '../../../enums/Product';
import i18n from '../../../i18n';
import HeadlessCommerceAdminCatalog from '../../../services/rest/HeadlessCommerceAdminCatalog';
import {formatDate} from '../../../utils/date';

const productStatuses = [
	ProductWorkflowStatusCode.APPROVED,
	ProductWorkflowStatusCode.DRAFT,
	ProductWorkflowStatusCode.PENDING,
];

export default function Solutions() {
	const navigate = useNavigate();

	const productStatusFilters: FilterOption[] = productStatuses.map(
		(status) => ({
			name: ProductWorkflowStatusLabel[status] || '',
			onClick: (dispatch) => {
				dispatch({
					payload: {filters: {filter: {statusCode: `${status}`}}},
					type: ListViewTypes.SET_FILTERS,
				});
			},
		})
	);

	return (
		<Page pageRendererProps={{className: 'border py-2'}} title="Solutions">
			<ListView<Product>
				id="administrator-apps"
				managementToolbarProps={{
					filterItems: [
						{
							children: productStatusFilters,
							name: 'Status',
						},
					],
					visible: true,
				}}
				resource={function getProducts({
					filters,
					keywords,
					page,
					pageSize,
					sort,
				}) {
					const searchBuilder = new SearchBuilder().lambda(
						'categoryNames',
						ProductTypeVocabulary.SOLUTION
					);

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
							'filter': searchBuilder.build(),
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
					actions: [
						{
							name: i18n.translate('view-details'),
							onClick: (row) => {
								navigate(`/solutions/${row.productId}`);
							},
						},
					],
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
					navigateTo: ({productId}) => `/solutions/${productId}`,
				}}
			/>
		</Page>
	);
}
