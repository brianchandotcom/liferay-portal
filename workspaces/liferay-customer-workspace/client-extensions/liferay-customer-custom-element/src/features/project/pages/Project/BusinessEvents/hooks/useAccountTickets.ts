/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';
import {Liferay} from '~/services/liferay';
import {ITicket} from '~/utils/types';

const useAccountTickets = (
	externalReferenceCode?: string,
	filter?: string,
	skip?: boolean
) => {
	const [loading, setLoading] = useState(true);
	const [tickets, setTickets] = useState<ITicket[] | undefined>(undefined);

	const fetchTickets = useCallback(async () => {
		if (skip || !externalReferenceCode) {
			return;
		}

		let filterQuery = filter;

		if (filter) {
			filterQuery = `?${filter}`;
		}
		else {
			filterQuery = '';
		}

		try {
			const response: ITicket[] =
				await Liferay.OAuth2Client.FromUserAgentApplication(
					'liferay-customer-etc-spring-boot-oaua'
				)
					.fetch(
						`/accounts/${externalReferenceCode}/tickets${filterQuery}`
					)
					.then((response: {json: () => any}) => response.json());

			setTickets(response);

			setLoading(false);
		}
		catch (error) {
			console.error('Error fetching tickets data:', error);

			setTickets(undefined);

			setLoading(false);
		}
	}, [externalReferenceCode, filter, skip]);

	useEffect(() => {
		fetchTickets();
	}, [fetchTickets]);

	return {loading, tickets};
};

export default useAccountTickets;
