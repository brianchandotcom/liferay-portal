/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import DropDown from '@clayui/drop-down/lib/DropDown';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {useNavigate} from 'react-router-dom';

import appsIcon from '../../../../assets/icons/apps_fill_icon.svg';
import {DashboardEmptyTable} from '../../../../components/DashboardTable/DashboardEmptyTable';
import OrderStatus, {
	Statuses as OrderStatuses,
} from '../../../../components/OrderStatus';
import Table from '../../../../components/Table/Table';
import {useMarketplaceContext} from '../../../../context/MarketplaceContext';
import {OrderType} from '../../../../enums/OrderType';
import i18n from '../../../../i18n';
import {showAppImage} from '../../../../utils/util';

type AppsTableProps = {
	items: Order[];
};

const AppsTable: React.FC<AppsTableProps> = ({items}) => {
	const navigate = useNavigate();
	const {properties} = useMarketplaceContext();

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
					key: 'name',
					render: (name, {thumbnail}) => (
						<div style={{width: 200}}>
							<img
								alt="App Image"
								height={36}
								src={showAppImage(thumbnail)}
								width={36}
							/>

							<span className="font-weight-semi-bold ml-2">
								{name}
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
					key: 'type',
					render: (type) => (
						<span className="dashboard-table-row-type">{type}</span>
					),
					title: 'License Type',
				},
				{
					key: 'orderTypeExternalReferenceCode',
					render: (orderTypeExternalReferenceCode) => {
						return (
							<>
								{orderTypeExternalReferenceCode ===
								OrderType.DXP
									? 'DXP'
									: 'Cloud'}
							</>
						);
					},
					title: 'App Type',
				},
				{
					key: 'id',
					title: 'Order ID',
				},
				{
					key: 'orderStatusInfo',
					render: (orderStatusInfo) => (
						<OrderStatus orderStatus={orderStatusInfo?.label}>
							{orderStatusInfo?.label}
						</OrderStatus>
					),
					title: 'Order Status',
				},
				{
					key: 'status',
					render: (
						_,
						{
							id,
							orderStatusInfo,
							orderTypeExternalReferenceCode,
							placedOrderItems,
							virtualURL,
						}
					) => {
						const orderStatusIsNotCompleted =
							orderStatusInfo?.label !== OrderStatuses.COMPLETED;

						const isFreeApp =
							placedOrderItems[0]?.price?.price === 0 &&
							placedOrderItems[0]?.sku !== 'TRIAL';

						return (
							<div onClick={(event) => event.stopPropagation()}>
								<DropDown
									trigger={
										<ClayButton displayType="secondary">
											{i18n.translate('manage')}
											<ClayIcon symbol="caret-bottom" />
										</ClayButton>
									}
								>
									<DropDown.ItemList>
										{orderTypeExternalReferenceCode ===
											OrderType.DXP && (
											<>
												<ClayTooltipProvider>
													<DropDown.Item
														data-tooltip-align="left"
														disabled={
															orderStatusIsNotCompleted
														}
														onClick={() =>
															navigate(
																`order/${id}/create-license`
															)
														}
														title={
															orderStatusIsNotCompleted
																? i18n.translate(
																		'the-order-must-be-completed-before-licensing-this-app.'
																  )
																: undefined
														}
													>
														{i18n.translate(
															'create-license-key'
														)}
													</DropDown.Item>
												</ClayTooltipProvider>

												<DropDown.Item
													disabled={isFreeApp}
													onClick={() => {
														navigate(
															`order/${id}/licenses`
														);
													}}
												>
													Manage License Key(s)
												</DropDown.Item>
											</>
										)}

										{orderTypeExternalReferenceCode ===
											OrderType.CLOUD && (
											<DropDown.Item
												onClick={() => {
													window.open(
														properties.cloudBaseURL
													);
												}}
											>
												{i18n.translate(
													'access-console'
												)}
											</DropDown.Item>
										)}

										{orderTypeExternalReferenceCode ===
											OrderType.DXP && (
											<ClayTooltipProvider>
												<DropDown.Item
													data-tooltip-align="left"
													disabled={
														orderStatusIsNotCompleted
													}
													onClick={() => {
														window.location.href = virtualURL;
													}}
													title={
														orderStatusIsNotCompleted
															? i18n.translate(
																	'this-order-must-be-completed-before-downloading-this-app.'
															  )
															: undefined
													}
												>
													{i18n.translate(
														'download-app'
													)}
												</DropDown.Item>
											</ClayTooltipProvider>
										)}
									</DropDown.ItemList>
								</DropDown>
							</div>
						);
					},
					title: 'Installation',
				},
			]}
			onClickRow={({id}) => navigate(`order/${id}`)}
			rows={items}
		/>
	);
};

export default AppsTable;
