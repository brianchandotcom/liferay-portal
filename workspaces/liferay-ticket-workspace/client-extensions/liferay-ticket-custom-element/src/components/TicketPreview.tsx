import { Ticket } from '../types';
import ClayPanel from '@clayui/panel';
import ClayButton from '@clayui/button';
import '../styles/TicketPreview.css';
import { icons } from '../icons';
import { useDraggable } from '@dnd-kit/core';
import { CSS } from '@dnd-kit/utilities';
import { assignTicketToMe } from '../services/tickets';
import { Liferay } from '../services/liferay';


const usersIconsMap: { [key: string]: string } = {
	'M2H8_ADMIN_ADAM': icons.adam,
	'M2H8_CUSTOMER_CARL': icons.carl,
	'M2H8_SUPPORT_SAM': icons.sam,
	'': icons.none
};

const getUserIcon = (ticket: Ticket): string => {

	if (ticket.assignee) {
		let icon = usersIconsMap[ticket.assignee.externalReferenceCode];

		if (!icon) {
			icon = usersIconsMap[''];
		}

		return icon;
	}
	return usersIconsMap[''];
};

const TicketPreview: React.FC<{ ticket: Ticket }> = ({ ticket }) => {
	const {
		attributes,
		listeners,
		setNodeRef,
		transform,
		isDragging,
	} = useDraggable({
		id: ticket.id + '_draggable',
		data: ticket,
	});

	const style: React.CSSProperties = {
		transform: CSS.Translate.toString(transform),
		backgroundColor: isDragging ? '#F4F9F9' : 'white',
		position: 'relative',
		zIndex: isDragging ? 2147483647 : 1
	};

	const assignToMe = async () => {
		let result = await assignTicketToMe(ticket);

		if (result.status === 200 || result.status === 204) {
			Liferay.Util.openToast({
				message: 'Ticket was assigned to you successfully!',
				type: 'success',
			});
		} else {
			Liferay.Util.openToast({
				message: 'Something went wrong, please try again.',
				type: 'danger',
			});
		}
	};

	return (
		<div
			ref={setNodeRef}
			{...listeners}
			{...attributes}
			style={style}
			className="ticket-preview-container"
		>
			<ClayPanel
				collapsable
				displayTitle={
					<ClayPanel.Title>
						<div className="ticket-subject">{ticket.subject}</div>
						<div className="mt-3">
							<img className='user-icon' src={'data:image/svg+xml;base64, ' + getUserIcon(ticket)} />
							<p className="d-inline ml-2 ticket-assignee">
								{ticket.assignee ? ticket.assignee?.familyName : 'Not assigned'}
							</p>
						</div>
					</ClayPanel.Title>
				}
				displayType="secondary"
				showCollapseIcon={true}
			>
				<ClayPanel.Body>
					<p>
						{+ticket.description
							? ticket.description
							: 'No description available.'}
					</p>
					{!ticket.assignee ?
						<ClayButton onClick={assignToMe} size='xs' displayType="secondary">
							Assign to me
						</ClayButton> : ''
					}
				</ClayPanel.Body>
			</ClayPanel>
		</div>
	);
};

export default TicketPreview;
