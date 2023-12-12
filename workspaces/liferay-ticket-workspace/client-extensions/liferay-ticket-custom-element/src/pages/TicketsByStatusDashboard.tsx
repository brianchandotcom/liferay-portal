/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {DndContext, PointerSensor, useSensor, useSensors} from '@dnd-kit/core';
import React, {useEffect, useState} from 'react';
import {QueryClient, useMutation} from 'react-query';
import StatusColumn from '../components/StatusColumn';
import {useTickets} from '../hooks/useTickets';
import {Liferay} from '../services/liferay';
import {J3Y7_STATUSES, fetchListTypeDefinitions} from '../services/listTypeEntries';
import {updateTicketStatus} from '../services/tickets';
import '../styles/TicketsByStatusDashboard.css';

const DRAG_RESULT = {
	NO_CHANGE: 'NO_CHANGE',
	STATUS_CHANGED: 'STATUS_CHANGED',
};

const allowedStatusesForDashboard = [
	{
		key: 'open',
		order: 1,
	},
	{
		key: 'inProgress',
		order: 2,
	},
	{
		key: 'answered',
		order: 3,
	},
	{
		key: 'closed',
		order: 4,
	},
];

function statusSortFunction(a: any, b: any) {
	const aEntry = allowedStatusesForDashboard.find(
		(temp) => temp.key === a.key
	);
	const bEntry = allowedStatusesForDashboard.find(
		(temp) => temp.key === b.key
	);

	if (aEntry && bEntry) {
		return aEntry.order - bEntry.order;
	}

	return 0;
}

const TicketsByStatusDashboard: React.FC<{
	queryClient: QueryClient;
}> = ({queryClient}) => {
	const [isLoading, setIsLoading] = useState(false);
	const [statuses, setStatuses] = useState([]);

	const sensors = useSensors(
		useSensor(PointerSensor, {
			activationConstraint: {
				distance: 0,
			},
		})
	);

	const {rows: tickets} = useTickets({
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

			statuses = statuses.filter((s) => allowedStatusesForDashboard.map((s) => s.key).indexOf(s.key) > -1);

			statuses.forEach((status) => {
				status.relatedTickets = tickets.filter(
					(t) => t.ticketStatus === status.name
				);
			});

			setStatuses(statuses as []);
		})();
	}, [tickets]);

	const mutation = useMutation({
		mutationFn: (event: any) => {
			setIsLoading(true);
			return onDragEnd(event, statuses);
		},
		onError: () => {
			setIsLoading(false);
			queryClient.invalidateQueries();
			Liferay.Util.openToast({
				message: 'Something went wrong, please try again.',
				type: 'danger',
			});
		},
		onSuccess: (data) => {
			setIsLoading(false);
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
				{isLoading ?
					<ClayLoadingIndicator className="loading-indicator" displayType="secondary" size="md" /> : ''}
				<h1>Ticket Dashboard by Status</h1>
			</header>
			<div className="row">
				<DndContext sensors={sensors} onDragEnd={(event) => {mutation.mutate(event);}} >
					{!statuses.length && <h1>Loading ...</h1>}
					{!!statuses.length && statuses?.sort(statusSortFunction)
							.map((status: any) => (
								<div className="col-3" key={status.key + '_container'} >
									<StatusColumn key={status.key} queryClient={queryClient} status={status} />
								</div>
							))}
				</DndContext>
			</div>
		</div>
	);
};

function onDragEnd(event: any, statuses: any[]) {
	return new Promise((resolve, reject) => {
		if (!event || !event.over || !event.over.id) {
			return resolve(DRAG_RESULT.NO_CHANGE);
		}

		const ticket = event.active.data.current;
		const newStatus = event.over.id.replace('_droppable', '');

		if (ticket.ticketStatus.toLowerCase().replaceAll(' ', '') !== newStatus.toLowerCase().replaceAll(' ', '')) {
			const ticketCopy = JSON.parse(JSON.stringify(ticket));

			ticketCopy.ticketStatus = statuses.find(
				(status: any) => status.key === newStatus
			);

			updateTicketStatus(ticketCopy)
				.then((result) => {
					if (result.status === 200 || result.status === 204) {
						return resolve(DRAG_RESULT.STATUS_CHANGED);
					}
					else {
						return reject(DRAG_RESULT.STATUS_CHANGED);
					}
				})
				.catch(() => {
					return reject(500);
				});
		}
		else {
			return resolve(DRAG_RESULT.NO_CHANGE);
		}
	});
}

export default TicketsByStatusDashboard;
