/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import {useParams} from 'react-router-dom';

import {DetailedCard} from '../../../../../components/DetailedCard/DetailedCard';
import {PageRenderer} from '../../../../../components/Page';
import QATable, {Orientation} from '../../../../../components/QATable';
import Table from '../../../../../components/Table/Table';
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
	if (address) {
		if (!Object.keys(address).length) {
			return '-';
		}

		const locale = Liferay.ThemeDisplay.getDefaultLanguageId().replace(
			'_',
			'-'
		);

		const countryNames = new Intl.DisplayNames([locale], {type: 'region'});

		return `${address?.street1}, ${address?.city}, ${address?.regionISOCode} ${address?.zip} , ${countryNames.of(address?.countryISOCode)}`;
	}

	return '-';
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
	let paragraph = '-';

	if (content?.length && !!content) {
		paragraph = content;
	}

	return <p className="mb-2 mt-1">{paragraph}</p>;
}

const OrderDetails = () => {
	const {orderId} = useParams();

	const {data, error, isLoading, mutate} = useAdminOrderProduct(orderId!!);

	const {order, payments, product} = data || {};

	const currencyCode = order?.currencyCode || 'USD';

	return (
		<PageRenderer
			className="app-details-header d-flex flex-column w-100"
			error={error}
			isLoading={isLoading}
		>
			<OrderDetailsHeader
				MarkAsPaid={() => (
					<Button
						displayType="secondary"
						onClick={async () => {
							try {
								const updatedOrder =
									await HeadlessCommerceAdminOrder.patchOrder(
										orderId as string,
										{
											paymentStatus:
												PaymentStatusCode.PAID,
										}
									);

								mutate(
									{
										...data!,
										order: {
											...data!.order,
											paymentStatus:
												PaymentStatusCode.PAID,

											...updatedOrder,
										},
									},
									{revalidate: false}
								);

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
						}}
					>
						{i18n.translate('mark-as-paid')}
					</Button>
				)}
				orderId={orderId as string}
				paymentStatusCode={order?.paymentStatusInfo.code as number}
			/>

			<div className="d-flex mt-5">
				<DetailedCard
					cardIconAltText="order-form-pencil"
					cardTitle="Account Details"
					className="mr-5 w-100"
					clayIcon="order-form-pencil"
				>
					<QATable
						items={[
							{
								title: 'Account Name',
								value: textWrapper(order?.account?.name),
							},
							{
								title: 'Liferay User Email',
								value: textWrapper(order?.creatorEmailAddress),
							},
							{
								title: 'Billing Email',
								value: '-',
							},
							{
								title: 'Project',
								value: textWrapper(order?.projectName),
							},
							{
								title: 'Address',
								value: textWrapper(
									formatAddress(
										order?.billingAddress as BillingAddress
									)
								),
							},
							{
								title: 'VAT Number',
								value: textWrapper(order?.account?.taxId),
							},
						]}
						orientation={Orientation.VERTICAL}
					/>
				</DetailedCard>

				<DetailedCard
					cardIconAltText="change-list"
					cardTitle="Transaction Details"
					className="w-100"
					clayIcon="change-list"
				>
					<QATable
						items={[
							{
								title: 'Purchase Date',
								value: textWrapper(
									formatDate(order?.createDate)
								),
							},
							{
								title: 'Payment Method',
								value: textWrapper(
									order?.paymentMethod ===
										'paypal-integration'
										? 'Paypal Integration'
										: 'Invoice'
								),
							},
							{
								title: 'Transaction ID',
								value: textWrapper(order?.transactionId),
							},
							{
								title: 'Fulfillment Date',
								value: textWrapper(
									formatDate(order?.orderDate)
								),
							},
							{
								title: 'Status',
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
								title: 'Error Reason',
								value: payments?.items[0]?.errorMessages,
								visible: !!payments?.items[0]?.errorMessages,
							},
							{
								title: 'Paid Date',
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
				cardTitle="Order Details"
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
												{skuOption.skuOptionValueKey}
											</p>
										</span>
									</div>
								);
							},
							title: 'App Name',
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
							title: 'Publisher',
						},
						{
							bodyClass: 'order-item-display',
							key: 'quantity',
							title: 'Quantity',
						},
						{
							bodyClass: 'order-item-display',
							key: 'finalPrice',
							render: (finalPrice) =>
								textWrapper(
									formatCurrency(finalPrice, currencyCode)
								),
							title: 'Net Price',
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
							title: 'VAT',
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
							title: 'Total',
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
