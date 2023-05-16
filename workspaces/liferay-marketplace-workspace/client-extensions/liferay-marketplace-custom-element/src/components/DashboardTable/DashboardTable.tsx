import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';

import './DashboardTable.scss';

import React, {ReactNode} from 'react';

import {DashboardEmptyTable} from './DashboardEmptyTable';

export type AppProps = {
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
	title: string;
	style?: {width: string};
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
