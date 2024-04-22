/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import DropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {Status} from '@clayui/modal/lib/types';
import {formatDistance} from 'date-fns';

import {DashboardEmptyTable} from '../../../../../components/DashboardTable/DashboardEmptyTable';
import Table from '../../../../../components/Table/Table';
import i18n from '../../../../../i18n';

type TrialTableProps = {
	items: Order[];
};

type DropDownItems = {
	id: number;
	name: string;
	onClick: (item?: Order) => void;
};

const ORDER_STATUS_LABEL = {
	completed: 'success',
	pending: 'info',
	processing: 'secondary',
};

const CONSOLE_CLOUD_URL = 'https://console.liferay.cloud';

const itemsDropdown = [
	{
		id: 1,
		name: i18n.translate('go-to-trial'),
		onClick: (order: Order) =>
			window.open(order?.customFields?.['trial-virtualhost'] as string),
	},
	{
		id: 2,
		name: i18n.translate('go-to-console'),
		onClick: () => window.open(CONSOLE_CLOUD_URL),
	},
];

const TrialTable: React.FC<TrialTableProps> = ({items}) => {
	if (!items.length) {
		return (
			<DashboardEmptyTable
				description1={i18n.translate(
					'purchase-and-install-new-apps-and-they-will-show-up-here'
				)}
				description2={i18n.translate('click-on-add-apps-to-start')}
				icon="grid"
				title={i18n.translate('no-orders-yet')}
			/>
		);
	}

	return (
		<>
			<h1 className="mb-3">{i18n.translate('recent-trials')}</h1>

			<Table
				columns={[
					{
						key: 'id',
						render: (id) => (
							<span className="font-weight-bold">{id}</span>
						),
						title: i18n.translate('id'),
					},
					{
						key: 'account',
						render: (account) => account?.name,
						title: i18n.translate('user-account'),
					},

					{
						key: 'orderStatusInfo',
						render: (orderStatusInfo) => (
							<ClayLabel
								className="text-nowrap"
								displayType={
									ORDER_STATUS_LABEL[
										orderStatusInfo?.label as keyof typeof ORDER_STATUS_LABEL
									] as Status
								}
							>
								{orderStatusInfo?.label_i18n}
							</ClayLabel>
						),
						title: i18n.translate('trial-status'),
					},
					{
						key: 'createDate',
						render: (createDate) => (
							<span className="ml-2 text-capitalize text-nowrap">
								{formatDistance(
									new Date(createDate),
									Date.now(),
									{addSuffix: true}
								)}
							</span>
						),
						title: i18n.translate('created-at'),
					},
					{
						key: 'customFields',
						render: (customFields) => (
							<span className="ml-2 text-capitalize text-nowrap">
								{formatDistance(
									new Date(customFields['trial-start-date']),
									Date.now(),
									{addSuffix: true}
								)}
							</span>
						),
						title: i18n.translate('start-date'),
					},
					{
						key: 'customFields',
						render: (customFields) => (
							<span className="ml-2 text-capitalize text-nowrap">
								{formatDistance(
									new Date(customFields['trial-end-date']),
									Date.now(),
									{addSuffix: true}
								)}
							</span>
						),
						title: i18n.translate('expiration-date'),
					},
					{
						align: 'right',
						key: 'accountId',
						render: (_, order) => {
							return (
								<DropDown
									closeOnClick
									filterKey="name"
									trigger={
										<div>
											<ClayButton
												aria-label="Actio dropdown"
												displayType="unstyled"
											>
												<ClayIcon symbol="ellipsis-v" />
											</ClayButton>
										</div>
									}
								>
									<DropDown.ItemList items={itemsDropdown}>
										{(dropDownItem: unknown) => {
											const item = dropDownItem as DropDownItems;

											return (
												<DropDown.Item
													key={item.name}
													onClick={() => {
														item.onClick(order);
													}}
												>
													{item?.name}
												</DropDown.Item>
											);
										}}
									</DropDown.ItemList>
								</DropDown>
							);
						},
						title: '',
					},
				]}
				rows={items}
			/>
		</>
	);
};

export default TrialTable;
