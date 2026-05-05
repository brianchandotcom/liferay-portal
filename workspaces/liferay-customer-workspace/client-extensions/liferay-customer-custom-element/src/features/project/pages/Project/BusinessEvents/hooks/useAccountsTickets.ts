/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';
import {getAccountTickets} from '~/services/liferay/rest/jira/Jira';
import {IBusinessEvent, ITicket} from '~/utils/types';

import parseAssociatedTickets from '../utils/parseAssociatedTickets';

const useAccountsTickets = (
	businessEvent?: IBusinessEvent,
	externalReferenceCode?: string,
	skip?: boolean
) => {
	const [loading, setLoading] = useState(true);
	const [tickets, setTickets] = useState<ITicket[] | undefined>(undefined);

	const fetchTickets = useCallback(async () => {
		if (skip || !externalReferenceCode) {
			setLoading(false);

			return;
		}

		try {
			const ticketIds = businessEvent
				? parseAssociatedTickets(businessEvent.associatedTickets)
				: undefined;

			const response: ITicket[] = await getAccountTickets(
				externalReferenceCode,
				ticketIds
			);

			setTickets(response);

			setLoading(false);
		}
		catch (error) {
			console.error('Error fetching tickets data:', error);

			setTickets(undefined);

			setLoading(false);
		}
	}, [businessEvent, externalReferenceCode, skip]);

	useEffect(() => {
		setLoading(true);

		fetchTickets();
	}, [fetchTickets]);

	return {loading, tickets};
};

export default useAccountsTickets;
