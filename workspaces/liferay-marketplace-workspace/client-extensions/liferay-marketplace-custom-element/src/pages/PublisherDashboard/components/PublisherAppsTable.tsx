/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useNavigate} from 'react-router-dom';

import {DashboardEmptyTable} from '../../../components/DashboardTable/DashboardEmptyTable';
import OrderStatus from '../../../components/OrderStatus';
import Table from '../../../components/Table/Table';
import i18n from '../../../i18n';
import {
	getProductVersionFromSpecifications,
	getThumbnailByProductAttachment,
	showAppImage,
} from '../../../utils/util';
import {
	formatDate,
	getProductTypeFromSpecifications,
} from '../PublisherDashboardPageUtil';

type PublisherAppsTableProps = {
	items: Order[];
};

const PublisherAppsTable: React.FC<PublisherAppsTableProps> = ({items}) => {
	const navigate = useNavigate();

	if (!items?.length) {
		return (
			<DashboardEmptyTable
				description1={i18n.translate(
					'publish-apps-and-they-will-show-up-here'
				)}
				description2={i18n.translate('click-on-add-apps-to-start')}
				icon="grid"
				title={i18n.translate('no-apps-yet')}
			/>
		);
	}

	return (
		<Table
			columns={[
				{
					key: 'name',
					render: (name, {images}) => (
						<div style={{width: 200}}>
							<img
								alt="App Image"
								className="app-details-page-table-icon"
								src={showAppImage(
									getThumbnailByProductAttachment(images)
								)}
							/>

							<span className="font-weight-semi-bold ml-2">
								{name?.en_US}
							</span>
						</div>
					),
					title: i18n.translate('name'),
				},
				{
					key: 'version',
					render: (_, {productSpecifications}) =>
						getProductVersionFromSpecifications(
							productSpecifications
						),
					title: i18n.translate('version'),
				},
				{
					key: 'appType',
					render: (_, {productSpecifications}) =>
						getProductTypeFromSpecifications(productSpecifications),
					title: i18n.translate('app-type'),
				},
				{
					key: 'modifiedDate',
					render: (modifiedDate) => <b>{formatDate(modifiedDate)}</b>,
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
			onClickRow={({id}) => navigate(`/app/${id}`)}
			rows={items}
		/>
	);
};

export default PublisherAppsTable;
