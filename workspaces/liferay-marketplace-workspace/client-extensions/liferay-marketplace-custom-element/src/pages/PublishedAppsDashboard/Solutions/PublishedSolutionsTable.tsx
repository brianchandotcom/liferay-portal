/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useNavigate} from 'react-router-dom';

import solutionsIcon from '../../../assets/icons/analytics_icon.svg';
import {DashboardTable} from '../../../components/DashboardTable/DashboardTable';
import OrderStatus from '../../../components/OrderStatus';
import Table from '../../../components/Table/Table';
import TableKebabButton from '../../../components/Table/TableButtons/TableKebabButton';
import {
	getProductVersionFromSpecifications,
	getThumbnailByProductAttachment,
	showAppImage,
} from '../../../utils/util';
import {formatDate} from '../PublishedDashboardPageUtil';

type PublishedSolutionsTableProps = {
	items: Order[];
};

const PublishedSolutionsTable: React.FC<PublishedSolutionsTableProps> = ({
	items,
}) => {
	const navigate = useNavigate();

	if (!items.length) {
		return (
			<DashboardTable
				emptyStateMessage={{
					title: 'No Solutions Yet',
				}}
				icon={solutionsIcon}
			/>
		);
	}

	const kebabItems = (row: any) => [
		{
			label: 'View Details',
			onClick: () => navigate(`/solution/${row.id}`),
		},
		{disabled: true, label: 'Edit'},
	];

	return (
		<Table
			Actions={({row}) => <TableKebabButton items={kebabItems(row)} />}
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
					render: () => 'Page',
					title: 'Solution Type',
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
			hasKebabButton
			onClickRow={({id}) => navigate(`/solution/${id}`)}
			rows={items}
		/>
	);
};

export default PublishedSolutionsTable;
