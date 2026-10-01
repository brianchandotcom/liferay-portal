import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import EventChip from './EventChip';
import EventDropdown from './EventDropdown';
import React from 'react';
import {Align} from '@clayui/drop-down';
import {Event} from 'event-analysis/utils/types';
import {useAttributes} from '../context/attributes';

interface IEventSectionProps {
	event?: Event;
	onEventChange: (event: Event | null) => void;
}

const EventSection: React.FC<IEventSectionProps> = ({event, onEventChange}) => {
	const {deleteAllAttributes} = useAttributes();

	const handleEventChange = (event: Event | null): void => {
		onEventChange(event);

		deleteAllAttributes();
	};

	return (
		<div className="event-section-root d-flex flex-column">
			<div className="section-header">
				{Liferay.Language.get('analyze')}
			</div>

			<div className="event-container d-flex flex-column align-items-start">
				<div className="event-list">
					{event && (
						<EventChip
							event={event}
							onEventChange={handleEventChange}
						/>
					)}
				</div>

				{!event && (
					<EventDropdown
						alignmentPosition={Align.LeftTop}
						onEventChange={handleEventChange}
						trigger={
							<ClayButton
								aria-label={Liferay.Language.get('add')}
								className="button-root add-event-button"
								size="sm"
							>
								<ClayIcon className="icon-root" symbol="plus" />
							</ClayButton>
						}
					/>
				)}
			</div>
		</div>
	);
};

export default EventSection;
