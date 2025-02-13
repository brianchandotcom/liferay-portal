/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useNavigate} from 'react-router-dom';

import './SVTable.css';

import Table from '~/components/Table';

export interface IColumn {
	columnKey: string;
	label: string;
}

export interface IRow {
	link?: string;
	[key: string]: string | number | JSX.Element | undefined;
}

interface IProps {
	columns: IColumn[];
	rows: IRow[];
}

const SVTable = ({columns, rows}: IProps) => {
	const navigate = useNavigate();

	const handleRowClick = (row: IRow) => {
		if (row.link) {
			navigate(row.link);
		}
	};

	return (
		<Table
			className="sv"
			columns={columns}
			onRowClick={handleRowClick}
			rows={rows}
		/>
	);
};

export default SVTable;
