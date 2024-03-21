/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React from 'react';

import {Liferay} from '../services/liferay';
import {Ticket} from '../types';

const TicketGrid = ({tickets}: {tickets: Ticket[]}) => {
	return (
		<div className="border border-light p-1 rounded w-100">
			<table className="table table-borderless">
				<thead>
					<tr>
						<th>
							<ClayIcon
								className="mr-1"
								spritemap={Liferay.Icons.spritemap}
								symbol="link"
							/>
							Subject
						</th>
						<th>Resolution</th>
						<th>Status</th>
						<th>Priority</th>
						<th>Region</th>
					</tr>
				</thead>
				<tbody>
					{tickets.map((ticket) => (
						<tr className="border-top" key={ticket.id}>
							<td>{ticket.subject}</td>
							<td>
								{ticket.resolution ? ticket.resolution : 'N/A'}
							</td>
							<td>{ticket.ticketStatus}</td>
							<td>{ticket.priority}</td>
							<td>{ticket.region}</td>
						</tr>
					))}
				</tbody>
			</table>
		</div>
	);
};

export {TicketGrid};
