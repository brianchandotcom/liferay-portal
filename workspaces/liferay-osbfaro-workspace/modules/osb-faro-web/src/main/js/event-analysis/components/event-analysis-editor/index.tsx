/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {CalculationTypes, Event} from '~/event-analysis/utils/types';
import Card from '~/shared/components/Card';
import CardTabs, {CardTabSizes} from '~/shared/components/CardTabs';
import Checkbox from '~/shared/components/Checkbox';
import {ReportContainer} from '~/shared/components/download-report/DownloadPDFReport';
import {DropdownRangeKey} from '~/shared/components/dropdown-range-key/DropdownRangeKey';
import {RangeSelectors} from '~/shared/types';

import BreakdownTable from './event-analysis-breakdown';
import EventAnalysisBuilder from './event-analysis-builder';

interface IEventAnalysisEditorProps extends React.HTMLAttributes<HTMLElement> {
	channelId: string;
	compareToPrevious: boolean;
	event: Event;
	onCompareToPreviousChange: (compareToPrevious: boolean) => void;
	onEventChange: (event: Event | null) => void;
	onRangeSelectorsChange: (rangeSelectors: RangeSelectors) => void;
	onTypeChange: (type: CalculationTypes) => void;
	rangeSelectors: RangeSelectors;
	type: CalculationTypes;
}

const EventAnalysisEditor: React.FC<IEventAnalysisEditorProps> = ({
	channelId,
	compareToPrevious,
	event,
	onCompareToPreviousChange,
	onEventChange,
	onRangeSelectorsChange,
	onTypeChange,
	rangeSelectors,
	type,
}) => (
	<Card
		className="event-analysis-editor-root"
		reportContainer={ReportContainer.EventAnalysisPage}
	>
		<EventAnalysisBuilder event={event} onEventChange={onEventChange} />

		<div className="d-flex flex-column-reverse flex-md-row justify-content-between options-container">
			<CardTabs
				activeTabId={type}
				className="type-selector"
				size={CardTabSizes.Small}
				tabs={[
					{
						onClick: () => onTypeChange(CalculationTypes.Total),
						tabId: CalculationTypes.Total,
						title: Liferay.Language.get('total'),
					},
					{
						onClick: () => onTypeChange(CalculationTypes.Unique),
						tabId: CalculationTypes.Unique,
						title: Liferay.Language.get('unique'),
					},
					{
						onClick: () => onTypeChange(CalculationTypes.Average),
						tabId: CalculationTypes.Average,
						title: Liferay.Language.get('average'),
					},
				]}
			/>

			<div className="align-items-center d-flex mb-3 mb-md-0">
				<Checkbox
					checked={compareToPrevious}
					className="compare-to-previous-checkbox mb-0 mr-4"
					label={Liferay.Language.get('compare-to-previous')}
					onChange={(event) =>
						onCompareToPreviousChange(event.currentTarget.checked)
					}
				/>

				<DropdownRangeKey
					legacy={false}
					onRangeSelectorChange={onRangeSelectorsChange}
					rangeSelectors={rangeSelectors}
				/>
			</div>
		</div>

		<BreakdownTable
			channelId={channelId}
			compareToPrevious={compareToPrevious}
			event={event}
			rangeSelectors={rangeSelectors}
			type={type}
		/>
	</Card>
);

export default EventAnalysisEditor;
