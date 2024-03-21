/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useDroppable} from '@dnd-kit/core';
import React from 'react';

import {Ticket} from '../types';
import TicketCard from './TicketCard';

const StatusColumn = ({
	name,
	relatedTickets,
}: {
	name: string;
	relatedTickets: Ticket[];
}) => {
	const {setNodeRef} = useDroppable({
		data: {status: name},
		id: name + '_droppable',
	});

	return (
		<div
			className="bg-neutral-1 h-100 min-vh-100 p-3 rounded"
			ref={setNodeRef}
		>
			<p className="font-weight-bold">{name}</p>

			{!relatedTickets?.length && (
				<div className="font-weight-normal text-neutral-9 text-paragraph-sm">
					No tickets are available.
				</div>
			)}

			{relatedTickets.map((ticket: Ticket) => (
				<TicketCard key={ticket.id} ticket={ticket} />
			))}
		</div>
	);
};

export default StatusColumn;
