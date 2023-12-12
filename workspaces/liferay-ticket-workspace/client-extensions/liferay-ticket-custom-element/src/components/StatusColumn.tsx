/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {QueryClient} from 'react-query';
import '../styles/StatusColumn.css';
import {useDroppable} from '@dnd-kit/core';
import TicketPreview from './TicketPreview';

const StatusColumn: React.FC<{queryClient: QueryClient; status: any}> = ({queryClient,status}) => {
	const {setNodeRef} = useDroppable({id: status.key + '_droppable'});

	return (
		<div className="status-col" id={status.key + '_droppable'} key={status.key + '_droppable'} ref={setNodeRef} >
			<h6>{status.name}</h6>

			{status.relatedTickets?.length === 0 && (
				<p>No tickets are available.</p>
			)}

			{status.relatedTickets?.length > 0 &&
				status.relatedTickets.map((t: any) => (
					<TicketPreview key={t.id} queryClient={queryClient} ticket={t} />
				))}
		</div>
	);
};

export default StatusColumn;
