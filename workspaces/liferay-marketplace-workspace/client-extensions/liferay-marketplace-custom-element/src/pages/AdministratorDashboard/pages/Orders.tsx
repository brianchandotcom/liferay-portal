/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import {Status} from '@clayui/modal/lib/types';
import {formatDistance} from 'date-fns';
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
	OrderWorkflowDisplayType,
	PaymentWorkflowDisplayType,
	orderTypeLabel,
} from '../../../enums/Order';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import CommerceSelectAccount from '../../../services/rest/CommerceSelectAccount';
import HeadlessCommerceAdminOrder from '../../../services/rest/HeadlessCommerceAdminOrder';
import {getLastDayOfMonth} from '../../../utils/date';
import InfoCard from '../components/InfoCard';

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

const orderTypes = [
	OrderTypes.CLIENT_EXTENSION,
	OrderTypes.CLOUDAPP,
	OrderTypes.COMPOSITE_APP,
	OrderTypes.DXPAPP,
	OrderTypes.LOW_CODE_CONFIGURATION,
	OrderTypes.OTHER,
];

const orderTypeFilters: FilterOption[] = orderTypes.map((orderType) => ({
	name: orderTypeLabel[orderType] || '',
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
						searchBuilder.contains(key, String(value));
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

	return (
		<>
			<div className="d-flex flex-column">
				<div className="d-flex flex-wrap info-container mb-4">
					<InfoCard
						expanded
						symbol="shopping-cart"
						title="Total Orders"
						value={totalOrders}
					/>

					<InfoCard
						expanded
						symbol="shopping-cart"
						title="Montly Orders"
						value={montlyOrders}
					/>

					<InfoCard
						expanded
						symbol="shopping-cart"
						title="Current Years Orders"
						value={currentYearOrders}
					/>
				</div>
			</div>

			<Page
				pageRendererProps={{className: 'border py-2'}}
				title={i18n.translate('orders')}
			>
				<AdministratorOrdersListView
					isSortable
					managementToolbarProps={{
						filterItems: [
							{
								children: orderTypeFilters,
								name: i18n.translate('app-type'),
							},
						],
						visible: true,
					}}
				/>
			</Page>
		</>
	);
}
