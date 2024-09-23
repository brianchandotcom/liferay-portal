/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {ReactNode} from 'react';
import {useOutletContext, useParams} from 'react-router-dom';

import {DetailedCard} from '../../../../../components/DetailedCard/DetailedCard';
import i18n from '../../../../../i18n';
import formatLocaleCurrency from '../../../../../utils/formatLocaleCurrency';
import {isCloudProduct} from '../../../../../utils/productUtils';
import {safeJSONParse} from '../../../../../utils/util';
import getProductPriceModel from '../../../../GetApp/utils/getProductPriceModel';
import {formatDate} from '../../../../PublisherDashboard/PublisherDashboardPageUtil';

import './App.scss';

const App = () => {
	const {orderId} = useParams();
	const {placedOrder, product} = useOutletContext<any>();

	const projectNameField =
		Object.values(placedOrder.customFields).find((field) =>
			Object.keys(field === 'Project Name')
		) || '-';

	const isCloud = isCloudProduct(product);
	const {isPaidApp} = getProductPriceModel(product);

	return (
		<div className="app-details-page-container mt-6">
			<div className="app-details-body-container">
				<DetailedCard
					cardIconAltText="Details Icon"
					cardTitle={i18n.translate('details')}
					clayIcon="order-form-tag"
				>
					<div className="mb-2 mt-7 row">
						<div className="col-6 h5">
							{i18n.translate('order-id')}
						</div>
						<p className="col">{orderId}</p>
					</div>
					<div className="mb-2 row">
						<div className="col-6 h5">
							{i18n.translate('order-date')}
						</div>
						<p className="col">
							{formatDate(placedOrder.createDate)}
						</p>
					</div>
					<div className="mb-2 row">
						<div className="col-6 h5">
							{i18n.translate('customer-account')}
						</div>
						<p className="col">{placedOrder.account}</p>
					</div>
					<div className="mb-2 row">
						<div className="col-6 h5">
							{i18n.translate('customer-roject')}
						</div>
						<p className="col">{projectNameField as ReactNode}</p>
					</div>
					<div className="mb-2 row">
						<div className="col-6 h5">
							{i18n.translate('purchased-by')}
						</div>
						<p className="col">{placedOrder.author}</p>
					</div>
					<div className="row">
						<div className="col-6 h5">Purchase Order Number</div>
						<p className="col">
							{placedOrder.purchaseOrderNumber || '-'}
						</p>
					</div>
				</DetailedCard>

				<DetailedCard
					cardIconAltText="Summary Icon"
					cardTitle={i18n.translate('summary')}
					clayIcon="shopping-cart"
				>
					{isPaidApp && (
						<div className="justify-content-center mb-2 mt-4 row">
							<div className="col-3 h5">
								{i18n.translate('type')}
							</div>
							<div className="col-1 h5">
								{i18n.translate('qty')}
							</div>
						</div>
					)}
					<div
						className={classNames('row mb-2', {
							'mt-6': !isPaidApp,
						})}
					>
						<div className="col h5">
							{i18n.translate('license-price')}
						</div>
						<div className="col-8">
							{placedOrder.placedOrderItems.map(
								(order: PlacedOrderItems) => {
									const optionName = safeJSONParse(
										order.options,
										[]
									);

									return (
										<div
											className={classNames('mb-2 row', {
												'justify-content-end':
													!isPaidApp,
											})}
											key={order.id}
										>
											{isPaidApp && (
												<>
													<p className="col-5 text-capitalize">
														{isCloud
															? 'Standard'
															: optionName[0]
																	?.value ||
																''}
													</p>
													<p className="col-3">
														{order.quantity}
													</p>
												</>
											)}
											<p className="col-4 text-right">
												{formatLocaleCurrency(
													order.quantity *
														order.price.price
												)}
											</p>
										</div>
									);
								}
							)}
						</div>
					</div>

					<div className="justify-content-between mb-2 row">
						<div className="col h5">
							{i18n.translate('subtotal')}
						</div>
						<p className="col-3 text-right">
							{formatLocaleCurrency(
								placedOrder.summary.subtotal
							) || ''}
						</p>
					</div>
					<div className="justify-content-between mb-2 row">
						<div className="col h5">
							{i18n.translate('subtotal-discount')}
						</div>
						<p className="col-3 text-right">
							{formatLocaleCurrency(
								placedOrder.summary.totalDiscountValue
							) || ''}
						</p>
					</div>
					<div className="justify-content-between mb-2 row">
						<div className="col h5">
							{i18n.translate('coupon-code')}
						</div>
						<p className="col-3 text-right">
							{placedOrder.couponCode || '-'}
						</p>
					</div>
					<div className="justify-content-between mb-2 row">
						<div className="col h5">
							{i18n.translate('tax-vat')}
						</div>
						<p className="col-3 text-right">
							{formatLocaleCurrency(
								placedOrder.summary.taxValue
							) || ''}
						</p>
					</div>
					<div className="justify-content-between row">
						<div className="col h5">{i18n.translate('total')}</div>
						<p className="col-3 text-right">
							{formatLocaleCurrency(placedOrder.summary.total) ||
								''}
						</p>
					</div>
				</DetailedCard>

				{placedOrder.placedOrderBillingAddress && (
					<DetailedCard
						cardIconAltText="Location Icon"
						cardTitle={i18n.translate('address')}
						clayIcon="geolocation"
					>
						<div className="mb-2 mt-4 row">
							<div className="col-6 h5">
								{i18n.translate('billing-address')}
							</div>
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
