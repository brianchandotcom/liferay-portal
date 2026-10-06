import AttributeBreakdownSection from './AttributeBreakdownSection';
import AttributeFilterSection from './AttributeFilterSection';
import EventSection from './EventSection';
import React from 'react';
import useConditionAnnouncements from './useConditionAnnouncements';
import {Event} from 'event-analysis/utils/types';
import {useAnnounce} from 'shared/components/condition-chip/AnnounceContext';

interface IEventAnalysisBuilderProps {
	event?: Event;
	onEventChange: (event: Event | null) => void;
}

const EventAnalysisBuilder: React.FC<IEventAnalysisBuilderProps> = ({
	event,
	onEventChange,
}) => {
	useConditionAnnouncements(event, useAnnounce());

	return (
		<div className="event-analysis-builder-root d-flex flex-column">
			<EventSection event={event} onEventChange={onEventChange} />

			<AttributeBreakdownSection eventId={event?.id} />

			<AttributeFilterSection eventId={event?.id} />
		</div>
	);
};

export default EventAnalysisBuilder;
