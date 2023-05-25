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

import './PublishedAppsDashboardTableRow.scss';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {MemberProps} from '../../pages/PublishedAppsDashboardPage/PublishedDashboardPageUtil';
import {Avatar} from '../Avatar/Avatar';

interface DashboardMemberTableRowProps {
	item: MemberProps;
	onSelectedMemberChange: (value: MemberProps | undefined) => void;
}

export function DashboardMemberTableRow({
	item,
	onSelectedMemberChange,
}: DashboardMemberTableRowProps) {
	const {email, image, name, role} = item;
	const [{gravatarAPI}, _] = useAppContext();

	return (
		<ClayTable.Row onClick={() => onSelectedMemberChange(item)}>
			<ClayTable.Cell>
				<div className="dashboard-table-row-name-container">
					<Avatar
						emailAddress={email}
						gravatarAPI={gravatarAPI}
						initialImage={image}
						userName={name}
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
