/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useNavigate} from 'react-router-dom';

import ListView from '../../../../components/ListView';
import OrderStatus from '../../../../components/OrderStatus';
import Page from '../../../../components/Page';
import SearchBuilder from '../../../../core/SearchBuilder';
import {OrderTypes, PaymentStatus} from '../../../../enums/Order';
import i18n from '../../../../i18n';
import {Liferay} from '../../../../liferay/liferay';
import PaymentStatusBadge from '../../../FinanceDashboard/components/PaymentStatus/PaymentStatusBadge';
import {useCustomerDashboardOutletContext} from '../../CustomerDashboardOutlet';

const searchParams = new URLSearchParams({
	filter: SearchBuilder.in('orderTypeExternalReferenceCode', [
		OrderTypes.ADDONS,
		OrderTypes.CMP,
		OrderTypes.DXP,
	]),
	nestedFields: 'placedOrderItems',
	sort: 'createDate:desc',
});

const getViewDetailsPath = (orderId: string, orderType: string) => {
	let path = orderId;

	if ([OrderTypes.CMP, OrderTypes.DXP].includes(orderType as OrderTypes)) {
		path = `${orderId}/activation-keys`;
	}

	return path;
};

const LiferayProductsListView = () => {
	const {selectedAccount} = useCustomerDashboardOutletContext();

	const navigate = useNavigate();

	return (
		<Page
			description={i18n.translate(
				'manage-your-products-purchased-from-the-marketplace'
			)}
			title={i18n.translate('products')}
		>
			<div className="customer-products">
				<ListView<PlacedOrder>
					emptyStateProps={{
						className:
							'border px-4 py-6 d-flex align-items-center flex-column justify-content-center',
						type: 'BLANK',
					}}
					id={`customer-products${selectedAccount?.id}`}
					initialContext={{
						pageSize: 20,
						paginationDeltaOptions: [20, 40, 80, 120],
					}}
					resource={`o/headless-commerce-delivery-order/v1.0/channels/${Liferay.CommerceContext.commerceChannelId}/accounts/${selectedAccount?.id}/placed-orders?${searchParams}`}
					tableProps={{
						actions: [
							{
								name: i18n.translate('view-details'),
								onClick: (row: PlacedOrder) =>
									navigate(
										`${getViewDetailsPath(String(row.id), row.orderTypeExternalReferenceCode)}`
									),
							},
						],
						columns: [
							{
								clickable: true,
								id: 'placedOrderItems',
								name: i18n.translate('name'),
								render: (placedOrderItems) => {
									const placedOrderItem =
										placedOrderItems[0] || [];

									return (
										<div style={{width: 200}}>
											<img
												alt="App Image"
												className="order-details-publisher-table-icon"
												src={placedOrderItem?.thumbnail}
											/>

											<span className="font-weight-semi-bold ml-2">
												{placedOrderItem?.name}
											</span>
										</div>
									);
								},
								size: 'sm',
							},
							{
								clickable: true,
								id: 'author',
								name: i18n.translate('purchased-by'),
								render: (author, {createDate}) => (
									<div className="d-flex flex-column">
										<span className="dashboard-table-row-text">
											{author}
										</span>

										<span className="dashboard-table-row-purchased-date text-black-50">
											{new Date(
												createDate
											).toLocaleDateString('en-US', {
												day: 'numeric',
												month: 'short',
												year: 'numeric',
											})}
										</span>
									</div>
								),
							},
							{
								id: 'id',
								name: i18n.translate('order-id'),
							},
							{
								id: 'orderStatusInfo',
								name: 'Status',
								render: (_, item) => {
									if (
										item.paymentStatus ===
										PaymentStatus.PENDING
									) {
										return (
											<PaymentStatusBadge
												paymentStatus={
													PaymentStatus.PENDING
												}
											/>
										);
									}

									return <OrderStatus placedOrder={item} />;
								},
							},
						],
						navigateTo: (item) =>
							`${getViewDetailsPath(String(item.id), item.orderTypeExternalReferenceCode)}`,
					}}
				/>
			</div>
		</Page>
	);
};

export default LiferayProductsListView;
