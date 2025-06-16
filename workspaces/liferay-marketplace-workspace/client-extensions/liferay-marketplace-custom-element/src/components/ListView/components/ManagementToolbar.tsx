/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayManagementToolbar from '@clayui/management-toolbar';
import {ReactNode, useContext} from 'react';
import ManagementToolbarSearch from './ManagementToolbarSearch';
import {
	FilterSchemaOption,
	filterSchema as filterSchemas,
} from '../../../schema/filters';
import {ListViewContext} from '../hooks/ListViewContext';
import ManagementToolbarFilter from './ManagementToolbarFilters/ManagementToolbarFilters';
import ManagementToolbarResultsBar from './ManagementToolbarResultsBar/ManagementToolbarResultsBar';

export type ManagementToolbarProps = {
	actions: any;
	applyFilters?: boolean;
	buttons?: ReactNode | ((actions: any) => ReactNode);
	customFilterFields?: {[key: string]: string};
	display?: {
		columns?: boolean;
	};

	/**
	 * Check out the file {src/schema/filter.ts}
	 */
	filterSchema?: FilterSchemaOption;
	hasFilters?: boolean;
	hasSearch?: boolean;
	title?: string;
	totalItems: number;
	visible?: boolean
};

const ManagementToolbar: React.FC<ManagementToolbarProps> = ({
	applyFilters = true,
	customFilterFields,
	filterSchema,
	totalItems,
	hasFilters = false,
	hasSearch = false,
}) => {
	const [{filters}] = useContext(ListViewContext);

	return (
		<>
			<ClayManagementToolbar>
				<div className="w-100 d-flex justify-content-between">
					{!!hasFilters && (
						<ManagementToolbarFilter
							applyFilters={applyFilters}
							customFilterFields={customFilterFields}
							filterSchema={
								(filterSchemas as any)[filterSchema ?? '']
							}
						/>
					)}

					{!!hasSearch && <ManagementToolbarSearch />}
				</div>

				{!!filters.entries?.filter(({value}) => value).length && (
					<ManagementToolbarResultsBar totalItems={totalItems} />
				)}
			</ClayManagementToolbar>
		</>
	);
};

export default ManagementToolbar;
