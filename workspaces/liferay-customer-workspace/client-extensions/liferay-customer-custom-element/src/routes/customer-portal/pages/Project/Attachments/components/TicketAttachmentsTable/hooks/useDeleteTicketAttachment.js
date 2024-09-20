/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';

export default function useDelete() {
	const [isDeleting, setIsDeleting] = useState(false);

	const onDelete = async (ticketAttachmentId) => {
		setIsDeleting(true);

		await Liferay.OAuth2Client.FromUserAgentApplication(
			'liferay-customer-etc-spring-boot-oaua'
		)
			.fetch('/ticket-attachments/' + ticketAttachmentId, {
				method: 'DELETE',
			})
			.finally(() => {
				setIsDeleting(false);
			});
	};

	return {isDeleting, onDelete};
}
