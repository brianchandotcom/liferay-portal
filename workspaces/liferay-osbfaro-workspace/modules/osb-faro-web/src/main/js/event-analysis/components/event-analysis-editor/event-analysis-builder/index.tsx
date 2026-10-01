import AttributeBreakdownSection from './AttributeBreakdownSection';
import AttributeFilterSection from './AttributeFilterSection';
import DndProvider from 'shared/components/DndProvider';
import EventSection from './EventSection';
import React from 'react';
import {Event} from 'event-analysis/utils/types';
import {HTML5Backend} from 'react-dnd-html5-backend';

interface IEventAnalysisBuilderProps {
	event?: Event;
	onEventChange: (event: Event | null) => void;
}

const EventAnalysisBuilder: React.FC<IEventAnalysisBuilderProps> = ({
	event,
	onEventChange,
}) => (
	<DndProvider backend={HTML5Backend}>
		<div className="event-analysis-builder-root d-flex flex-column">
			<EventSection event={event} onEventChange={onEventChange} />

			<AttributeBreakdownSection eventId={event?.id} />

			<AttributeFilterSection eventId={event?.id} />
		</div>
	</DndProvider>
);

export default EventAnalysisBuilder;
