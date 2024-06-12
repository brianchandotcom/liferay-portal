/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import DropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {Status} from '@clayui/modal/lib/types';
import {formatDistance} from 'date-fns';

import {DashboardEmptyTable} from '../../../../../components/DashboardTable/DashboardEmptyTable';
import Table from '../../../../../components/Table/Table';
import SearchBuilder from '../../../../../core/SearchBuilder';
import i18n from '../../../../../i18n';
import {Liferay} from '../../../../../liferay/liferay';
import CommerceSelectAccountImpl from '../../../../../services/rest/CommerceSelectAccount';
import HeadlessCommerceAdminCatalogImpl from '../../../../../services/rest/HeadlessCommerceAdminCatalog';

type AppsTableProps = {
	items: Order[];
};

const ORDER_STATUS = {
	completed: 'success',
	pending: 'info',
	processing: 'secondary',
};

const PAYMENT_STATUS = {
	authorized: 'success',
	cancelled: 'warning',
	completed: 'success',
	failed: 'danger',
	pending: 'info',
};

const OrdersTable: React.FC<AppsTableProps> = ({items}) => {
	if (!items.length) {
		return (
			<DashboardEmptyTable
				description1={i18n.translate(
					'purchase-and-install-new-apps-and-they-will-show-up-here'
				)}
				description2={i18n.translate('click-on-add-apps-to-start')}
				icon="grid"
				title={i18n.translate('no-orders-yet')}
			/>
		);
	}

	const itemsDropdown = [
		{
			id: 1,
			name: i18n.translate('customer-dashboard'),
			onClick: (order: Order) =>
				CommerceSelectAccountImpl.selectAccount(order?.accountId).then(
					() => {
						Liferay.CommerceContext.account = {
							accountId: order?.accountId,
						};

						Liferay.Util.navigate(
							Liferay.ThemeDisplay.getLayoutURL().replace(
								'/administrator-dashboard',
								`/customer-dashboard`
							)
						);
					}
				),
		},
		{
			id: 2,
			name: i18n.translate('publisher-dashboard'),
			onClick: async (order: Order) => {
				const product =
					await HeadlessCommerceAdminCatalogImpl.getProducts(
						new URLSearchParams({
							filter: new SearchBuilder()
								.eq(
									'name',
									`${order.orderItems[0]?.name?.en_US}`
								)
								.build(),
							nestedFields: 'catalog',
						})
					);

				const accountId = product.items[0]?.catalog?.accountId;

				return CommerceSelectAccountImpl.selectAccount(accountId).then(
					() => {
						Liferay.CommerceContext.account = {
							accountId,
						};

						Liferay.Util.navigate(
							Liferay.ThemeDisplay.getLayoutURL().replace(
								'/administrator-dashboard',
								`/publisher-dashboard`
							)
						);
					}
				);
			},
		},
	];

	return (
		<>
			<h1 className="mb-3">Recent Orders</h1>

			<Table
				columns={[
					{
						key: 'id',
						render: (id) => (
							<span className="font-weight-bold">{id}</span>
						),
						title: i18n.translate('id'),
					},
					{
						key: 'orderItems',
						render: (orderItems) => orderItems[0]?.name?.en_US,
						title: i18n.translate('app-name'),
					},
					{
						key: 'account',
						render: (account) => account?.name,
						title: i18n.translate('user-account'),
					},
					{
						key: 'orderTypeExternalReferenceCode',
						title: i18n.translate('app-type'),
					},
					{
						key: 'totalFormatted',
						title: i18n.translate('amount'),
					},
					{
						key: 'orderStatusInfo',
						render: (orderStatusInfo) => (
							<ClayLabel
								className="text-nowrap"
								displayType={
									ORDER_STATUS[
										orderStatusInfo?.label as keyof typeof ORDER_STATUS
									] as Status
								}
							>
								{orderStatusInfo?.label_i18n}
							</ClayLabel>
						),
						title: i18n.translate('order-status'),
					},
					{
						key: 'paymentStatusInfo',
						render: (paymentStatusInfo) => (
							<ClayLabel
								className="text-nowrap"
								displayType={
									PAYMENT_STATUS[
										paymentStatusInfo?.label as keyof typeof PAYMENT_STATUS
									] as Status
								}
							>
								{paymentStatusInfo?.label_i18n}
							</ClayLabel>
						),
						title: i18n.translate('payment-status'),
					},
					{
						key: 'createDate',
						render: (createDate) => (
							<span className="ml-2 text-capitalize text-nowrap">
								{formatDistance(
									new Date(createDate),
									Date.now(),
									{addSuffix: true}
								)}
							</span>
						),
						title: i18n.translate('created-at'),
					},
					{
						align: 'right',
						key: 'accountId',
						render: (_, order) => (
							<DropDown
								closeOnClick
								filterKey="name"
								trigger={
									<div>
										<ClayButton
											aria-label="Actio dropdown"
											displayType="unstyled"
										>
											<ClayIcon symbol="ellipsis-v" />
										</ClayButton>
									</div>
								}
							>
								<DropDown.ItemList items={itemsDropdown}>
									{(item: any) => (
										<DropDown.Item
											key={item.name}
											onClick={() => {
												item.onClick(order);
											}}
										>
											{item?.name}
										</DropDown.Item>
									)}
								</DropDown.ItemList>
							</DropDown>
						),
						title: '',
					},
				]}
				rows={items}
			/>
		</>
	);
};

export default OrdersTable;
