/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as OAuth2 from '@liferay/oauth2-provider-web/client';
import {useState} from 'react';

export default function useDelete(fetchTicketAttachments) {
	const [isDeleting, setIsDeleting] = useState(false);

	const onDelete = async (ticketAttachmentId) => {
		setIsDeleting(true);

		try {
			const oauth2Client = await OAuth2.FromUserAgentApplication(
				'liferay-customer-etc-spring-boot-oaua'
			);

			await oauth2Client.fetch(
				'/ticket-attachments/' + ticketAttachmentId,
				{
					method: 'DELETE',
				}
			);
		}
		finally {
			setIsDeleting(false);
			fetchTicketAttachments();
		}
	};

	return {isDeleting, onDelete};
}
