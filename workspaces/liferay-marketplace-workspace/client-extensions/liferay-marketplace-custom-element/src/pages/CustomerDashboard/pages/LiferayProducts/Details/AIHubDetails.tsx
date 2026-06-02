/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import {useOutletContext, useSearchParams} from 'react-router-dom';

import {DetailedCard} from '../../../../../components/DetailedCard/DetailedCard';
import ListView from '../../../../../components/ListView';
import OrderStatus from '../../../../../components/OrderStatus';
import QATable, {Orientation} from '../../../../../components/QATable';
import SearchBuilder from '../../../../../core/SearchBuilder';
import {
	OrderCustomFields,
	OrderWorkflowStatusCode,
} from '../../../../../enums/Order';
import {useFetch} from '../../../../../hooks/useFetch';
import i18n from '../../../../../i18n';
import {Liferay} from '../../../../../liferay/liferay';
import {safeJSONParse} from '../../../../../utils/util';
import ActivationKeyAlert from '../Licenses/LicenseAlert';

const AIHubDetails = () => {
	const {placedOrder, product, selectedAccount} = useOutletContext<any>();
	const [searchParams] = useSearchParams();

	const {data: tokenOrdersData} = useFetch<any>(
		selectedAccount?.id && product?.id
			? `o/headless-commerce-delivery-order/v1.0/channels/${Liferay.CommerceContext.commerceChannelId}/accounts/${selectedAccount?.id}/placed-orders`
			: null,
		{
			params: {
				filter: `orderTypeExternalReferenceCode eq 'AI_HUB_TOKEN' and orderStatusInfo/code eq 0`,
				nestedFields: 'placedOrderItems',
				pageSize: 100,
			},
		}
	);

	const hasCompletedTokenOrders = useMemo(() => {
		const tokens = tokenOrdersData?.items;

		if (!tokens || !tokens.length) {
			return false;
		}

		return tokens.some((order: any) => {
			const isCompleted = order.orderStatusInfo?.code === 0;
			const isPaid =
				order.paymentStatus === 0 ||
				order.paymentStatusInfo?.code === 0;

			return isCompleted && isPaid;
		});
	}, [tokenOrdersData]);

	const orderStatusCode = placedOrder?.orderStatusInfo
		?.code as OrderWorkflowStatusCode;

	const orderMetadata = placedOrder
		? JSON.parse(placedOrder.customFields[OrderCustomFields.ORDER_METADATA])
		: {};

	const orderAdditionalInformation = orderMetadata?.aiHubForm || {};

	return (
		<div>
			{orderStatusCode !== OrderWorkflowStatusCode.COMPLETED && (
				<ActivationKeyAlert
					className="license-alert"
					dismissible={false}
					symbol="check-circle"
					title="Your Beta Access Request is Pending"
					type="info"
				>
					Our team is currently reviewing your request. An
					administrator will approve your access, and you will receive
					a notification via email as soon as your account is
					activated.
				</ActivationKeyAlert>
			)}

			{orderStatusCode === OrderWorkflowStatusCode.COMPLETED &&
				searchParams.has('next-steps') && (
					<ActivationKeyAlert
						className="license-alert"
						symbol="check-circle"
						title="Your AI Hub is Ready"
					>
						Provisioning is complete and your subscription is now
						active. Access your hub via the URL below to start using
						your monthly token allowance.
					</ActivationKeyAlert>
				)}

			<DetailedCard
				cardIconAltText="Profile Icon"
				cardTitle={i18n.translate('ai-hub-details')}
				className="tokens-card"
				clayIcon="order-form-tag"
			>
				<QATable
					columns={2}
					items={[
						{
							className: 'mt-4',
							title: i18n.translate('ai-hub-account-name'),
							value: orderAdditionalInformation.fullName,
						},
						{
							className: 'mt-4',
							title: i18n.translate('ai-administration-email'),
							value: orderAdditionalInformation.businessEmailAddress,
						},
						{
							className: 'mt-4',
							title: i18n.translate('token-monthly-allowance'),
							value: orderAdditionalInformation.tokenMonthlyAllowance,
						},
						{
							className: 'mt-4',
							title: i18n.translate('ai-hub-url'),
							value: orderAdditionalInformation.aiHubUrl ? (
								<a
									href={
										orderAdditionalInformation.aiHubUrl.startsWith(
											'http'
										)
											? orderAdditionalInformation.aiHubUrl
											: `https://${orderAdditionalInformation.aiHubUrl}`
									}
									rel="noopener noreferrer"
									target="_blank"
								>
									{orderAdditionalInformation.aiHubUrl}
								</a>
							) : (
								'-'
							),
						},
					]}
					orientation={Orientation.VERTICAL}
				/>
			</DetailedCard>

			{hasCompletedTokenOrders && (
				<DetailedCard
					cardIconAltText="Profile Icon"
					cardTitle={i18n.translate('token-past-purchases')}
					className="mt-4 pb-0 tokens-card"
					clayIcon="coin"
				>
					<ListView<PlacedOrder>
						emptyStateProps={{
							className:
								'border px-4 py-6 d-flex align-items-center flex-column justify-content-center',
							type: 'BLANK',
						}}
						id={`token-past-purchases-${selectedAccount?.id}`}
						initialContext={{
							pageSize: 5,
							paginationDeltaOptions: [5, 10, 20],
						}}
						resource={`o/headless-commerce-delivery-order/v1.0/channels/${Liferay.CommerceContext.commerceChannelId}/accounts/${selectedAccount?.id}/placed-orders?filter=${SearchBuilder.eq('orderTypeExternalReferenceCode', 'AI_HUB_TOKEN')}&nestedFields=placedOrderItems&sort=createDate:desc`}
						tableProps={{
							actions: [
								{
									name: i18n.translate('download-invoice'),
									onClick: (row: PlacedOrder) =>
										window.open(
											`/o/headless-commerce-delivery-order/v1.0/placed-orders/${row.id}/print`,
											'_blank'
										),
								},
							],
							columns: [
								{
									id: 'createDate',
									name: i18n.translate('date'),
									render: (createDate) => {
										const date = new Date(
											createDate as string
										);

										return date.toLocaleDateString(
											'en-US',
											{
												day: 'numeric',
												month: 'short',
												year: 'numeric',
											}
										);
									},
								},
								{
									id: 'placedOrderItems',
									name: i18n.translate('tokens'),
									render: (placedOrderItems) => {
										const item = placedOrderItems?.[0];
										if (!item) {return '-';}
										const options = safeJSONParse<any[]>(
											item.options,
											[]
										);
										const optionValue =
											options[0]
												?.skuOptionValueNames?.[0] ||
											options[0]?.skuOptionValueName ||
											'';

										return (
											optionValue
												.replace(/[^\d]/g, '')
												.trim() || '-'
										);
									},
								},
								{
									id: 'summary',
									name: i18n.translate('amount'),
									render: (summary) =>
										summary?.totalFormatted || '',
								},
								{
									id: 'orderStatusInfo',
									name: i18n.translate('status'),
									render: (_, item) => (
										<OrderStatus placedOrder={item} />
									),
								},
							],
						}}
					/>
				</DetailedCard>
			)}
		</div>
	);
};

export default AIHubDetails;
