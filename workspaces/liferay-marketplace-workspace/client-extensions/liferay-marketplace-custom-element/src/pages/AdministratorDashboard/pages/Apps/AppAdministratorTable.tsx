/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useNavigate} from 'react-router-dom';

import {DashboardEmptyTable} from '../../../../components/DashboardTable/DashboardEmptyTable';
import OrderStatus from '../../../../components/OrderStatus';
import Table from '../../../../components/Table/Table';
import i18n from '../../../../i18n';
import {
	formatDate,
	getProductTypeFromSpecifications,
} from '../../../PublisherDashboard/PublisherDashboardPageUtil';

type AppsTableProps = {
	items: PublisherRequestInfo[];
};

const AppAdministratorTable: React.FC<AppsTableProps> = ({items}) => {
	const navigate = useNavigate();

	if (!items?.length) {
		return (
			<DashboardEmptyTable
				icon="grid"
				title={i18n.translate('no-apps-yet')}
			/>
		);
	}

	return (
		<div>
			<Table
				columns={[
					{
						key: 'name',
						render: (name, {thumbnail}) => (
							<div>
								<img
									alt="App Image"
									className="app-details-page-table-icon"
									src={thumbnail}
								/>

								<span className="font-weight-semi-bold ml-2 text-nowrap">
									{name?.en_US}
								</span>
							</div>
						),
						title: i18n.translate('name'),
					},
					{
						key: 'productSpecifications',
						render: (_, {productSpecifications}) => (
							<div className="text-capitalize">
								{getProductTypeFromSpecifications(
									productSpecifications
								)}
							</div>
						),
						title: i18n.translate('app-type'),
					},

					{
						key: 'modifiedDate',
						render: (modifiedDate) => (
							<b>{formatDate(modifiedDate)}</b>
						),
						title: i18n.translate('last-update'),
					},
					{
						key: 'workflowStatusInfo',
						render: (workflowStatusInfo) => (
							<OrderStatus orderStatus={workflowStatusInfo.label}>
								{workflowStatusInfo.label}
							</OrderStatus>
						),
						title: i18n.translate('status'),
					},
				]}
				onClickRow={({id}) => navigate(`${id}`)}
				rows={items}
			/>
		</div>
	);
};

export default AppAdministratorTable;
