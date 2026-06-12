/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useMemo} from 'react';
import {useOutletContext, useSearchParams} from 'react-router-dom';
import useSWR from 'swr';

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
import HeadlessAIHub from '../../../../../services/rest/HeadlessAIHub';
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
			"We've sent the order form to your email via DocuSign. Please review, sign, and return it to confirm your subscription — once received, we'll provision your AI Hub and notify you by email.",
		title: 'Awaiting Signature',
		type: 'info',
	},
};

const AIHubDetails = () => {
	const {placedOrder, selectedAccount} = useOutletContext<{
		placedOrder: PlacedOrder;
		selectedAccount: Account;
	}>();
	const [searchParams, setSearchParams] = useSearchParams();

	const orderStatusCode = placedOrder?.orderStatusInfo
		?.code as OrderWorkflowStatusCode;

	const {data: aiHubApplication} = useSWR(
		`/ai-hub-application/${selectedAccount.externalReferenceCode}`,
		() => {
			return HeadlessAIHub.getAIHubApplicationByExternalReferenceCode(
				selectedAccount.externalReferenceCode
			);
		}
	);

	const {data: tokenOrdersData} = useFetch<APIResponse<PlacedOrder>>(
		`o/headless-commerce-delivery-order/v1.0/channels/${Liferay.CommerceContext.commerceChannelId}/accounts/${Liferay.CommerceContext.account?.accountId}/placed-orders`,
		{
			params: {
				filter: SearchBuilder.eq(
					'orderTypeExternalReferenceCode',
					OrderTypes.AI_HUB_TOKEN
				),
				nestedFields: 'placedOrderItems',
				pageSize: 100,
			},
		}
	);

	const activationKeyAlertStatus = useMemo(() => {
		if (orderStatusCode === OrderWorkflowStatusCode.COMPLETED) {
			return activationKeyAlertStatuses.completed;
		}

		return activationKeyAlertStatuses.pending;
	}, [orderStatusCode]);

	useEffect(() => {
		if (
			searchParams.has('tokenPurchaseSuccess') &&
			tokenOrdersData?.items
		) {
			const completedOrders = (tokenOrdersData.items ?? []).filter(
				(order) => {
					const isCompleted =
						order.orderStatusInfo?.code ===
						OrderWorkflowStatusCode.COMPLETED;

					const isPaid =
						order.paymentStatus === PaymentStatus.PAID ||
						order.paymentStatusInfo?.code === PaymentStatus.PAID;

					return isCompleted && isPaid;
				}
			);

			if (completedOrders.length) {
				const lastOrder = completedOrders[0];
				const sku = lastOrder.placedOrderItems?.[0]?.sku;
				const tokensAmount = sku.split(' ')[0] || '';

				const toastMessage = [
					tokensAmount,
					i18n.translate('liferay-tokens-was-purchased-successfully'),
				].join(' ');

				Liferay.Util.openToast({
					message: toastMessage,
					title: i18n.translate('success'),
					type: 'success',
				});

				const newSearchParams = new URLSearchParams(searchParams);
				newSearchParams.delete('tokenPurchaseSuccess');
				setSearchParams(newSearchParams, {replace: true});
			}
		}
	}, [searchParams, tokenOrdersData, setSearchParams]);

	const orderMetadata = placedOrder
		? JSON.parse(placedOrder.customFields[OrderCustomFields.ORDER_METADATA])
		: {};

	const aiHubForm = orderMetadata?.aiHubForm || {};
	const aiHubURL = aiHubForm?.aiHubURL ?? 'https://ai.hub.liferay.com';

	return (
		<div>
			{activationKeyAlertStatus && (
				<ActivationKeyAlert
					{...activationKeyAlertStatus}
					className="license-alert"
					dismissible={false}
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
							className: 'mb-4',
							title: i18n.translate('ai-hub-account-name'),
							value:
								aiHubForm.fullName ??
								aiHubApplication?.accountName,
						},
						{
							className: 'mb-4',
							title: i18n.translate('ai-administration-email'),
							value:
								aiHubForm.businessEmailAddress ??
								aiHubApplication?.administratorEmailAddress,
						},
						{
							title: i18n.translate('token-monthly-allowance'),
							value: aiHubForm.tokenMonthlyAllowance ?? '1000',
						},
						{
							title: i18n.translate('ai-hub-url'),
							value: (
								<a
									href={
										aiHubURL.startsWith('http')
											? aiHubURL
											: `https://${aiHubURL}`
									}
									rel="noopener noreferrer"
									target="_blank"
								>
									{aiHubURL}
								</a>
							),
						},
					]}
					orientation={Orientation.VERTICAL}
				/>
			</DetailedCard>

			{!!tokenOrdersData?.items.length && (
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

										return Intl.NumberFormat().format(
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
