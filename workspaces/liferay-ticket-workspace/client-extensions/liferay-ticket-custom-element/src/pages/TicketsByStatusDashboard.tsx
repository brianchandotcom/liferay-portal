/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {DndContext} from '@dnd-kit/core';
import React, {useContext, useState} from 'react';
import {QueryClient, useMutation} from 'react-query';

import StatusColumn from '../components/StatusColumn';
import {TicketsAppContext} from '../context';
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

const TicketsByStatusDashboard: React.FC = () => {
	const queryClient: QueryClient = useContext(TicketsAppContext).queryClient;
	const defaultPage: string = useContext(TicketsAppContext).defaultPage;
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

	const onDragEnd = (event: any) =>
		new Promise((resolve, reject) => {
			if (!event || !event.over || !event.over.id) {
				return resolve(DRAG_RESULT.NO_CHANGE);
			}

			const ticket = event.active.data.current;
			const newStatus = event.over.id.replace('_droppable', '');

			if (
				ticket.ticketStatus.toLowerCase().replaceAll(' ', '') !==
				newStatus.toLowerCase().replaceAll(' ', '')
			) {
				const ticketCopy = JSON.parse(JSON.stringify(ticket));

				ticketCopy.ticketStatus = newStatus;

				updateTicketStatus(ticketCopy)
					.then((result) => {
						if (result.status === 200 || result.status === 204) {
							return resolve(DRAG_RESULT.STATUS_CHANGED);
						}
						else {
							return reject(DRAG_RESULT.NO_CHANGE); // this will cause the onError method to be called
						}
					})
					.catch((error) => {
						throw new Error(error);
					});
			}
			else {
				return resolve(DRAG_RESULT.NO_CHANGE);
			}
		});

	const onDragEndMutation = useMutation({
		mutationFn: (event: any) => {
			setIsLoading(true);

			return onDragEnd(event);
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
		<div>
			{!defaultPage && (
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

			{!!defaultPage && isLoading && (
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
					{allowedStatusesForDashboard.map((status: string) => (
						<div
							className="autofit-col w-25"
							key={status + '_container'}
						>
							<StatusColumn
								name={status}
								relatedTickets={tickets.filter(
									(ticket: Ticket) =>
										ticket.ticketStatus === status
								)}
								statusKey={status}
							/>
						</div>
					))}
				</DndContext>
			</div>
		</div>
	);
};

export default TicketsByStatusDashboard;
