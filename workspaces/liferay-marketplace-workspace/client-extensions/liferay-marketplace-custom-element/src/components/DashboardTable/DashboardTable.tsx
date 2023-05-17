/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';

import './DashboardTable.scss';

import React, {ReactNode} from 'react';

import {DashboardEmptyTable} from './DashboardEmptyTable';

export type AppProps = {
	attachments: Partial<ProductAttachment>[];
	catalogId: number;
	externalReferenceCode: string;
	lastUpdatedBy?: string;
	name: string;
	productId: number;
	selected?: boolean;
	status: string;
	thumbnail: string;
	type: string;
	updatedDate: string;
	version: string;
};

export type TableHeaders = {
	iconSymbol?: string;
	style?: {width: string};
	title: string;
}[];

interface DashboardTableProps<T> {
	children: (item: T) => ReactNode;
	emptyStateMessage: {
		description1: string;
		description2: string;
		title: string;
	};
	icon: string;
	items: T[];
	tableHeaders: TableHeaders;
}

export function DashboardTable<T>({
	children,
	emptyStateMessage,
	icon,
	items,
	tableHeaders,
}: DashboardTableProps<T>) {
	const {description1, description2, title} = emptyStateMessage;

	if (!items.length) {
		return (
			<DashboardEmptyTable
				description1={description1}
				description2={description2}
				icon={icon}
				title={title}
			/>
		);
	}
	else {
		return (
			<ClayTable borderless className="dashboard-table-container">
				<ClayTable.Head>
					{tableHeaders.map(({iconSymbol, style, title}) => (
						<ClayTable.Cell headingCell key={title} style={style}>
							<div className="dashboard-table-header-name">
								<span className="dashboard-table-header-text">
									{title}
								</span>

								{iconSymbol && <ClayIcon symbol={iconSymbol} />}
							</div>
						</ClayTable.Cell>
					))}
				</ClayTable.Head>

				<ClayTable.Body>
					{items.map((item, index) => (
						<React.Fragment key={index}>
							{children(item)}
						</React.Fragment>
					))}
				</ClayTable.Body>
			</ClayTable>
		);
	}
}
