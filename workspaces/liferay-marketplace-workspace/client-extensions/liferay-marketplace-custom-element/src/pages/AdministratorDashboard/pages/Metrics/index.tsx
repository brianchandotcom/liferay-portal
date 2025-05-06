/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import {Status} from '@clayui/modal/lib/types';
import {formatDistance} from 'date-fns';
import {useMemo} from 'react';

import ListView from '../../../../components/ListView';
import SearchBuilder from '../../../../core/SearchBuilder';
import {
	OrderTypes,
	OrderWorkflowDisplayType,
	PaymentWorkflowDisplayType,
} from '../../../../enums/Order';
import i18n from '../../../../i18n';
import {Liferay} from '../../../../liferay/liferay';
import CommerceSelectAccount from '../../../../services/rest/CommerceSelectAccount';
import HeadlessCommerceAdminOrder from '../../../../services/rest/HeadlessCommerceAdminOrder';
import InfoCard from '../../components/InfoCard';
import useAccountsMetrics from '../../hooks/useAccountsMetrics';
import useAnalyticsViewsMetrics from '../../hooks/useAnalyticsViewsMetrics';
import useOrderMetrics from '../../hooks/useOrderMetrics';

function redirectTo(path: string) {
	return async function (order: Order) {
		await CommerceSelectAccount.selectAccount(order.accountId);

		Liferay.CommerceContext.account = {
			accountId: order.accountId,
		};

		Liferay.Util.navigate(
			Liferay.ThemeDisplay.getLayoutURL().replace(
				'/administrator-dashboard',
				path
			)
		);
	};
}

const getTotalAmountCurrency = (amount = 0) =>
	new Intl.NumberFormat('en-US', {
		currency: 'USD',
		style: 'currency',
	}).format(amount);

const Metrics = () => {
	const {data: accounts} = useAccountsMetrics('week');
	const {visitorsMetric} = useAnalyticsViewsMetrics();
	const {data: orderMetrics} = useOrderMetrics('week');

	const infoCard = useMemo(
		() => [
			{
				growth: accounts?.growth ?? 0,
				growthContext: `+${accounts?.lastPeriod ?? 0} this week `,
				symbol: 'users',
				title: i18n.translate('accounts'),
				value: accounts?.totalCount ?? 0,
			},
			{
				symbol: 'dollar-symbol',
				title: (
					<span>
						{i18n.translate('income')}{' '}
						&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;
					</span>
				),
				value: getTotalAmountCurrency(orderMetrics?.paidAmount),
			},
			{
				growth: orderMetrics?.growth ?? 0,
				growthContext: `+${orderMetrics?.lastPeriod ?? 0} this week `,
				symbol: 'shopping-cart',
				title: i18n.translate('orders'),
				value: orderMetrics?.totalCount ?? 0,
			},
			{
				symbol: 'analytics',
				title: 'Site Visitors',
				value: visitorsMetric ?? 0,
			},
		],
		[
			accounts?.growth,
			accounts?.lastPeriod,
			accounts?.totalCount,
			orderMetrics?.growth,
			orderMetrics?.lastPeriod,
			orderMetrics?.paidAmount,
			orderMetrics?.totalCount,
			visitorsMetric,
		]
	);

	return (
		<div className="d-flex flex-column">
			<div className="d-flex flex-wrap info-container mb-4">
				{infoCard.map((infoItem, index) => (
					<InfoCard
						growth={infoItem.growth}
						growthContext={infoItem.growthContext}
						key={index}
						symbol={infoItem.symbol}
						title={infoItem.title as string}
						value={infoItem.value as string}
					/>
				))}
			</div>

			<ListView<Order>
				emptyStateProps={{title: i18n.translate('no-orders-yet')}}
				initialContext={{pageSize: 30}}
				resource={function getAdministratorOrders({page, pageSize}) {
					return HeadlessCommerceAdminOrder.getOrders(
						new URLSearchParams({
							filter: SearchBuilder.in(
								'orderTypeExternalReferenceCode',
								[
									OrderTypes.CLIENT_EXTENSION,
									OrderTypes.CLOUDAPP,
									OrderTypes.DXPAPP,
									OrderTypes.COMPOSITE_APP,
									OrderTypes.LOW_CODE_CONFIGURATION,
								]
							),

							nestedFields: 'account,orderItems',
							page: page.toString(),
							pageSize: pageSize.toString(),
							sort: 'createDate:desc',
						})
					);
				}}
				tableProps={{
					actions: [
						{
							name: i18n.translate('customer-dashboard'),
							onClick: redirectTo('/customer-dashboard'),
						},
						{
							name: i18n.translate('publisher-dashboard'),
							onClick: redirectTo('/publisher-dashboard'),
						},

						{
							name: i18n.translate('order-panel'),
							onClick: (order: Order) => {
								window.open(
									`/group/guest/~/control_panel/manage?p_p_id=com_liferay_commerce_order_web_internal_portlet_CommerceOrderPortlet&p_p_lifecycle=0&p_p_state=maximized&_com_liferay_commerce_order_web_internal_portlet_CommerceOrderPortlet_mvcRenderCommandName=%2Fcommerce_order%2Fedit_commerce_order&_com_liferay_commerce_order_web_internal_portlet_CommerceOrderPortlet_commerceOrderId=${order.id}`,
									'_blank'
								);
							},
						},
					],
					columns: [
						{
							id: 'id',
							name: i18n.translate('id'),
						},
						{
							id: 'orderItems',
							name: i18n.translate('app-name'),
							render: (orderItems) => orderItems[0]?.name?.en_US,
						},
						{
							id: 'account',
							name: i18n.translate('user-account'),
							render: (account) => account.name,
						},
						{
							id: 'orderTypeExternalReferenceCode',
							name: i18n.translate('app-type'),
						},
						{
							id: 'totalFormatted',
							name: i18n.translate('amount'),
						},
						{
							id: 'orderStatusInfo',
							name: i18n.translate('order-status'),
							render: (orderStatusInfo) => (
								<ClayLabel
									className="text-nowrap"
									displayType={
										OrderWorkflowDisplayType[
											orderStatusInfo.code as keyof typeof OrderWorkflowDisplayType
										] as Status
									}
								>
									{orderStatusInfo.label_i18n}
								</ClayLabel>
							),
						},
						{
							id: 'paymentStatusInfo',
							name: i18n.translate('payment-status'),
							render: (paymentStatusInfo) => (
								<ClayLabel
									className="text-nowrap"
									displayType={
										PaymentWorkflowDisplayType[
											paymentStatusInfo?.code as keyof typeof PaymentWorkflowDisplayType
										] as Status
									}
								>
									{paymentStatusInfo.label_i18n}
								</ClayLabel>
							),
						},
						{
							id: 'createDate',
							name: i18n.translate('created-at'),
							render: (createDate) => (
								<span className="ml-2 text-capitalize text-nowrap">
									{formatDistance(
										new Date(createDate ?? ''),
										Date.now(),
										{addSuffix: true}
									)}
								</span>
							),
						},
					],
				}}
			/>
		</div>
	);
};

export default Metrics;
