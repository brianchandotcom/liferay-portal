/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayPanel from '@clayui/panel';
import {Ticket} from '../types';
import '../styles/TicketPreview.css';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useDraggable} from '@dnd-kit/core';
import {CSS} from '@dnd-kit/utilities';
import {useState} from 'react';
import {QueryClient, useMutation} from 'react-query';
import {icons} from '../icons';
import {Liferay} from '../services/liferay';
import {assignTicketToMe} from '../services/tickets';
export const SPRITEMAP = (Liferay as any).ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';

const usersIconsMap: {[key: string]: string} = {
	'': icons.NONE,
	'M2H8_ADMIN_ADAM': icons.ADAM,
	'M2H8_CUSTOMER_CARL': icons.CARL,
	'M2H8_SUPPORT_SAM': icons.SAM,
};

const getUserIcon = (ticket: Ticket): string => {
	if (ticket.assignee) {
		let icon = usersIconsMap[ticket.assignee.externalReferenceCode];
		if (!icon) {
			icon = usersIconsMap[''];
		} else {
			return icon;
		}
	}
	return usersIconsMap[''];
};

const TicketPreview: React.FC<{queryClient: QueryClient; ticket: Ticket}> = ({
	queryClient,
	ticket,
}) => {
	const [isLoading, setIsLoading] = useState(false);

	const { attributes, isDragging, listeners, setNodeRef, transform} = useDraggable({ data: ticket, id: ticket.id + '_draggable'});

	const style: React.CSSProperties = {
		backgroundColor: isDragging ? '#F4F9F9' : 'white',
		position: 'relative',
		transform: CSS.Translate.toString(transform),
		zIndex: isDragging ? 2147483647 : 1,
	};

	const mutation = useMutation({
		mutationFn: (ticket: Ticket) => {
			setIsLoading(true);
			return assignToMe(ticket);
		},
		onError: () => {
			setIsLoading(false);
			Liferay.Util.openToast({
				message: 'Something went wrong, please try again.',
				type: 'danger',
			});
		},
		onSuccess: (responseCode) => {
			setIsLoading(false);
			queryClient.invalidateQueries();
			if (responseCode === 200) {
				Liferay.Util.openToast({
					message: 'Ticket was assigned to you successfully!',
					type: 'success',
				});
			}
			else {
				Liferay.Util.openToast({
					message: 'Something went wrong, please try again.',
					type: 'danger',
				});
			}
		},
	});

	return (
		<div ref={setNodeRef} {...listeners} {...attributes} className="ticket-preview-container" style={style}>
			<ClayPanel collapsable displayType="secondary" showCollapseIcon={true}
				displayTitle={
					<ClayPanel.Title>
						<div className="ticket-subject">{ticket.subject}</div>
						<div className="mt-3">
							<img className="user-icon" src={getUserIcon(ticket)} />
							<div className="d-inline ml-2 ticket-assignee">
								{ticket.assignee ? <b>{ticket.assignee?.familyName}</b> : <i>{'Not assigned'}</i>}
							</div>
						</div>
					</ClayPanel.Title>
				}>
				<ClayPanel.Body>
					<p>
						{+ticket.description ? ticket.description: 'No description available.'}
					</p>
					{!ticket.assignee ? 
						<ClayButton displayType="secondary" onClick={() => mutation.mutate(ticket)} size="xs">

							{isLoading ? 
								<span className="inline-item inline-item-before">
									<ClayLoadingIndicator displayType="secondary" size="sm" />
								</span> : ''}

							{'Assign to Me'}
						</ClayButton> : ''}
				</ClayPanel.Body>
			</ClayPanel>
		</div>
	);
};

function assignToMe(ticket: Ticket) {
	return new Promise((resolve, reject) => {
		assignTicketToMe(ticket)
			.then((result) => {
				if (result.status === 200 || result.status === 204) {
					return resolve(200);
				}
				else {
					return reject(500);
				}
			})
			.catch(() => reject(500));
	});
}

export default TicketPreview;
