/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useParams} from 'react-router-dom';

import {DetailedCard} from '../../../../../components/DetailedCard/DetailedCard';
import {PageRenderer} from '../../../../../components/Page';
import QATable, {Orientation} from '../../../../../components/QATable';
import Table from '../../../../../components/Table/Table';
import {CurrencyAbbreviation} from '../../../../../enums/CurrencyAbbreviation';
import {PaymentStatus as PaymentStatusCode} from '../../../../../enums/Order';
import {ProductSpecificationKey} from '../../../../../enums/Product';
import useAdminOrderProduct from '../../../../../hooks/useAdminOrderProduct';
import i18n from '../../../../../i18n';
import {Liferay} from '../../../../../liferay/liferay';
import HeadlessCommerceAdminOrder from '../../../../../services/rest/HeadlessCommerceAdminOrder';
import {safeJSONParse} from '../../../../../utils/util';
import OrderDetailsHeader from '../../../components/Order/OrderDetailsHeader';
import PaymentStatus from '../../../components/Order/PaymentStatus/PaymentStatus';

function formatAddress(address: BillingAddress) {
	if (!address || !Object.keys(address).length) {
		return '-';
	}

	const displayNames = new Intl.DisplayNames(
		[Liferay.ThemeDisplay.getBCP47LanguageId()],
		{type: 'region'}
	);

	return [
		address.street1,
		address.city,
		address.regionISOCode,
		address.zip,
		displayNames.of(address.countryISOCode),
	]
		.filter(Boolean)
		.join(', ');
}

function formatCurrency(price: number, currencyCode: string) {
	if (!price) {
		return '-';
	}

	return price.toLocaleString(currencyCode, {
		currency: currencyCode,
		currencyDisplay: 'narrowSymbol',
		maximumFractionDigits: 2,
		minimumFractionDigits: 2,
		style: 'currency',
	});
}

function formatDate(date: string | undefined) {
	if (!date) {
		return '-';
	}

	return new Date(date).toLocaleDateString('en-US', {
		day: 'numeric',
		hour: 'numeric',
		minute: '2-digit',
		month: 'short',
		year: 'numeric',
	});
}

function textWrapper(content: string | undefined) {
	if (!content) {
		return '-';
	}

	return <p className="mb-2 mt-1">{content}</p>;
}

