/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import DataTable from '~/components/DataTable';

import './BETable.css';

export interface IColumn {
	columnKey: string;
	label: string;
	subLabel?: string;
}

export interface IRow {
	link?: string;
	[key: string]: string | number | JSX.Element | undefined;
}

interface IProps {
	columns: IColumn[];
	rows: IRow[];
}

const BETable = ({columns, rows}: IProps) => {
	return <DataTable className="be" columns={columns} rows={rows} />;
};

export default BETable;
