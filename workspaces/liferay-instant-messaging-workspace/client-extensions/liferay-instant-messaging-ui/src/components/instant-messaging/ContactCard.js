/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBadge from '@clayui/badge';
import ClayList from '@clayui/list';
import ClaySticker from '@clayui/sticker';
import {forwardRef} from 'react';

// eslint-disable-next-line no-unused-vars
const ContactCard = forwardRef((props, ref) => {
	const {detail, handleClick, notifications, online} = props;

	const getInitials = (name) => {
		if (!name) {
			return '';
		}

		const words = name.trim().split(/\s+/);

		if (words.length === 1) {
			return words[0][0].toUpperCase();
		}

		const initials = words.map((word) => word[0].toUpperCase()).join('');

		return initials.slice(0, 2);
	};

	return (
		<>
			{detail && (
				<>
					<ClayList.Item flex onClick={handleClick}>
						<ClayList.ItemField>
							<div>
								{detail.type.toString() !== 'bot' && (
									<ClayBadge
										className="notification-badge"
										displayType="info"
										label={notifications}
									/>
								)}

								<ClaySticker
									className={
										detail.type === 'bot'
											? 'bg-success'
											: online
												? 'bg-success'
												: ''
									}
									displayType="light"
									size="xl"
								>
									{getInitials(detail.name)}
								</ClaySticker>
							</div>
						</ClayList.ItemField>

						<ClayList.ItemField expand>
							<ClayList.ItemTitle className="m-0">
								<label
									className="m-0 text-capitalize"
									data-id={detail.userId}
								>
									{detail.name}
								</label>
							</ClayList.ItemTitle>

							<ClayList.ItemText className="m-0">
								{detail.type === 'bot'
									? 'Online'
									: online
										? 'Online'
										: 'Offline'}
							</ClayList.ItemText>
						</ClayList.ItemField>
					</ClayList.Item>
				</>
			)}
		</>
	);
});

export default ContactCard;