const OrderDetails = () => {
	const {orderId} = useParams();

	const {data, error, isLoading, mutate} = useAdminOrderProduct(orderId!!);

	const {order, payments, product} = data || {};

	const currencyCode = order?.currencyCode || CurrencyAbbreviation.USD;

	return (
		<PageRenderer
			className="app-details-header d-flex flex-column w-100"
			error={error}
			isLoading={isLoading}
		>
			<OrderDetailsHeader
				onClick={async () =>
					HeadlessCommerceAdminOrder.patchOrder(orderId as string, {
						paymentStatus: PaymentStatusCode.PAID,
					})
						.then((updatedOrder) => {
							mutate(
								{
									...data!,
									order: {
										...data!.order,
										...updatedOrder,
										paymentStatus: PaymentStatusCode.PAID,
									},
								},
								{revalidate: false}
							);

							Liferay.Util.openToast({
								message: 'Order marked as paid.',
								type: 'success',
							});
						})
						.catch(() =>
							Liferay.Util.openToast({
								message: 'Oops! Something went wrong.',
								type: 'danger',
							})
						)
				}
				orderId={orderId as string}
				paymentStatusCode={order?.paymentStatusInfo.code as number}
			/>

			<div className="d-flex mt-5">
				<DetailedCard
					cardIconAltText="order-form-pencil"
					cardTitle={i18n.translate('account-details')}
					className="mr-5 w-100"
					clayIcon="order-form-pencil"
				>
					<QATable
						items={[
							{
								title: i18n.translate('account-name'),
								value: textWrapper(order?.account?.name),
							},
							{
								title: i18n.translate('liferay-user-email'),
								value: textWrapper(order?.creatorEmailAddress),
							},
							{
								title: i18n.translate('billing-email'),
								value: '-',
							},
							{
								title: i18n.translate('project'),
								value: textWrapper(order?.projectName),
							},
							{
								title: i18n.translate('address'),
								value: textWrapper(
									formatAddress(
										order?.billingAddress as BillingAddress
									)
								),
							},
							{
								title: i18n.translate('vat-number'),
								value: textWrapper(order?.account?.taxId),
							},
						]}
						orientation={Orientation.VERTICAL}
					/>
				</DetailedCard>

				<DetailedCard
					cardIconAltText="change-list"
					cardTitle={i18n.translate('transaction-details')}
					className="w-100"
					clayIcon="change-list"
				>
					<QATable
						items={[
							{
								title: i18n.translate('purchase-date'),
								value: textWrapper(
									formatDate(order?.createDate)
								),
							},
							{
								title: i18n.translate('payment-method'),
								value: textWrapper(
									order?.paymentMethod ===
										'paypal-integration'
										? 'Paypal Integration'
										: 'Invoice'
								),
							},
							{
								title: i18n.translate('transaction-id'),
								value: textWrapper(order?.transactionId),
							},
							{
								title: i18n.translate('fulfillment-date'),
								value: textWrapper(
									formatDate(order?.orderDate)
								),
							},
							{
								title: i18n.translate('payment-status'),
								value: (
									<PaymentStatus
										paymentStatus={
											order?.paymentStatusInfo
												.code as number
										}
									/>
								),
							},
							{
								title: i18n.translate('error-details'),
								value: payments?.items[0]?.errorMessages,
								visible:
									!!payments?.items[0]?.errorMessages &&
									order?.paymentStatus ===
										PaymentStatusCode.FAILED,
							},
							{
								title: i18n.translate('paid-date'),
								value: textWrapper(
									formatDate(payments?.items?.[0]?.createDate)
								),
								visible:
									order?.paymentStatus ===
									PaymentStatusCode.PAID,
							},
						]}
						orientation={Orientation.VERTICAL}
					/>
				</DetailedCard>
			</div>

			<DetailedCard
				cardIconAltText="order-form"
				cardTitle={i18n.translate('order-details')}
				className="mt-5 pb-0 w-100"
				clayIcon="order-form"
			>
				<Table
					columns={[
						{
							bodyClass: 'order-item-display',
							key: 'options',
							render: (options) => {
								const [skuOption] = safeJSONParse(options, [
									{skuOptionValueKey: 'Standard'},
								]);

								return (
									<div className="alignt-items-center d-flex mt-2">
										<img
											alt="App Icon"
											className="mr-2 order-details-app-icon rounded"
											draggable={false}
											src={product?.thumbnail}
										/>
										<span>
											<strong>
												{product?.name.en_US}
											</strong>
											<p className="finance-dashboard-text-secondary text-capitalize">
												{skuOption?.skuOptionValueKey}
											</p>
										</span>
									</div>
								);
							},
							title: i18n.translate('app-name'),
						},
						{
							bodyClass: 'order-item-display',
							key: 'id',
							render: () =>
								textWrapper(
									product?.productSpecifications?.find(
										(specification) =>
											specification.specificationKey ===
											ProductSpecificationKey.APP_DEVELOPER_NAME
									)?.value.en_US || ''
								),
							title: i18n.translate('publisher'),
						},
						{
							bodyClass: 'order-item-display',
							key: 'quantity',
							title: i18n.translate('quantity'),
						},
						{
							bodyClass: 'order-item-display',
							key: 'finalPrice',
							render: (finalPrice) =>
								textWrapper(
									formatCurrency(finalPrice, currencyCode)
								),
							title: i18n.translate('net-price'),
						},
						{
							bodyClass: 'order-item-display',
							key: 'finalPriceWithTaxAmount',
							render: (finalPriceWithTaxAmount, _) =>
								textWrapper(
									formatCurrency(
										finalPriceWithTaxAmount - _?.finalPrice,
										currencyCode
									)
								),
							title: i18n.translate('vat'),
						},
						{
							bodyClass: 'order-item-display',
							key: 'finalPriceWithTaxAmount',
							render: (finalPriceWithTaxAmount) =>
								textWrapper(
									formatCurrency(
										finalPriceWithTaxAmount,
										currencyCode
									)
								),
							title: i18n.translate('total'),
						},
					]}
					hasHover={false}
					rows={order?.orderItems || []}
				/>
			</DetailedCard>
		</PageRenderer>
	);
};

export default OrderDetails;
