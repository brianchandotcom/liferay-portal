/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import solutionsIcon from '../../../../assets/icons/analytics_icon.svg';
import {DashboardTable} from '../../../../components/DashboardTable/DashboardTable';
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

type PublishedSolutionsTableProps = {
	items: Order[];
};

const PublishedSolutionsTable: React.FC<PublishedSolutionsTableProps> = ({
	items,
}) => {
	if (!items.length) {
		return (
			<DashboardTable
				emptyStateMessage={{
					description1: '',
					description2: '',
					title: 'No Solutions Yet',
				}}
				icon={solutionsIcon}
				items={[]}
				tableHeaders={[]}
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
					key: 'solutionType',
					render: (_, {productSpecifications}) =>
						getProductTypeFromSpecifications(productSpecifications),
					title: 'Type',
				},
				{
					key: 'modifiedDate',
					render: (modifiedDate) => <b>{formatDate(modifiedDate)}</b>,
					title: 'Last Updated',
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
			rows={items}
		/>
	);
};

export default PublishedSolutionsTable;
