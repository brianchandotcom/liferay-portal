/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useNavigate} from 'react-router-dom';

import appsIcon from '../../../../assets/icons/apps_fill_icon.svg';
import {DashboardEmptyTable} from '../../../../components/DashboardTable/DashboardEmptyTable';
import OrderStatus from '../../../../components/OrderStatus';
import Table from '../../../../components/Table/Table';
import {
	getProductVersionFromSpecifications,
	getThumbnailByProductAttachment,
	showAppImage,
} from '../../../../utils/util';
import {
	formatDate,
	getProductTypeFromSpecifications,
} from '../../PublishedDashboardPageUtil';

type PublishedAppsTableProps = {
	items: Order[];
};

const PublishedAppsTable: React.FC<PublishedAppsTableProps> = ({items}) => {
	const navigate = useNavigate();

	if (!items.length) {
		return (
			<DashboardEmptyTable
				description1="Publish apps and they will show up here."
				description2="Click on “New App” to start."
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
					render: (name, {attachments}) => (
						<div style={{width: 200}}>
							<img
								alt="App Image"
								height={36}
								src={showAppImage(
									getThumbnailByProductAttachment(attachments)
								)}
								width={36}
							/>

							<span className="font-weight-semi-bold ml-2">
								{name?.en_US}
							</span>
						</div>
					),
					title: 'Name',
				},
				{
					key: 'version',
					render: (_, {productSpecifications}) =>
						getProductVersionFromSpecifications(
							productSpecifications
						),
					title: 'Version',
				},
				{
					key: 'appType',
					render: (_, {productSpecifications}) =>
						getProductTypeFromSpecifications(productSpecifications),
					title: 'App Type',
				},
				{
					key: 'modifiedDate',
					render: (modifiedDate) => <b>{formatDate(modifiedDate)}</b>,
					title: 'Last Update',
				},
				{
					key: 'workflowStatusInfo',
					render: (workflowStatusInfo) => (
						<OrderStatus orderStatus={workflowStatusInfo.label}>
							{workflowStatusInfo.label}
						</OrderStatus>
					),
					title: 'Status',
				},
			]}
			onClickRow={({id}) => navigate(`/app/${id}`)}
			rows={items}
		/>
	);
};

export default PublishedAppsTable;
