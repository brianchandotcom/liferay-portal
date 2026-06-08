/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';
import {Event} from '~/event-analysis/utils/types';
import Chip from '~/shared/components/Chip';

import EventDropdown from './EventDropdown';

interface IEventChipProps {
	event: Event;
	onEventChange: (event: Event | null) => void;
}

const EventChip: React.FC<IEventChipProps> = React.forwardRef<
	HTMLDivElement,
	IEventChipProps & {onClick?: () => void}
>(({event: {displayName, name}, onClick, onEventChange}, ref) => (
	<Chip
		className="event-chip-root"
		onCloseClick={() => onEventChange(null)}
		ref={ref}
	>
		<ClayButton
			className="button-root event-name"
			displayType="unstyled"
			onClick={onClick}
		>
			{displayName || name}
		</ClayButton>
	</Chip>
));

const EventChipWrapper: React.FC<IEventChipProps> = ({
	event,
	onEventChange,
}) => (
	<EventDropdown
		eventId={event.id}
		onEventChange={onEventChange}
		trigger={<EventChip event={event} onEventChange={onEventChange} />}
	/>
);

export default EventChipWrapper;
