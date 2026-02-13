/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { useNavigate, useOutletContext } from 'react-router-dom';

import ListView from '../../../../components/ListView';
import OrderStatus from '../../../../components/OrderStatus';
import Page from '../../../../components/Page';
import { useMarketplaceContext } from '../../../../context/MarketplaceContext';
import SearchBuilder from '../../../../core/SearchBuilder';
import { OrderWorkflowStatusCode } from '../../../../enums/Order';
import { PaymentStatus } from '../../../../enums/Order';
import i18n from '../../../../i18n';
import PaymentStatusBadge from '../../../FinanceDashboard/components/PaymentStatus/PaymentStatusBadge';

const searhParams = new URLSearchParams({
	nestedFields: 'placedOrderItems',
	sort: 'createDate:desc',
});

const LiferayServicesListView = () => {
	const { selectedAccount } = useOutletContext<any>();
	const { channel } = useMarketplaceContext();
	const navigate = useNavigate();

	return (
		<Page
			description={i18n.translate(
				'manage-your-liferay-services-purchased-from-the-marketplace'
			)}
			title={i18n.translate('liferay-services')}
		>
			<div className="customer-liferay-services">
				<ListView<PlacedOrder>
					defaultFilters={{
						filter: new SearchBuilder()
							.inEqualNumbers('orderStatusInfo/code', [
								OrderWorkflowStatusCode.IN_PROGRESS,
								OrderWorkflowStatusCode.COMPLETED,
							])
							.build(),
					}}
					emptyStateProps={{
						className:
							'border px-4 py-6 d-flex align-items-center flex-column justify-content-center',
						type: 'BLANK',
					}}
					id={`customer-liferay-services${selectedAccount?.id}`}
					initialContext={{
						pageSize: 20,
						paginationDeltaOptions: [20, 40, 80, 120],
					}}
					resource={`o/headless-commerce-delivery-order/v1.0/channels/${channel?.id}/accounts/${selectedAccount?.id}/placed-orders?${searhParams}`}
					tableProps={{
						actions: [
							{
								name: i18n.translate('view-details'),
								onClick: (row: PlacedOrder) =>
									navigate(`${row.id}`),
							},
							{
								disabled: true,
								name: i18n.translate('download-invoice'),
							},
							{
								disabled: true,
								name: i18n.translate('purchase-upgrade'),
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
										<div style={{ width: 200 }}>
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
								render: (author, { createDate }) => (
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
								render: (orderStatusInfo, item) => {
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

									return (
										<OrderStatus
											orderStatus={orderStatusInfo.label}
										>
											{orderStatusInfo.label}
										</OrderStatus>
									);
								},
							},
						],
						navigateTo: (item) => `${item.id}`,
					}}
					transformData={(response) => {
						const items = response.items.filter(
							(item) =>
								item.paymentStatus === PaymentStatus.PAID ||
								item.paymentStatus === PaymentStatus.PENDING
						);

						return {
							...response,
							items,
							totalCount:
								response.totalCount -
								(items.length - response.items.length),
						};
					}}
				/>
			</div>
		</Page>
	);
};

export default LiferayServicesListView;
