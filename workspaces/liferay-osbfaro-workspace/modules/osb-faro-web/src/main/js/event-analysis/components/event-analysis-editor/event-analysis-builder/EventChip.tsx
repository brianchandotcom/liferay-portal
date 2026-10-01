import ConditionChip from './ConditionChip';
import EventDropdown from './EventDropdown';
import React from 'react';
import {Event} from 'event-analysis/utils/types';

interface IEventChipProps {
	event: Event;
	onEventChange: (event: Event | null) => void;
}

const EventChip: React.FC<IEventChipProps> = ({event, onEventChange}) => {
	const name = event.displayName || event.name;

	return (
		<EventDropdown
			eventId={event.id}
			onEventChange={onEventChange}
			trigger={
				<ConditionChip
					icon="click"
					label={name}
					name={name}
					onRemove={() => onEventChange(null)}
				/>
			}
		/>
	);
};

export default EventChip;
