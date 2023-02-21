import ClayTable from '@clayui/table';

import swapVert from '../../assets/icons/swap-vert.svg';

import './DashboardTable.scss';
import {DashboardTableRow} from './DashboardTableRow';

export type AppProps = {
	image: string;
	name: string;
	rating: string;
	selected: boolean;
	status: string;
	type: string;
	updatedBy: string;
	updatedDate: string;
	updatedResponsible: string;
	version: string;
};
interface DashboardTableProps {
	apps: AppProps[];
}

export function DashboardTable({apps}: DashboardTableProps) {
	return (
		<ClayTable borderless className="dashboard-table-container">
			<ClayTable.Head>
				<ClayTable.Cell headingCell>
					<div className="dashboard-table-header-name">
						<span className="dashboard-table-header-text">
							Name
						</span>

						<img
							alt="Swap Vert"
							className="dashboard-table-header-name-icon"
							src={swapVert}
						/>
					</div>
				</ClayTable.Cell>

				<ClayTable.Cell headingCell>
					<span className="dashboard-table-header-text">Version</span>
				</ClayTable.Cell>

				<ClayTable.Cell headingCell>
					<span className="dashboard-table-header-text">Type</span>
				</ClayTable.Cell>

				<ClayTable.Cell headingCell>
					<span className="dashboard-table-header-text">
						Last Updated
					</span>
				</ClayTable.Cell>

				<ClayTable.Cell headingCell>
					<span className="dashboard-table-header-text">Rating</span>
				</ClayTable.Cell>

				<ClayTable.Cell headingCell>
					<span className="dashboard-table-header-text">Status</span>
				</ClayTable.Cell>
			</ClayTable.Head>

			<ClayTable.Body>
				{apps.map((app) => (
					<DashboardTableRow app={app} key={app.name} />
				))}
			</ClayTable.Body>
		</ClayTable>
	);
}
