/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as OAuth2 from '@liferay/oauth2-provider-web/client';
import {useCallback, useState} from 'react';

interface IParams {
	comment: string;
	fileMd5: string;
	ticketAttachmentId: string;
}

interface IProps {
	completeUpload: (params: IParams) => Promise<void>;
	loading: boolean;
}

const useTicketAttachmentsCompleteUpload = (): IProps => {
	const [loading, setLoading] = useState(false);

	const completeUpload = useCallback(async (params: IParams) => {
		setLoading(true);

		const {comment, fileMd5, ticketAttachmentId} = params;

		try {
			const oauth2Client = await OAuth2.FromUserAgentApplication(
				'liferay-customer-etc-spring-boot-oaua'
			);

			const response: Response = (await oauth2Client.fetch(
				`/ticket-attachments/${ticketAttachmentId}/complete-upload`,
				{
					body: JSON.stringify({
						commentBody: comment,
					}),
					method: 'POST',
				}
			)) as unknown as Response;

			if (!response.ok) {
				throw new Error(
					`Failed to complete upload: ${response.text()}`
				);
			}

			sessionStorage.removeItem(`gcsSessionURL:${fileMd5}`);
		}
		catch (uploadError) {
			console.error('Complete upload error:', uploadError);

			throw uploadError;
		}
		finally {
			setLoading(false);
		}
	}, []);

	return {completeUpload, loading};
};

export default useTicketAttachmentsCompleteUpload;
