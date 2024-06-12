/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {DndContext} from '@dnd-kit/core';
import React, {useMemo, useState} from 'react';
import {QueryClient, useMutation, useQueryClient} from 'react-query';

import StatusColumn from '../components/StatusColumn';
import {useTickets} from '../hooks/useTickets';
import {Liferay} from '../services/liferay';
import {updateTicketStatus} from '../services/tickets';
import {ScreenType, Ticket} from '../types';
import {INITIAL_FILTER_STATE} from '../utils/constants';

type RelatedTicketsMap = {
	[key: string]: Ticket[];
};

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

const DASHBOARD_STYLE_WHILE_LOADING: React.CSSProperties = {
	opacity: 0.5,
	zIndex: 1,
};

const PAGE_LOADING_INDICATOR_STYLE: React.CSSProperties = {
	opacity: 1,
	zIndex: 2,
};

const TicketsDashboard = ({screenType}: {screenType: ScreenType}) => {
	const queryClient: QueryClient = useQueryClient();

	const [isLoading, setIsLoading] = useState(false);

	const {rows: tickets} = useTickets({
		filter: INITIAL_FILTER_STATE,
		page: 0,
		pageSize: 1000,
		search: '',
	});

	const relatedTicketsMap: RelatedTicketsMap =
		useMemo<RelatedTicketsMap>(() => {
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
		<div className="position-relative">
			{screenType === ScreenType.INTEGRATED && (
				<ClayLayout.ContentRow className="align-items-center bg-neutral-1 mb-3 p-3 rounded">
					{isLoading && (
						<ClayLoadingIndicator
							className="m-0 mr-2"
							displayType="secondary"
							size="md"
						/>
					)}
					<div className="text-11">Ticket Dashboard by Status</div>
				</ClayLayout.ContentRow>
			)}

			{screenType === ScreenType.STANDALONE && isLoading && (
				<ClayLayout.ContentRow
					className="h-100 position-absolute pt-5 w-100"
					padded
					style={PAGE_LOADING_INDICATOR_STYLE}
				>
					<ClayLoadingIndicator
						className="d-block"
						displayType="secondary"
						size="lg"
					/>
				</ClayLayout.ContentRow>
			)}

			<ClayLayout.ContentRow
				style={isLoading ? DASHBOARD_STYLE_WHILE_LOADING : {}}
			>
				<DndContext
					onDragEnd={(event) => {
						onDragEndMutation.mutate(event);
					}}
				>
					{ALLOWED_DASHBOARD_STATUSES.map((status) => (
						<div className="autofit-col mx-3 w-25" key={status}>
							<StatusColumn
								name={status}
								relatedTickets={
									relatedTicketsMap[status]
										? relatedTicketsMap[status]
										: []
								}
							/>
						</div>
					))}
				</DndContext>
			</ClayLayout.ContentRow>
		</div>
	);
};

export default TicketsDashboard;
