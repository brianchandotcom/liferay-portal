/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useOutletContext, useParams} from 'react-router-dom';

import './App.scss';

import classNames from 'classnames';

import {DetailedCard} from '../../../../components/DetailedCard/DetailedCard';
import getProductPriceModel from '../../../GetAppPage/utils/getProductPriceModel';
import {formatDate} from '../../../PublishedAppsDashboard/PublishedDashboardPageUtil';

const App = () => {
	const {orderId} = useParams();
	const {placedOrder, product} = useOutletContext<any>();

	const projectNameField = product.customFields.find(
		(field: {name: string}) => field.name === 'Project Name'
	);

	const {isPaidApp} = getProductPriceModel(product);


	return (
		<div className="app-details-page-container mt-6">
			<div className="app-details-body-container">
				<DetailedCard
					cardIconAltText="Details Icon"
					cardTitle="Details"
					clayIcon="order-form-tag"
				>
					<div className="mb-2 mt-4 row">
						<h5 className="col-6">Order ID</h5>
						<p className="col">{orderId}</p>
					</div>
					<div className="mb-2 row">
						<h5 className="col-6">Order Date</h5>
						<p className="col">
							{formatDate(placedOrder.createDate)}
						</p>
					</div>
					<div className="mb-2 row">
						<h5 className="col-6">Customer Account</h5>
						<p className="col">{placedOrder.account}</p>
					</div>
					<div className="mb-2 row">
						<h5 className="col-6">Customer Project</h5>
						<p className="col">
							{projectNameField.customValue.data || '-'}
						</p>
					</div>
					<div className="mb-2 row">
						<h5 className="col-6">Purchased by</h5>
						<p className="col">{placedOrder.author}</p>
					</div>
					<div className="row">
						<h5 className="col-6">Purchase Number</h5>
						<p className="col">
							{placedOrder.purchaseOrderNumber || '-'}
						</p>
					</div>
				</DetailedCard>
				<DetailedCard
					cardIconAltText="Summary Icon"
					cardTitle="Summary"
					clayIcon="shopping-cart"
				>
					{isPaidApp && (
						<div className="justify-content-center mb-2 mt-4 row">
							<h5 className="col-3">Type</h5>
							<h5 className="col-1">Qty</h5>
						</div>
					)}
					<div
						className={classNames('row mb-2', {
							'mt-6': !isPaidApp,
						})}
					>
						<h5 className="col">License Price</h5>
						<div className="col-8">
							{placedOrder.placedOrderItems.map(
								(order: PlacedOrderItems) => <div
								className={classNames('row', {
									'justify-content-end': !isPaidApp,
								})}
								key={order.id}
							>
								{isPaidApp && (
									<>
										<p className="col text-capitalize">
											{order.sku.toLowerCase() ||
												''}
										</p>
										<p className="col">
											{order.quantity}
										</p>
									</>
								)}
								<p className="col-3">
									{order.price.priceFormatted}
								</p>
							</div>
							)}
						</div>
					</div>
					<div className="justify-content-between mb-2 row">
						<h5 className="col-2">Subtotal</h5>
						<p className="col-2">
							{placedOrder.summary.subtotalFormatted || ''}
						</p>
					</div>
					<div className="justify-content-between mb-2 row">
						<h6 className="col">Subtotal Discount</h6>
						<p className="col-2">
							{placedOrder.summary.totalDiscountValueFormatted ||
								''}
						</p>
					</div>
					<div className="justify-content-between mb-2 row">
						<h6 className="col">Coupon Code</h6>
						<p className="col-2">{placedOrder.couponCode || '-'}</p>
					</div>
					<div className="justify-content-between mb-2 row">
						<h5 className="col">Tax/VAT</h5>
						<p className="col-2">
							{placedOrder.summary.taxValueFormatted || ''}
						</p>
					</div>
					<div className="justify-content-between row">
						<h5 className="col">Total</h5>
						<p className="col-2">
							{placedOrder.summary.totalFormatted || ''}
						</p>
					</div>
				</DetailedCard>
				{placedOrder.placedOrderBillingAddress && (
					<DetailedCard
						cardIconAltText="Location Icon"
						cardTitle="Address"
						clayIcon="geolocation"
					>
						<div className="mb-2 mt-4 row">
							<h5 className="col-6">Billing Address</h5>
							<div className="col-6">
								<p>
									{placedOrder.placedOrderBillingAddress
										.street1 || ''}
									,
								</p>
								{placedOrder.placedOrderBillingAddress
									.street2 && (
									<p>
										{
											placedOrder
												.placedOrderBillingAddress
												.street2
										}
									</p>
								)}
								{placedOrder.placedOrderBillingAddress
									.street3 && (
									<p>
										{
											placedOrder
												.placedOrderBillingAddress
												.street3
										}
									</p>
								)}
								<p>
									{placedOrder.placedOrderBillingAddress.city}
									,
								</p>
								<p>
									{placedOrder.placedOrderBillingAddress
										.regionISOCode || ''}
									,{' '}
									{placedOrder.placedOrderBillingAddress
										.zip || ''}
									,
								</p>
								<p>
									{placedOrder.placedOrderBillingAddress
										.countryISOCode || ''}
								</p>
							</div>
						</div>
					</DetailedCard>
				)}
			</div>
		</div>
	);
};

export default App;