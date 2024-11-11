/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import i18n from '~/common/I18n';

const SecurityVulnerabilitiesItem = () => {
	const {id} = useParams();
	const [ticket, setTicket] = useState(null);

	useEffect(() => {
		const fetchTicketDetails = async () => {
			const response = await fetch(`/api/tickets/${id}`);
			const ticketData = await response.json();
			setTicket(ticketData);
		};

		fetchTicketDetails();
	}, [id]);

	if (!ticket) {
		return <div>{i18n.translate('loading')}...</div>;
	}

	return (
		<div>
			<h1>{i18n.translate('ticket')}</h1>
		</div>
	);
};

export default SecurityVulnerabilitiesItem;
