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
	OrderTypes,
	OrderWorkflowStatusCode,
	PaymentStatus,
} from '../../../../../enums/Order';
import {useFetch} from '../../../../../hooks/useFetch';
import i18n from '../../../../../i18n';
import {Liferay} from '../../../../../liferay/liferay';
import {safeJSONParse} from '../../../../../utils/util';
import ActivationKeyAlert from '../Licenses/LicenseAlert';

const activationKeyAlertStatuses = {
	completed: {
		description:
			'Provisioning is complete and your subscription is now active. Access your hub via the URL below to start using your monthly token allowance.',
		title: 'Your AI Hub is Ready',
	},
	pending: {
		description:
			'Our team is currently reviewing your request. An administrator will approve your access, and you will receive a notification via email as soon as your account is activated.',
		dismissable: false,
		title: 'Your Beta Access Request is Pending',
		type: 'info',
	},
};

const AIHubDetails = () => {
	const {placedOrder, selectedAccount} = useOutletContext<any>();
	const [searchParams] = useSearchParams();

	const orderStatusCode = placedOrder?.orderStatusInfo
		?.code as OrderWorkflowStatusCode;

	const {data: tokenOrdersData} = useFetch<APIResponse<PlacedOrder>>(
		`o/headless-commerce-delivery-order/v1.0/channels/${Liferay.CommerceContext.commerceChannelId}/accounts/${Liferay.CommerceContext.account?.accountId}/placed-orders`,
		{
			params: {
				filter: new SearchBuilder()
					.eq(
						'orderTypeExternalReferenceCode',
						OrderTypes.AI_HUB_TOKEN
					)
					.and()
					.eq(
						'orderStatusInfo/code',
						OrderWorkflowStatusCode.COMPLETED,
						{unquote: true}
					)
					.build(),
				nestedFields: 'placedOrderItems',
				pageSize: 100,
			},
		}
	);

	const activationKeyAlertStatus = useMemo(() => {
		if (orderStatusCode !== OrderWorkflowStatusCode.COMPLETED) {
			return activationKeyAlertStatuses.pending;
		}

		if (
			orderStatusCode === OrderWorkflowStatusCode.COMPLETED &&
			searchParams.has('next-steps')
		) {
			return activationKeyAlertStatuses.completed;
		}

		return null;
	}, []);

	const hasCompletedTokenOrders = useMemo(() => {
		const tokens = tokenOrdersData?.items ?? [];

		if (!tokens.length) {
			return false;
		}

		return tokens.some((order) => {
			const isCompleted =
				order.orderStatusInfo?.code ===
				OrderWorkflowStatusCode.COMPLETED;

			const isPaid =
				order.paymentStatus === PaymentStatus.PAID ||
				order.paymentStatusInfo?.code === PaymentStatus.PAID;

			return isCompleted && isPaid;
		});
	}, [tokenOrdersData]);

	const orderMetadata = placedOrder
		? JSON.parse(placedOrder.customFields[OrderCustomFields.ORDER_METADATA])
		: {};

	const aiHubForm = orderMetadata?.aiHubForm || {};

	return (
		<div>
			{activationKeyAlertStatus && (
				<ActivationKeyAlert
					{...activationKeyAlertStatus}
					className="license-alert"
					symbol="check-circle"
				>
					{activationKeyAlertStatus.description}
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
							value: aiHubForm.fullName,
						},
						{
							className: 'mt-4',
							title: i18n.translate('ai-administration-email'),
							value: aiHubForm.businessEmailAddress,
						},
						{
							className: 'mt-4',
							title: i18n.translate('token-monthly-allowance'),
							value: aiHubForm.tokenMonthlyAllowance,
						},
						{
							className: 'mt-4',
							title: i18n.translate('ai-hub-url'),
							value: aiHubForm.aiHubURL ? (
								<a
									href={
										aiHubForm.aiHubURL.startsWith('http')
											? aiHubForm.aiHubURL
											: `https://${aiHubForm.aiHubURL}`
									}
									rel="noopener noreferrer"
									target="_blank"
								>
									{aiHubForm.aiHubURL}
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
						resource={`o/headless-commerce-delivery-order/v1.0/channels/${Liferay.CommerceContext.commerceChannelId}/accounts/${Liferay.CommerceContext.account?.accountId}/placed-orders?filter=${SearchBuilder.eq('orderTypeExternalReferenceCode', OrderTypes.AI_HUB_TOKEN)}&nestedFields=placedOrderItems&sort=createDate:desc`}
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

										if (!item) {
											return '-';
										}

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
