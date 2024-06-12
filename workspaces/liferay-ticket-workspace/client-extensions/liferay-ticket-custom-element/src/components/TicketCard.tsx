/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayPanel from '@clayui/panel';
import {useDraggable} from '@dnd-kit/core';
import {CSS} from '@dnd-kit/utilities';
import classNames from 'classnames';
import React, {useState} from 'react';
import {QueryClient, useMutation, useQueryClient} from 'react-query';

import {Liferay} from '../services/liferay';
import {assignTicketToMe} from '../services/tickets';
import {Ticket} from '../types';

const TicketCard = ({ticket}: {ticket: Ticket}) => {
	const [isLoading, setIsLoading] = useState(false);
	const [isPanelExpanded, setIsPanelExpanded] = useState(false);

	const queryClient: QueryClient = useQueryClient();

	const {attributes, isDragging, listeners, setNodeRef, transform} =
		useDraggable({data: ticket, id: ticket.id + '_draggable'});

	const draggableContainerClass = classNames({
		'bg-brand-primary-lighten-6': isDragging,
		'bg-neutral-0': !isDragging,
		'border border-neutral-2 mb-4 py-2': true,
	});

	const assignToMeMutation = useMutation({
		mutationFn: async (ticket: Ticket) => {
			setIsLoading(true);

			return await assignTicketToMe(ticket);
		},
		onError: (error: Error) => {
			setIsLoading(false);

			Liferay.Util.openToast({
				message: error.message,
				title: 'Request Failed',
				type: 'danger',
			});
		},
		onSuccess: () => {
			setIsLoading(false);

			queryClient.invalidateQueries();

			Liferay.Util.openToast({
				message: 'The ticket was successfully assigned to you!',
				type: 'success',
			});
		},
	});

	return (
		<div
			{...attributes}
			className={draggableContainerClass}
			ref={setNodeRef}
			style={{
				position: 'relative',
				transform: CSS.Translate.toString(transform),
				zIndex: isDragging ? 150 : 1,
			}}
		>
			<ClayLayout.ContentRow padded>
				<ClayLayout.ContentCol
					className="justify-content-center p-2"
					shrink
				>
					<ClayIcon
						{...listeners}
						style={{cursor: 'grab'}}
						symbol="drag"
					></ClayIcon>
				</ClayLayout.ContentCol>
				<ClayLayout.ContentCol shrink>
					<ClayPanel
						className="border-0 m-0 p-0"
						collapsable
						displayTitle={
							<ClayPanel.Title>
								<div
									className={`text-neutral-9 font-weight-bold ${
										!isPanelExpanded
											? 'text-truncate text-paragraph-sm'
											: 'text-paragraph'
									}`}
								>
									{ticket.subject}
								</div>
								<div className="font-weight-normal mt-3 text-neutral-8 text-paragraph-xs">
									{ticket.assignee ? (
										<>
											<ClayIcon
												className="mr-1 rounded-circle"
												symbol="user"
											></ClayIcon>

											<span>
												{`${ticket.assignee?.givenName} ${ticket.assignee?.familyName}`}
											</span>
										</>
									) : (
										<div className="ml-2">
											Not assigned.
										</div>
									)}
								</div>
							</ClayPanel.Title>
						}
						displayType="secondary"
						expanded={isPanelExpanded}
						onExpandedChange={setIsPanelExpanded}
						showCollapseIcon={true}
					>
						<ClayPanel.Body>
							<div className="font-weight-normal text-neutral-8 text-paragraph-sm">
								{ticket.description
									? ticket.description
									: 'No description available.'}
							</div>

							{!ticket.assignee && (
								<ClayButton
									className="mt-3"
									displayType="secondary"
									onClick={() =>
										assignToMeMutation.mutate(ticket)
									}
									size="xs"
								>
									{isLoading && (
										<span className="inline-item inline-item-before">
											<ClayLoadingIndicator
												displayType="secondary"
												size="sm"
											/>
										</span>
									)}
									Assign to Me
								</ClayButton>
							)}
						</ClayPanel.Body>
					</ClayPanel>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>
		</div>
	);
};

export default TicketCard;
