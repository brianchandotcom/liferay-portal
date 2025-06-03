/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import Icon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {Status} from '@clayui/modal/lib/types';
import {formatDistance} from 'date-fns';
import {ComponentProps, useMemo} from 'react';
import useSWR from 'swr';

import ListView, {ListViewProps} from '../../../components/ListView';
import {
	FilterOption,
	ManagementToolbarProps,
} from '../../../components/ListView/components/ManagementToolbar';
import {ListViewTypes} from '../../../components/ListView/hooks/ListViewContext';
import Page from '../../../components/Page';
import SearchBuilder from '../../../core/SearchBuilder';
import {
	OrderTypes,
	OrderWorkflowStatusCode,
	orderTypeLabel,
	orderWorkflowDisplayType,
	orderWorkflowStatusCodeLabels,
	paymentWorkflowDisplayType,
} from '../../../enums/Order';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import marketplaceOAuth2 from '../../../services/oauth/Marketplace';
import CommerceSelectAccount from '../../../services/rest/CommerceSelectAccount';
import HeadlessCommerceAdminOrder from '../../../services/rest/HeadlessCommerceAdminOrder';
import {getLastDayOfMonth} from '../../../utils/date';
import InfoCard from '../components/InfoCard';
import useOrderMetrics from '../hooks/useOrderMetrics';

function redirectTo(path: string) {
	return async function (order: Order) {
		await CommerceSelectAccount.selectAccount(order.accountId);

		Liferay.CommerceContext.account = {
			accountId: order.accountId,
			accountName: '',
		};

		Liferay.Util.navigate(
			Liferay.ThemeDisplay.getLayoutURL().replace(
				'/administrator-dashboard',
				path
			)
		);
	};
}

type AdministratorOrdersListViewProps = {
	isSortable?: boolean;
	listViewProps?: Partial<ListViewProps<Order>>;
	managementToolbarProps?: ManagementToolbarProps & {visible?: boolean};
};

const orderStatuses = [
	OrderWorkflowStatusCode.CANCELLED,
	OrderWorkflowStatusCode.COMPLETED,
	OrderWorkflowStatusCode.ON_HOLD,
	OrderWorkflowStatusCode.PENDING,
	OrderWorkflowStatusCode.PROCESSING,
];

const orderTypes = [
	OrderTypes.CLIENT_EXTENSION,
	OrderTypes.CLOUDAPP,
	OrderTypes.COMPOSITE_APP,
	OrderTypes.DXPAPP,
	OrderTypes.LOW_CODE_CONFIGURATION,
	OrderTypes.OTHER,
];

const orderStatusFilters: FilterOption[] = orderStatuses.map((status) => ({
	name: orderWorkflowStatusCodeLabels[status],
	onClick: (dispatch) => {
		dispatch({
			payload: {
				filters: {
					filter: {
						orderStatus: status,
					},
				},
			},
			type: ListViewTypes.SET_FILTERS,
		});
	},
}));

const orderTypeFilters: FilterOption[] = orderTypes.map((orderType) => ({
	name: orderTypeLabel[orderType],
	onClick: (dispatch) => {
		dispatch({
			payload: {
				filters: {
					filter: {
						orderTypeExternalReferenceCode: orderType,
					},
				},
			},
			type: ListViewTypes.SET_FILTERS,
		});
	},
}));

