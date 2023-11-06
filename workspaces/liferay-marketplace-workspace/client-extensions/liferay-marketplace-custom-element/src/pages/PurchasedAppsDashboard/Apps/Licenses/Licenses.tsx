/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';

import solutionsIcon from '../../../../assets/icons/bookmarks_icon.svg';
import {DashboardEmptyTable} from '../../../../components/DashboardTable/DashboardEmptyTable';
import StatusCell from '../../../../components/Table/StatusCell/StatusCell';
import Table from '../../../../components/Table/Table';
import i18n from '../../../../i18n';

import './Licenses.scss';

const ROWS = [
	{
		columns: {
			environment: {
				description: 'Hourglass - Search Security - Standard',
				value: 'Standard',
			},
			keyType: {
				description: 'PLPRIWS318.Hourglass-portal.com',
				value: 'On-Premise',
			},
			startDateEndDate: {
				value: 'Sep 24, 2023 - Sep 24, 2024',
			},
			status: {
				value: (
					<StatusCell icon="circle" iconClass="active">
						<span>Active</span>
					</StatusCell>
				),
			},
		},
		id: Math.random().toString(),
		onClickRow: () => {},
	},
	{
		columns: {
			environment: {
				description: 'Hourglass - Search Security - Standard',
				value: 'Standard',
			},
			keyType: {
				description: '-',
				value: 'On-Premise',
			},
			startDateEndDate: {
				value: 'Sep 24, 2023 - Sep 24, 2024',
			},
			status: {
				value: (
					<StatusCell icon="circle" iconClass="expired">
						<span>Expired</span>
					</StatusCell>
				),
			},
		},
		id: Math.random().toString(),
		onClickRow: () => {},
	},
];

const columns = [
	{
		bodyClass: 'border-0 cursor-pointer',
		expanded: true,
		header: {
			description: 'Description',
			name: 'Environment',
			noWrap: true,
		},
		id: 'environment',
	},
	{
		bodyClass: 'border-0 cursor-pointer',
		header: {
			description: 'Host Name',
			name: 'Key Type',
		},
		id: 'keyType',
	},
	{
		bodyClass: 'border-0 cursor-pointer',
		header: {
			name: 'Start Date - Exp. Date',
		},
		id: 'startDateEndDate',
	},
	{
		bodyClass: 'border-0 cursor-pointer',
		header: {
			name: 'Status',
		},
		id: 'status',
	},
];

const Licenses = () => {
	const [licenseKeys] = useState(ROWS);

	return (
		<div className="licenses">
			{licenseKeys.length ? (
				<Table
					columns={columns}
					hasKebabButton
					hasPagination
					rows={licenseKeys}
				/>
			) : (
				<DashboardEmptyTable
					button
					buttonName={i18n.translate('create-license-key')}
					description1={i18n.translate(
						'create-new-licenses-and-they-will-show-up-here'
					)}
					icon={solutionsIcon}
					title={i18n.translate('no-licenses-yet')}
				/>
			)}
		</div>
	);
};

export default Licenses;
