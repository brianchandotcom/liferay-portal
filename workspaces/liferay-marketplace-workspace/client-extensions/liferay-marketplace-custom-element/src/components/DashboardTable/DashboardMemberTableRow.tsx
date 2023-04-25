import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';

import './PublishedAppsDashboardTableRow.scss';
import {MemberProps} from '../../pages/PublishedAppsDashboardPage/PublishedDashboardPageUtil';

interface DashboardMemberTableRowProps {
	item: MemberProps;
	onSelectedMemberChange: (value: MemberProps | undefined) => void;
}

export function DashboardMemberTableRow({
	item,
	onSelectedMemberChange,
}: DashboardMemberTableRowProps) {
	const {email, image, name, role} = item;

	return (
		<ClayTable.Row onClick={() => onSelectedMemberChange(item)}>
			<ClayTable.Cell>
				<div className="dashboard-table-row-name-container">
					<img
						alt="Member Image"
						className="dashboard-table-row-name-logo"
						src={image}
					/>

					<span className="dashboard-table-row-name-text">
						{name}
					</span>
				</div>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<span className="dashboard-table-row-text">{email}</span>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<span className="dashboard-table-row-text">{role}</span>

				<ClayIcon
					className="dashboard-table-angle-right-small float-right mt-1"
					symbol="angle-right-small"
				/>
			</ClayTable.Cell>
		</ClayTable.Row>
	);
}
