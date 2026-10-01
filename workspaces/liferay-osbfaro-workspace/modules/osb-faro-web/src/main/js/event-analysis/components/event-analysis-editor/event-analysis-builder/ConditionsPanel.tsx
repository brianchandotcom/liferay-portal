import EventAnalysisBuilder from './index';
import React from 'react';
import useConditionAnnouncements from './useConditionAnnouncements';
import {Event} from 'event-analysis/utils/types';
import {Heading} from '@clayui/core';

interface IConditionsPanelProps {
	event?: Event;
	onEventChange: (event: Event | null) => void;
}

const ConditionsPanel: React.FC<IConditionsPanelProps> = ({
	event,
	onEventChange,
}) => {
	const announcement = useConditionAnnouncements(event);

	return (
		<aside className="bg-white border-right event-analysis-conditions-panel">
			<div
				className="d-flex event-analysis-conditions-panel-content flex-column"
				data-report-expand
			>
				<div className="flex-shrink-0 px-4 py-3">
					<Heading fontSize={6} level={2} weight="semi-bold">
						{Liferay.Language.get('conditions-library')}
					</Heading>
				</div>

				<div className="flex-grow-1 overflow-auto" data-report-expand>
					<EventAnalysisBuilder
						event={event}
						onEventChange={onEventChange}
					/>
				</div>
			</div>

			<div aria-live="polite" className="sr-only" role="status">
				{announcement}
			</div>
		</aside>
	);
};

export default ConditionsPanel;
