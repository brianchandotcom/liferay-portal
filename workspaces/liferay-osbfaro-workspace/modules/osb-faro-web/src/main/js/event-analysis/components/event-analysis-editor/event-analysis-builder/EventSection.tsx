/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React from 'react';
import {Event} from '~/event-analysis/utils/types';

import {
	DeleteAllAttributes,
	withAttributesConsumer,
} from '../context/attributes';
import EventChip from './EventChip';
import EventDropdown from './EventDropdown';

interface IEventSectionProps {
	deleteAllAttributes: DeleteAllAttributes;
	event: Event;
	onEventChange: (event: Event | null) => void;
}

const EventSection: React.FC<IEventSectionProps> = ({
	deleteAllAttributes,
	event,
	onEventChange,
}) => {
	const handleEventChange = (event: Event | null): void => {
		onEventChange(event);

		deleteAllAttributes();
	};

	return (
		<div className="align-items-center d-flex event-section-root">
			<div className="section-header">
				{Liferay.Language.get('analyze')}
			</div>

			<div className="d-flex event-container justify-content-between">
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
								className="add-event-button button-root"
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

export default withAttributesConsumer(EventSection);
