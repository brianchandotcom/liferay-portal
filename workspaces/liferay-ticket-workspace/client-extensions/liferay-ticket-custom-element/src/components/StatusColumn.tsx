import '../styles/StatusColumn.css';
import TicketPreview from './TicketPreview';
import {useDroppable} from '@dnd-kit/core';

const StatusColumn: React.FC<{status: any}> = ({status}) => {
	const {setNodeRef} = useDroppable({
		id: status.key + '_droppable',
	});

	return (
		<div
			ref={setNodeRef}
			id={status.key + '_droppable'}
			key={status.key + '_droppable'}
			className="status-col"
		>
			<h6>{status.name}</h6>
			{status.relatedTickets?.length === 0 && (
				<p>No tickets are available.</p>
			)}

			{status.relatedTickets?.length > 0 &&
				status.relatedTickets.map((t: any) => (
					<TicketPreview key={t.id} ticket={t} />
				))}
		</div>
	);
};

export default StatusColumn;
