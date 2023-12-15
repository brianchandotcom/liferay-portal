/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useDroppable} from '@dnd-kit/core';
import {QueryClient} from 'react-query';

import TicketPreview from './TicketPreview';

const StatusColumn: React.FC<{queryClient: QueryClient; status: any}> = ({
	queryClient,
	status,
}) => {
	const {setNodeRef} = useDroppable({id: status.key + '_droppable'});

	return (
		<div
			className="bg-light h-100 min-vh-100 py-3 rounded status-col"
			ref={setNodeRef}
		>
			<p className="font-weight-bold">{status.name}</p>

			{status.relatedTickets?.length === 0 && (
				<p>No tickets are available.</p>
			)}

			{status.relatedTickets?.length > 0 &&
				status.relatedTickets.map((ticket: any) => (
					<TicketPreview
						key={ticket.id}
						queryClient={queryClient}
						ticket={ticket}
					/>
				))}
		</div>
	);
};

export default StatusColumn;