export function AdministratorOrdersListView({
	isSortable,
	listViewProps,
	managementToolbarProps,
}: AdministratorOrdersListViewProps) {
	return (
		<ListView<Order>
			emptyStateProps={{title: i18n.translate('no-orders-yet')}}
			id="administrator-orders"
			managementToolbarProps={managementToolbarProps}
			paginationOptions={{displayType: 'always'}}
			resource={function getAdministratorOrders({
				filters,
				keywords,
				page,
				pageSize,
				sort,
			}) {
				const searchBuilder = new SearchBuilder();

				if (filters.filter) {
					for (const [key, value] of Object.entries(filters.filter)) {
						if (key === 'orderStatus') {
							searchBuilder.lambda(key, value, {unquote: true});
						}
						else {
							searchBuilder.eq(key, String(value));
						}
					}
				}
				else {
					searchBuilder.in('orderTypeExternalReferenceCode', [
						OrderTypes.CLIENT_EXTENSION,
						OrderTypes.CLOUDAPP,
						OrderTypes.DXPAPP,
						OrderTypes.COMPOSITE_APP,
						OrderTypes.LOW_CODE_CONFIGURATION,
						OrderTypes.OTHER,
					]);
				}

				return HeadlessCommerceAdminOrder.getOrders(
					new URLSearchParams({
						filter: searchBuilder.build(),
						nestedFields: 'account,orderItems',
						page: page.toString(),
						pageSize: pageSize.toString(),
						search: keywords,
						sort: sort.key
							? `${sort.key}:${sort.direction}`
							: 'createDate:desc',
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
						name: i18n.translate('order-details'),
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
						render: (id) => (
							<span className="font-weight-bold">{id}</span>
						),
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
						render: (orderTypeExternalReferenceCode) => (
							<span>
								{
									orderTypeLabel[
										orderTypeExternalReferenceCode as keyof typeof OrderTypes
									]
								}
							</span>
						),
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
									orderWorkflowDisplayType[
										orderStatusInfo.code as keyof typeof orderWorkflowDisplayType
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
									paymentWorkflowDisplayType[
										paymentStatusInfo?.code as keyof typeof paymentWorkflowDisplayType
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
						sortable: isSortable,
					},
				],
			}}
			{...listViewProps}
		/>
	);
}

async function getOrders(params = new URLSearchParams()) {
	const response = await HeadlessCommerceAdminOrder.getOrders(params);

	return response.totalCount;
}

const baseSearchParams = {
	fields: 'id',
	pageSize: '1',
};

const today = new Date();

export default function Orders() {
	const {
		data: [totalOrders = 0, montlyOrders = 0, currentYearOrders = 0] = [],
	} = useSWR('/administrator/orders/metrics', () =>
		Promise.all([
			getOrders(new URLSearchParams(baseSearchParams)),
			getOrders(
				new URLSearchParams({
					...baseSearchParams,
					filter: new SearchBuilder()
						.gt(
							'createDate',
							new Date(
								today.getFullYear(),
								today.getMonth(),
								1,
								0,
								0,
								0
							).toISOString()
						)
						.and()
						.lt(
							'createDate',
							new Date(
								today.getFullYear(),
								today.getMonth(),
								getLastDayOfMonth(
									today.getMonth(),
									today.getFullYear()
								),
								23,
								59,
								59
							).toISOString()
						)
						.build(),
				})
			),
			getOrders(
				new URLSearchParams({
					...baseSearchParams,
					filter: SearchBuilder.gt(
						'createDate',
						new Date(today.getFullYear(), 0, 1).toISOString()
					),
				})
			),
		])
	);

	const {data: orders} = useOrderMetrics('week');

	const infoCard = useMemo(
		() => [
			{
				growth: orders?.growth ?? 0,
				growthContext: `+${orders?.lastPeriod ?? 0} this week `,
				title: 'Total Orders',
				value: totalOrders,
			},
			{
				growth: orders?.growth ?? 0,
				growthContext: `+${orders?.lastPeriod ?? 0} this week `,
				title: 'Monthly Orders',
				value: montlyOrders,
			},
			{
				growth: orders?.growth ?? 0,
				growthContext: `+${orders?.lastPeriod ?? 0} this week `,
				title: 'Current Year Orders',
				value: currentYearOrders,
			},
		],
		[
			currentYearOrders,
			montlyOrders,
			orders?.growth,
			orders?.lastPeriod,
			totalOrders,
		]
	);

	return (
		<>
			<div className="d-flex flex-column">
				<div className="d-flex flex-wrap info-container mb-4">
					{infoCard.map((card, index) => {
						return (
							<InfoCard
								expanded
								growth={card?.growth ?? 0}
								growthContext={card?.growthContext ?? 0}
								key={index}
								symbol="shopping-cart"
								title={card.title}
								value={card.value}
							/>
						);
					})}
				</div>
			</div>

			<Page
				pageRendererProps={{className: 'border py-2'}}
				title={i18n.translate('orders')}
			>
				<AdministratorOrdersListView
					isSortable
					managementToolbarProps={{
						actionButton: ({filter}) => (
							<Button
								className="ml-3 mr-4"
								displayType={
									'' as ComponentProps<
										typeof Button
									>['displayType']
								}
								onClick={() =>
									marketplaceOAuth2.downloadOrderReport(
										filter
											? SearchBuilder.in(
													'orderTypeExternalReferenceCode',
													[filter]
												)
											: ''
									)
								}
								outline
								size="sm"
							>
								<Icon className="mr-2" symbol="download" />

								{i18n.translate('export-csv')}
							</Button>
						),
						filterItems: [
							{
								children: orderTypeFilters,
								name: i18n.translate('app-type'),
							},
							{
								children: orderStatusFilters,
								name: i18n.translate('status'),
							},
						],
						hasOrderExportCSV: true,
						visible: true,
					}}
				/>
			</Page>
		</>
	);
}
