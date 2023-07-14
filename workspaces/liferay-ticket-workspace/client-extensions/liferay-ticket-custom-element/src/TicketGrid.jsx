import DataGrid from 'react-data-grid';
import ClayIcon from '@clayui/icon';
import 'react-data-grid/lib/styles.css';

export const TicketGrid = ({tickets}) => {
	return (
		<DataGrid
			columns={[
				{
					key: 'subject',
					name: 'Subject',
					resizable: true,
					width: '45%',
					formatter: ({row}) => (
						<span>
							{row.suggestions && row.suggestions.length > 0 && (
								<ClayIcon
									className="mr-1"
									spritemap={Liferay.Icons.spritemap}
									symbol="link"
								/>
							)}
							{row.subject}
						</span>
					),
				},
				{
					key: 'resolution',
					name: 'Resolution',
					resizable: true,
					width: '15%',
				},
				{
					key: 'ticketStatus',
					name: 'Status',
					resizable: true,
					width: '15%',
					formatter: ({row}) => (
						<span>
							{row.ticketStatus === 'Queued' && (
								<ClayIcon
									className="mr-1"
									spritemap={Liferay.Icons.spritemap}
									symbol="bolt"
								/>
							)}
							{row.ticketStatus}
						</span>
					),
				},
				{
					key: 'priority',
					name: 'Priority',
					resizable: true,
					width: '10%',
				},
				{key: 'type', name: 'Type', resizable: true, width: '10%'},
				{
					key: 'supportRegion',
					name: 'Region',
					resizable: true,
					width: '10%',
				},
			]}
			rows={tickets.rows}
			rowKeyGetter={(row) => row.id}
		/>
	);
};
