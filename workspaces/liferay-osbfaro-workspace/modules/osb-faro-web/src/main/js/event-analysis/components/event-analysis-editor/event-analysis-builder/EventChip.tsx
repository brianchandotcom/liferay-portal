import ConditionChip from 'shared/components/condition-chip/ConditionChip';
import React from 'react';
import {Event} from 'event-analysis/utils/types';

interface IEventChipProps {
	event: Event;
	onEventChange: (event: Event | null) => void;
}

const EventChip: React.FC<IEventChipProps> = ({event, onEventChange}) => {
	const name = event.displayName || event.name;

	return (
		<ConditionChip
			label={name}
			name={name}
			onRemove={() => onEventChange(null)}
			sticker={{symbol: 'click'}}
		/>
	);
};

export default EventChip;
