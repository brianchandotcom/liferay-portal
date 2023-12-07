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

	const projectNameField = Object.values(
		placedOrder.customFields
	).find((field) => Object.keys(field === 'Project Name'));

	const {isPaidApp} = getProductPriceModel(product);

	return (
		<div className="app-details-page-container mt-6">
			<div className="app-details-body-container">
				<DetailedCard
					cardIconAltText="Details Icon"
					cardTitle="Details"
					clayIcon="order-form-tag"
				>
					<div className="mb-2 mt-7 row">
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
						<p className="col">{projectNameField || '-'}</p>
					</div>
					<div className="mb-2 row">
						<h5 className="col-6">Purchased by</h5>
						<p className="col">{placedOrder.author}</p>
					</div>
					<div className="row">
						<h5 className="col-6">Purchase Order Number</h5>
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
								(order: PlacedOrderItems) => {
									const optionName = JSON.parse(
										order.options
									);

									return (
										<div
											className={classNames('mb-2 row', {
												'justify-content-end': !isPaidApp,
											})}
											key={order.id}
										>
											{isPaidApp && (
												<>
													<p className="col-5 text-capitalize">
														{optionName[0].value ||
															''}
													</p>
													<p className="col-3">
														{order.quantity}
													</p>
												</>
											)}
											<p className="col-4 text-right">
												{order.price.priceFormatted}
											</p>
										</div>
									);
								}
							)}
						</div>
					</div>
					<div className="justify-content-between mb-2 row">
						<h5 className="col">Subtotal</h5>
						<p className="col-3 text-right">
							{placedOrder.summary.subtotalFormatted || ''}
						</p>
					</div>
					<div className="justify-content-between mb-2 row">
						<h5 className="col">Subtotal Discount</h5>
						<p className="col-3 text-right">
							{placedOrder.summary.totalDiscountValueFormatted ||
								''}
						</p>
					</div>
					<div className="justify-content-between mb-2 row">
						<h5 className="col">Coupon Code</h5>
						<p className="col-3 text-right">
							{placedOrder.couponCode || '-'}
						</p>
					</div>
					<div className="justify-content-between mb-2 row">
						<h5 className="col">Tax/VAT</h5>
						<p className="col-3 text-right">
							{placedOrder.summary.taxValueFormatted || ''}
						</p>
					</div>
					<div className="justify-content-between row">
						<h5 className="col">Total</h5>
						<p className="col-3 text-right">
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
