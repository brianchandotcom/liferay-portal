/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AttachmentMessage from '../../components/AttachmentMessage/AttachmentMessage';

const TicketIsClosed = () => {
	return (
		<AttachmentMessage
			icon="warning-full"
			subtitle="no-further-edits-can-be-made-when-tickets-are-closed-please-open-a-new-support-ticket-if-assistance-is-needed"
			title="this-ticket-has-been-closed"
		/>
	);
};

export default TicketIsClosed;
