/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useNavigate} from 'react-router-dom';

import ListView from '../../../../components/ListView';
import Page from '../../../../components/Page';
import {PaymentStatus as PaymentStatusCode} from '../../../../enums/Order';
import i18n from '../../../../i18n';
import {Liferay} from '../../../../liferay/liferay';
import HeadlessCommerceAdminOrder from '../../../../services/rest/HeadlessCommerceAdminOrder';
import PaymentStatus from '../../components/Order/PaymentStatus/PaymentStatus';

const Orders = () => {
	const navigate = useNavigate();

	return (
		<Page
			description={i18n.translate(
				'section-that-shows-the-latest-sales-made'
			)}
			pageRendererProps={{
				className: 'border py-2 rounded-lg mb-8',
			}}
			title={i18n.translate('last-orders')}
		>
			<ListView<Order>
				defaultFilters={{
					filter: 'totalAmount gt 0',
				}}
				emptyStateProps={{title: i18n.translate('no-orders-yet')}}
				id="finance-dashboard-orders"
				managementToolbarProps={{
					filterSchema: 'financeOrders',
					searchVisible: true,
					visible: true,
				}}
				resource="o/headless-commerce-admin-order/v1.0/orders?nestedFields=account,orderItems&sort=createDate:desc"
				tableProps={{
					actions: [
						{
							disabled(item) {
								return (
									item.paymentStatus ===
										PaymentStatusCode.PAID ||
									item.paymentStatus ===
										PaymentStatusCode.CANCELLED
								);
							},
							name: i18n.translate('mark-as-paid'),
							onClick: async (order: Order, mutate) => {
								try {
									await HeadlessCommerceAdminOrder.patchOrder(
										order.id,
										{
											paymentStatus:
												PaymentStatusCode.PAID,
										}
									);
									mutate((response: Order) => response, {
										revalidate: true,
									});
									Liferay.Util.openToast({
										message: 'Order marked as paid.',
										type: 'success',
									});
								}
								catch (error) {
									Liferay.Util.openToast({
										message: 'Oops! Something went wrong.',
										type: 'danger',
									});
								}
							},
						},
						{
							name: i18n.translate('view-details'),
							onClick: (order: Order) => {
								navigate('/order/' + order.id);
							},
						},
					],
					columns: [
						{
							clickable: true,
							id: 'id',
							name: 'ID',
							render: (id) => <b>{id}</b>,
						},
						{
							id: 'orderDate',
							name: 'Date',
							render: (isoString) => {
								const date = new Date(isoString as string);

								return (
									<div className="d-flex flex-column justify-content-center">
										<p className="mb-0 pt-1">
											{date.toLocaleDateString('en-US', {
												day: 'numeric',
												month: 'short',
												year: 'numeric',
											})}
										</p>
										<p className="mb-0 text-muted">
											{date.toLocaleTimeString('en-US', {
												hour: 'numeric',
												minute: '2-digit',
											})}
										</p>
									</div>
								);
							},
						},
						{
							id: 'account',
							name: 'Account',
							render: (account) => account?.name,
						},
						{
							id: 'orderItems',
							name: 'App',
							render: (orderItems) => orderItems[0].name?.en_US,
						},
						{
							id: 'paymentStatusInfo',
							name: 'Status',
							render: (paymentStatus) => {

								// this is where it should update

								return (
									<PaymentStatus
										paymentStatus={paymentStatus.code}
									/>
								);
							},
						},
						{
							id: 'totalWithTaxAmountFormatted',
							name: 'Total',
						},
					],
					navigateTo: (order) => `/order/${order.id}`,
				}}
			/>
		</Page>
	);
};
export default Orders;
