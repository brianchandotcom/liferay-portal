import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';

import './DashboardTable.scss';

import React, {ReactNode} from 'react';

import {DashboardEmptyTable} from './DashboardEmptyTable';

export type AppProps = {
	externalReferenceCode: string;
	image: string;
	name: string;
	productId: number;
	selected: boolean;
	status: string;
	type: string;
	updatedBy: string;
	updatedDate: string;
	updatedResponsible: string;
	version: string;
};

export type TableHeaders = {
	iconSymbol?: string;
	title: string;
}[];

interface DashboardTableProps<T> {
	children: (item: T) => ReactNode;
	emptyStateMessage: {
		description1: string;
		description2: string;
		title: string;
	};
	items: T[];
	tableHeaders: TableHeaders;
}

export function DashboardTable<T>({
	children,
	emptyStateMessage,
	items,
	tableHeaders,
}: DashboardTableProps<T>) {
	const {description1, description2, title} = emptyStateMessage;

	if (!items.length) {
		return (
			<DashboardEmptyTable
				description1={description1}
				description2={description2}
				title={title}
			/>
		);
	}
	else {
		return (
			<ClayTable borderless className="dashboard-table-container">
				<ClayTable.Head>
					{tableHeaders.map((tableHeader) => (
						<ClayTable.Cell headingCell key={tableHeader.title}>
							<div className="dashboard-table-header-name">
								<span className="dashboard-table-header-text">
									{tableHeader.title}
								</span>

								{tableHeader.iconSymbol && (
									<ClayIcon symbol={tableHeader.iconSymbol} />
								)}
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
