/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Label from '@clayui/label';

import ListView from '../../../components/ListView';
import {ListViewTypes} from '../../../components/ListView/hooks/ListViewContext';
import Page from '../../../components/Page';
import SearchBuilder from '../../../core/SearchBuilder';
import i18n from '../../../i18n';
import HeadlessAdminUser from '../../../services/rest/HeadlessAdminUser';
import {formatDate} from '../../../utils/date';

export function Publishers() {
	return (
		<Page pageRendererProps={{className: 'border py-2'}} title="Publishers">
			<ListView<Account>
				id="administrator-publishers"
				paginationOptions={{displayType: 'always'}}
				managementToolbarProps={{
					filterItems: [
						{
							label: 'Technology Partner',
							onClick: (dispatch) => {
								dispatch({
									payload: {
										filters: {
											filter: {
												'customFields/AccountType':
													'Technology Partner',
											},
										},
									},
									type: ListViewTypes.SET_FILTERS,
								});
							},
						},
						{
							label: 'Strategic Partner',
							onClick: (dispatch: any) => {
								dispatch({
									payload: {
										filters: {
											filter: {
												'customFields/AccountType':
													'Strategic Partner',
											},
										},
									},
									type: ListViewTypes.SET_FILTERS,
								});
							},
						},
					],
					visible: true,
				}}
				resource={async function getAccounts({
					filters,
					keywords,
					page,
					pageSize,
					sort,
				}) {
					const searchBuilder = new SearchBuilder().eq(
						'type',
						'supplier'
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

					return HeadlessAdminUser.getAccounts(
						new URLSearchParams({
							filter: searchBuilder.build(),
							page: page.toString(),
							pageSize: pageSize.toString(),
							sort: sort.key
								? `${sort.key}:${sort.direction}`
								: 'dateCreated:desc',
						})
					);
				}}
				tableProps={{
					actions: [
						{
							disabled: true,
							name: i18n.translate('edit'),
						},
						{
							disabled: true,
							name: i18n.translate('view'),
						},
					],
					columns: [
						{
							id: 'name',
							name: 'Name',
							render: (name, {logoURL}) => (
								<div>
									<img
										className="mr-2 rounded"
										draggable={false}
										height={42}
										src={logoURL}
										width={42}
									/>
									<span className="font-weight-bold">
										{name}
									</span>
								</div>
							),
							sortable: true,
						},
						{
							id: 'id',
							name: 'ID',
						},
						{
							id: 'externalReferenceCode',
							name: 'External Reference Code',
						},
						{
							id: 'customFields',
							name: 'Type',
							render: (customFields) => {
								const type = customFields?.find(
									({name}) => name === 'AccountType'
								);

								return (
									type?.customValue.data ||
									'Marketplace Publisher'
								);
							},
						},
						{
							id: 'dateCreated',
							name: 'Created at',
							render: formatDate,
						},
						{
							id: 'status',
							name: 'Status',
							render: (status) => {
								return (
									<Label
										displayType={
											status === 0 ? 'success' : 'warning'
										}
									>
										{status === 0 ? 'Approved' : 'Pending'}
									</Label>
								);
							},
						},
					],
				}}
			/>
		</Page>
	);
}
