/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import DropDown from '@clayui/drop-down/lib/DropDown';
import ClayIcon from '@clayui/icon';
import {addDays, format} from 'date-fns';

import appsIcon from '../../../../assets/icons/apps_fill_icon.svg';
import {DashboardEmptyTable} from '../../../../components/DashboardTable/DashboardEmptyTable';
import OrderStatus from '../../../../components/OrderStatus';
import Table from '../../../../components/Table/Table';
import i18n from '../../../../i18n';

type SolutionsTableProps = {
	items: PlacedOrder[];
};

const SolutionsTable: React.FC<SolutionsTableProps> = ({items}) => {
	if (!items.length) {
		return (
			<DashboardEmptyTable
				description1="Purchase and install new apps and they will show up here."
				description2="Click on “Add Apps” to start."
				icon={appsIcon}
				title="No Apps Yet"
			/>
		);
	}

	return (
		<Table
			columns={[
				{
					key: 'placedOrderItems',
					render: ([placedOrderItem]) => (
						<div style={{width: 200}}>
							<img
								alt="App Image"
								className="order-details-publisher-table-icon"
								src={placedOrderItem.thumbnail}
							/>

							<span className="font-weight-semi-bold ml-2">
								{placedOrderItem.name}
							</span>
						</div>
					),
					title: 'Name',
				},
				{
					key: 'author',
					render: (author, {createDate}) => {
						return (
							<div className="d-flex flex-column">
								<span className="dashboard-table-row-text">
									{author}
								</span>

								<span className="dashboard-table-row-purchased-date">
									{new Date(createDate).toLocaleDateString(
										'en-US',
										{
											day: 'numeric',
											month: 'short',
											year: 'numeric',
										}
									)}
								</span>
							</div>
						);
					},
					title: 'Purchased By',
				},
				{
					key: 'id',
					title: 'Order ID',
				},
				{
					key: 'orderTypeExternalReferenceCode',
					render: (orderTypeExternalReferenceCode) => (
						<span className="label label-info">
							{orderTypeExternalReferenceCode.includes('7')
								? '7-day Trial'
								: '30-day Trial'}
						</span>
					),
					title: 'App Type',
					width: '2%',
				},
				{
					key: 'createDate',
					render: (createDate, {orderTypeExternalReferenceCode}) =>
						format(
							addDays(
								new Date(createDate),
								orderTypeExternalReferenceCode.includes('7')
									? 7
									: 30
							),
							'dd MMM, yyyy'
						).toString(),
					title: 'End Date',
					width: '2%',
				},
				{
					key: 'orderStatusInfo',
					render: (orderStatusInfo) => (
						<OrderStatus orderStatus={orderStatusInfo?.label}>
							{orderStatusInfo?.label}
						</OrderStatus>
					),
					title: 'Provisioning',
				},
				{
					key: 'status',
					render: () => (
						<div onClick={(event) => event.stopPropagation()}>
							<DropDown
								trigger={
									<ClayButton
										displayType="secondary"
										size="sm"
									>
										{i18n.translate('manage')}
										<ClayIcon symbol="caret-bottom" />
									</ClayButton>
								}
							>
								<DropDown.ItemList>
									<DropDown.Item>Action</DropDown.Item>
								</DropDown.ItemList>
							</DropDown>
						</div>
					),
					title: 'View Details',
				},
			]}
			rows={items}
		/>
	);
};

export default SolutionsTable;
