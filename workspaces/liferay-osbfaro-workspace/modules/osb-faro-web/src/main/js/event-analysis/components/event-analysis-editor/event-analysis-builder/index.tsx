/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Event} from '~/event-analysis/utils/types';

import AttributeBreakdownSection from './AttributeBreakdownSection';
import AttributeFilterSection from './AttributeFilterSection';
import EventSection from './EventSection';

interface IEventAnalysisBuilderProps {
	event?: Event;
	onEventChange: (event: Event | null) => void;
}

const EventAnalysisBuilder: React.FC<IEventAnalysisBuilderProps> = ({
	event,
	onEventChange,
}) => (
	<div className="d-flex event-analysis-builder-root flex-column">
		<EventSection event={event} onEventChange={onEventChange} />

		<AttributeBreakdownSection eventId={event?.id} />

		<AttributeFilterSection eventId={event?.id} />
	</div>
);

export default EventAnalysisBuilder;
