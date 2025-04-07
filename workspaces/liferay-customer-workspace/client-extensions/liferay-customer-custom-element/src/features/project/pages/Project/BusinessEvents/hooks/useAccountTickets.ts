/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';
import {Liferay} from '~/services/liferay';
import {IBusinessEvent} from '~/utils/types';
import {ITicket} from '~/utils/types';

const useAccountTickets = (
	externalReferenceCode: string,
	businessEvent?: IBusinessEvent,
	getOpenTickets?: boolean
) => {
	const [loading, setLoading] = useState(true);
	const [tickets, setTickets] = useState<ITicket[] | undefined>(undefined);

	const fetchTickets = useCallback(async (associatedTicketIds?: string) => {
		if (!externalReferenceCode) {
			setTickets(undefined);

			return;
		}

		if (associatedTicketIds) {
			associatedTicketIds = `associatedTicketIds=${JSON.parse(
				associatedTicketIds).map(
					(id: string) => Number(id)
			)}`;
		}

		try {
			const response: ITicket[] =
				await Liferay.OAuth2Client.FromUserAgentApplication(
					'liferay-customer-etc-spring-boot-oaua'
				)
					.fetch(
						`/accounts/${externalReferenceCode}/tickets?${associatedTicketIds}`
					)
					.then((response: {json: () => any}) => response.json());

			if (getOpenTickets) {
				const openTicketResponse: ITicket[] =
					await Liferay.OAuth2Client.FromUserAgentApplication(
						'liferay-customer-etc-spring-boot-oaua'
					)
						.fetch(`/accounts/${externalReferenceCode}/tickets`)
						.then((response: {json: () => any}) => response.json());

				setTickets(
					openTicketResponse.filter(
						(openTicket) => !response.some(
							(ticket) => openTicket.ticketId === ticket.ticketId)
					).concat(response)
				);
			}
			else {
				setTickets(response);
			}

			setLoading(false);
		}
		catch (error) {
			console.error('Error fetching tickets data:', error);

			setTickets(undefined);
			setLoading(false);
		}
	}, [businessEvent, externalReferenceCode, getOpenTickets]);

	useEffect(() => {
		if (businessEvent) {
			fetchTickets(businessEvent?.associatedTickets);
		}
		else {
			fetchTickets();
		}

	}, [fetchTickets]);

	return {loading, tickets};
};

export default useAccountTickets;
