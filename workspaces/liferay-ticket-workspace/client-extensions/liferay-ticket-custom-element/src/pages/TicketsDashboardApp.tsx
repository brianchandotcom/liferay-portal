/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {DndContext} from '@dnd-kit/core';
import React, {useContext, useMemo, useState} from 'react';
import {QueryClient, useMutation} from 'react-query';

import StatusColumn from '../components/StatusColumn';
import {DefaultAppContext, QueryClientContext} from '../context';
import {useTickets} from '../hooks/useTickets';
import {Liferay} from '../services/liferay';
import {updateTicketStatus} from '../services/tickets';
import {Ticket} from '../types';

const DRAG_RESULT = {
	NO_CHANGE: 'NO_CHANGE',
	STATUS_CHANGED: 'STATUS_CHANGED',
};

const allowedStatusesForDashboard = [
	'Open',
	'In Progress',
	'Answered',
	'Closed',
];

const TicketsDashboardApp: React.FC = () => {
	const queryClient: QueryClient = useContext(QueryClientContext);

	const [isLoading, setIsLoading] = useState(false);

	const specificPageLoadingCSS: React.CSSProperties = {
		opacity: 0.5,
		zIndex: 2,
	};

	const {rows: tickets} = useTickets({
		filter: {
			field: '',
			value: '',
		},
		page: 0,
		pageSize: 1000,
		search: '',
	});

	type RelatedTicketsMap = {
		[key: string]: Ticket[];
	};

	const relatedTicketsMap: RelatedTicketsMap = useMemo<
		RelatedTicketsMap
	>(() => {
		const map: RelatedTicketsMap = {};

		tickets.forEach((ticket: Ticket) => {
			if (!map[ticket.ticketStatus]) {
				map[ticket.ticketStatus] = [];
			}

			map[ticket.ticketStatus].push(ticket);
		});

		return map;
	}, [tickets]);

	const onDragEnd = async (event: any) => {
		if (!event || !event.over || !event.over.id) {
			return DRAG_RESULT.NO_CHANGE;
		}

		const ticket = event.active.data.current;
		const newStatus = event.over.id.replace('_droppable', '');

		if (
			ticket.ticketStatus.toLowerCase().replaceAll(' ', '') !==
			newStatus.toLowerCase().replaceAll(' ', '')
		) {
			const ticketCopy = JSON.parse(JSON.stringify(ticket));

			ticketCopy.ticketStatus = newStatus;

			await updateTicketStatus(ticketCopy);

			return DRAG_RESULT.STATUS_CHANGED;
		}
		else {
			return DRAG_RESULT.NO_CHANGE;
		}
	};

	const onDragEndMutation = useMutation({
		mutationFn: (event: any) => {
			setIsLoading(true);

			return onDragEnd(event);
		},
		onError: (error: Error) => {
			setIsLoading(false);

			queryClient.invalidateQueries();

			Liferay.Util.openToast({
				message: error.message,
				title: 'Request Failed',
				type: 'danger',
			});
		},
		onSuccess: (result) => {
			setIsLoading(false);

			if (result === DRAG_RESULT.STATUS_CHANGED) {
				queryClient.invalidateQueries();

				Liferay.Util.openToast({
					message: 'Ticket status was updated successfully!',
					type: 'success',
				});
			}
		},
	});

	return (
		<DefaultAppContext.Consumer>
			{(defaultApp) => (
				<>
					{!defaultApp && (
						<div className="align-items-center autofit-padded autofit-row bg-neutral-1 mb-3 p-3">
							{isLoading && (
								<ClayLoadingIndicator
									className="m-0 mr-2"
									displayType="secondary"
									size="md"
								/>
							)}
							<div className="text-11">
								Ticket Dashboard by Status
							</div>
						</div>
					)}

					{!!defaultApp && isLoading && (
						<div
							className="autofit-padded autofit-row bg-neutral-1 h-100 justify-content-center position-absolute rounded w-100"
							style={specificPageLoadingCSS}
						>
							<ClayLoadingIndicator
								className="d-block"
								displayType="secondary"
								size="lg"
							/>
						</div>
					)}

					<div className="autofit-padded-no-gutters autofit-row">
						<DndContext
							onDragEnd={(event) => {
								onDragEndMutation.mutate(event);
							}}
						>
							{allowedStatusesForDashboard.map((status) => (
								<div className="autofit-col w-25" key={status}>
									<StatusColumn
										name={status}
										relatedTickets={
											relatedTicketsMap[status]
										}
										statusKey={status}
									/>
								</div>
							))}
						</DndContext>
					</div>
				</>
			)}
		</DefaultAppContext.Consumer>
	);
};

export default TicketsDashboardApp;
