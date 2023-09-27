/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';

import './PublishedAppsDashboardTableRow.scss';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {MemberProps} from '../../pages/PublishedAppsDashboardPage/PublishedDashboardPageUtil';
import {Avatar} from '../Avatar/Avatar';

import './DashboardMemberTableRow.scss';

import classNames from 'classnames';

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
	const isInvitedMember = role.includes('Invited Member');

	return (
		<ClayTable.Row
			className={classNames({'invited-member': isInvitedMember})}
			onClick={() => onSelectedMemberChange(item)}
		>
			<ClayTable.Cell>
				<div className="dashboard-table-row-name-container">
					<Avatar
						emailAddress={email}
						gravatarAPI={gravatarAPI}
						initialImage={image}
						userName={name}
					/>

					<div className="d-flex">
						<span className="dashboard-table-row-name-text mr-3">
							{name}
						</span>

						{isInvitedMember && (
							<span className="label label-inverse-light rounded-lg">
								<span className="label-item label-item-expand">
									Invited
								</span>
							</span>
						)}
					</div>
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
