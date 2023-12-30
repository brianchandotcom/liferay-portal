/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon, {ClayIconSpriteContext} from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayPanel from '@clayui/panel';
import {useDraggable} from '@dnd-kit/core';
import {CSS} from '@dnd-kit/utilities';
import {useContext, useState} from 'react';
import {QueryClient, useMutation} from 'react-query';

import {QueryClientContext} from '../context';
import {Liferay} from '../services/liferay';
import {assignTicketToMe} from '../services/tickets';
import {Ticket} from '../types';

const TicketCard: React.FC<{ticket: Ticket}> = ({ticket}) => {
	const [isLoading, setIsLoading] = useState(false);
	const [isPanelExpanded, setIsPanelExpanded] = useState(false);

	const queryClient: QueryClient = useContext(QueryClientContext);
	const assigneeName: string = `${ticket.assignee?.givenName} ${ticket.assignee?.familyName}`;

	const {
		attributes,
		isDragging,
		listeners,
		setNodeRef,
		transform,
	} = useDraggable({data: ticket, id: ticket.id + '_draggable'});

	const style: React.CSSProperties = {
		position: 'relative',
		transform: CSS.Translate.toString(transform),
		zIndex: isDragging ? 150 : 1,
	};

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
		<ClayIconSpriteContext.Consumer>
			{(spritemap) => (
				<div
					ref={setNodeRef}
					{...attributes}
					className={`border border-neutral-2 card mb-4 py-2 shadow-none ${
						isDragging
							? 'bg-brand-primary-lighten-6'
							: 'bg-neutral-0'
					}`}
					style={style}
				>
					<div className="autofit-padded autofit-row">
						<div className="autofit-col autofit-col-shrink justify-content-center p-2">
							<ClayIcon
								{...listeners}
								spritemap={spritemap}
								style={{cursor: 'grab'}}
								symbol="drag"
							></ClayIcon>
						</div>
						<div className="autofit-col autofit-col-expand">
							<div className="autofit-section">
								<ClayPanel
									className="border-0 m-0 p-0"
									collapsable
									displayTitle={
										<ClayPanel.Title>
											<div
												className={`text-neutral-9 font-weight-bold ${
													!isPanelExpanded
														? 'overflow-hidden text-truncate white-space text-paragraph-sm'
														: 'text-paragraph'
												}`}
											>
												{ticket.subject}
											</div>
											<div className="font-weight-normal mt-3 text-neutral-8 text-paragraph-xs">
												{ticket.assignee ? (
													<>
														<ClayIcon
															className="d-inline mr-1 rounded-circle"
															spritemap={
																spritemap
															}
															symbol="user"
														></ClayIcon>

														<div className="d-inline">
															{assigneeName}
														</div>
													</>
												) : (
													<i className="ml-2">
														Not assigned
													</i>
												)}
											</div>
										</ClayPanel.Title>
									}
									displayType="secondary"
									expanded={isPanelExpanded}
									onExpandedChange={(isExpanded: any) =>
										setIsPanelExpanded(isExpanded)
									}
									showCollapseIcon={true}
									spritemap={spritemap}
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
													assignToMeMutation.mutate(
														ticket
													)
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
							</div>
						</div>
					</div>
				</div>
			)}
		</ClayIconSpriteContext.Consumer>
	);
};

export default TicketCard;
