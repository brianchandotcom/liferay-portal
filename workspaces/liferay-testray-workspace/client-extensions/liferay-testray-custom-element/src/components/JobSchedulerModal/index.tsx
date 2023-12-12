/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';

import i18n from '../../i18n';
import {APIResponse, TestrayDispatchTrigger} from '../../services/rest';
import {getTimeFromNow} from '../../util/date';
import Avatar from '../Avatar';
import EmptyState from '../EmptyState';
import StatusBadge from '../StatusBadge';

type NotificationPopoverProps = {
	testrayDispatchTriggers: APIResponse<TestrayDispatchTrigger>;
};

const JobSchedulerModal: React.FC<NotificationPopoverProps> = ({
	testrayDispatchTriggers,
}) => {
	const notifications = testrayDispatchTriggers?.items ?? [];

	return (
		<>
			{notifications.length ? (
				<ClayTable borderless>
					<ClayTable.Head>
						<ClayTable.Row>
							<ClayTable.Cell headingCell>
								{i18n.translate('type')}
							</ClayTable.Cell>

							<ClayTable.Cell headingCell>
								{i18n.translate('status')}
							</ClayTable.Cell>

							<ClayTable.Cell headingCell>
								{i18n.translate('created')}
							</ClayTable.Cell>

							<ClayTable.Cell headingCell>
								{i18n.translate('creator')}
							</ClayTable.Cell>
						</ClayTable.Row>
					</ClayTable.Head>

					<ClayTable.Body>
						{notifications.map((notification, index) => (
							<ClayTable.Row key={index}>
								<ClayTable.Cell headingTitle>
									{notification.type}
								</ClayTable.Cell>

								<ClayTable.Cell>
									<StatusBadge
										type={
											notification.dueStatus.key.toLowerCase() as any
										}
									>
										{notification.dueStatus.name}
									</StatusBadge>
								</ClayTable.Cell>

								<ClayTable.Cell headingCell>
									{getTimeFromNow(notification.dateCreated)}
								</ClayTable.Cell>

								<ClayTable.Cell>
									<Avatar
										displayName={true}
										name={notification.creator.name}
										url={notification.creator.image}
									/>
								</ClayTable.Cell>
							</ClayTable.Row>
						))}
					</ClayTable.Body>
				</ClayTable>
			) : (
				<EmptyState />
			)}
		</>
	);
};

export default JobSchedulerModal;
