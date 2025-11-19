/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';
import {getTicketAttachments} from '~/services/liferay/api';
import {ITicketAttachment} from '~/utils/types';

export default function useGetTicketAttachments(
	filterQuery: string,
	skip: boolean
): {
	fetchTicketAttachments: () => void;
	loading: boolean;
	ticketAttachments: ITicketAttachment[];
	totalCount: number;
} {
	const [loading, setLoading] = useState(true);
	const [ticketAttachments, setTicketAttachments] = useState<
		ITicketAttachment[]
	>([]);
	const [totalCount, setTotalCount] = useState(0);

	const fetchTicketAttachments = useCallback(async () => {
		if (skip) {
			return;
		}

		try {
			const ticketAttachmentsResponse =
				await getTicketAttachments(filterQuery);

			setTicketAttachments(ticketAttachmentsResponse.items);
			setTotalCount(ticketAttachmentsResponse.totalCount);
		}
		catch (error) {
			console.error('Error fetching ticket attachments:', error);
		}
		finally {
			setLoading(false);
		}
	}, [filterQuery, skip]);

	useEffect(() => {
		setLoading(true);

		fetchTicketAttachments();
	}, [fetchTicketAttachments]);

	return {fetchTicketAttachments, loading, ticketAttachments, totalCount};
}
