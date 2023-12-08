import React, { useState, useEffect } from 'react';
import { QueryClient, useMutation } from 'react-query';
import { Liferay } from '../services/liferay';

import {
	J3Y7_STATUSES,
	fetchListTypeDefinitions,
} from '../services/listTypeEntries';
import { useTickets } from '../hooks/useTickets';
import StatusColumn from '../components/StatusColumn';
import { DndContext, useSensor, useSensors, PointerSensor } from '@dnd-kit/core';
import { updateTicketStatus } from '../services/tickets';

const DRAG_RESULT = {
	STATUS_CHANGED: 'STATUS_CHANGED',
	NO_CHANGE: 'NO_CHANGE',
};

const allowedStatusesForDashboard = [
	{
		key:'open',
		order: 1
	},
	{
		key:'inProgress',
		order: 2
	},
	{
		key:'answered',
		order: 3
	},
	{
		key:'closed',
		order: 4
	}
];

function statusSortFunction(a:any,b:any){
	const aEntry = allowedStatusesForDashboard.find(temp => temp.key === a.key);
	const bEntry = allowedStatusesForDashboard.find(temp => temp.key === b.key);

	if (aEntry && bEntry)
		return aEntry.order - bEntry.order;
	return 0;
}

const TicketsByStatusDashboard: React.FC<{
	queryClient: QueryClient;
}> = ({ queryClient }) => {
	//This is needed to allow onClick events to run on Draggable elements.
	const sensors = useSensors(
		useSensor(PointerSensor, {
			activationConstraint: {
				distance: 0,
			},
		})
	);
	const [statuses, setStatuses] = useState([]);

	const { rows: tickets } = useTickets({
		filter: {
			field: '',
			value: '',
		},
		page: 0,
		pageSize: 1000,
		search: '',
	});

	useEffect(() => {
		(async () => {
			const listTypeDefinitions = await fetchListTypeDefinitions();
			let statuses = listTypeDefinitions[J3Y7_STATUSES] as any[];

			statuses = statuses.filter(
				(s) => allowedStatusesForDashboard.map(s=> s.key).indexOf(s.key) > -1
			);

			statuses.forEach((status) => {
				status.relatedTickets = tickets.filter(
					(t) => t.ticketStatus === status.name
				);
			});

			setStatuses(statuses as []);
		})();
	}, [tickets]);

	const mutation = useMutation({
		mutationFn: (e: any) => onDragEnd(e),
		onSuccess: (data) => {
			if (data === DRAG_RESULT.STATUS_CHANGED) {
				queryClient.invalidateQueries();

				Liferay.Util.openToast({
					message: 'Ticket status was updated successfully!',
					type: 'success',
				});
			}
		},
	});

	return (
		<div className="container">
			<header className="align-items-center bg-light mb-3 p-3 row">
				<h1>Ticket Dashboard by Status</h1>
			</header>
			<div className="row">
				<DndContext
					sensors={sensors}
					onDragEnd={(e) => {
						mutation.mutate(e);
					}}
				>
					{statuses.length === 0 && <h1>Loading ...</h1>}
					{statuses.length > 0 &&
						statuses?.sort(statusSortFunction).map((status: any) => (
							<div key={status.key + '_container'} className="col-3">
								<StatusColumn key={status.key} status={status} />
							</div>
						))}
				</DndContext>
			</div>
		</div>
	);

	function onDragEnd(e: any) {
		return new Promise(async (resolve, reject) => {
			if (!e || !e.over || !e.over.id)
				return resolve(DRAG_RESULT.NO_CHANGE);

			const ticket = e.active.data.current;
			const newStatus = e.over.id.replace('_droppable', '');

			//Checking if the ticket is dropped into a different status
			if (ticket.ticketStatus.toLowerCase().replaceAll(' ', '') !== newStatus.toLowerCase().replaceAll(' ', '')) {

				//Making a copy of the object so the UI does not break
				const ticketCopy = JSON.parse(JSON.stringify(ticket));

				ticketCopy.ticketStatus = statuses.find(
					(s: any) => s.key === newStatus
				);
				await updateTicketStatus(ticketCopy);
				return resolve(DRAG_RESULT.STATUS_CHANGED);
			}

			return resolve(DRAG_RESULT.NO_CHANGE);
		});
	}
};

export default TicketsByStatusDashboard;
