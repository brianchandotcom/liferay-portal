/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {DndContext} from '@dnd-kit/core';
import React, {useContext, useMemo, useState} from 'react';
import {QueryClient, useMutation} from 'react-query';

import StatusColumn from '../components/StatusColumn';
import {QueryClientContext} from '../context';
import {useTickets} from '../hooks/useTickets';
import {Liferay} from '../services/liferay';
import {updateTicketStatus} from '../services/tickets';
import {Filter, ScreenType, Ticket} from '../types';

const DRAG_RESULT = {
	NO_CHANGE: 'NO_CHANGE',
	STATUS_CHANGED: 'STATUS_CHANGED',
};

const ALLOWED_DASHBOARD_STATUSES = [
	'Open',
	'In Progress',
	'Answered',
	'Closed',
];

const initialFilterState: Filter = {
	field: '',
	label: '',
	value: '',
};

const TicketsDashboard: React.FC<{screenType: ScreenType}> = ({screenType}) => {
	const queryClient: QueryClient = useContext(QueryClientContext);

	const [isLoading, setIsLoading] = useState(false);

	const pageLoadingStyle: React.CSSProperties = {
		opacity: 0.5,
		zIndex: 2,
	};

	const {rows: tickets} = useTickets({
		filter: initialFilterState,
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
		const newStatus = event.over.data.current.status;

		if (ticket.ticketStatus !== newStatus) {
			const updatedTicket = {
				...ticket,
				ticketStatus: newStatus,
			};

			await updateTicketStatus(updatedTicket);

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
		onError: () => {
			setIsLoading(false);

			queryClient.invalidateQueries();

			Liferay.Util.openToast({
				message: 'Unable to update ticket status.',
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
		<>
			{screenType === ScreenType.INTEGRATED && (
				<div className="align-items-center autofit-padded autofit-row bg-neutral-1 mb-3 p-3">
					{isLoading && (
						<ClayLoadingIndicator
							className="m-0 mr-2"
							displayType="secondary"
							size="md"
						/>
					)}
					<div className="text-11">Ticket Dashboard by Status</div>
				</div>
			)}

			{screenType === ScreenType.STANDALONE && isLoading && (
				<div
					className="autofit-padded autofit-row bg-neutral-1 h-100 justify-content-center position-absolute rounded w-100"
					style={pageLoadingStyle}
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
					{ALLOWED_DASHBOARD_STATUSES.map((status) => (
						<div className="autofit-col w-25" key={status}>
							<StatusColumn
								name={status}
								relatedTickets={relatedTicketsMap[status]}
							/>
						</div>
					))}
				</DndContext>
			</div>
		</>
	);
};

export default TicketsDashboard;
