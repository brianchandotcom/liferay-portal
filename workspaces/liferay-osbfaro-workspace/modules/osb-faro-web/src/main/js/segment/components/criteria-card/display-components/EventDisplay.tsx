/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map} from 'immutable';
import React from 'react';
import {getFilterCriterionIMap} from '~/segment/segment-editor/dynamic/utils/custom-inputs';
import {SegmentTypes} from '~/shared/util/constants';
import {CustomValue} from '~/shared/util/records';

import {IDisplayComponentProps} from '../types';
import {getOperatorLabel, maybeFormatToKnownType} from '../utils';
import DateFilterConjunctionDisplay from './DateFilterConjunctionDisplay';
import OccurenceConjunctionDisplay from './OccurenceConjunctionDisplay';

const EventDisplay: React.FC<IDisplayComponentProps> = ({
	criterion,
	property,
	segmentType,
}) => {
	const {operatorName, value} = criterion;

	const valueIMap = value as CustomValue;

	const {label, options, type} = property;

	const operatorKey = maybeFormatToKnownType(operatorName ?? '', name);

	const operatorLabel = getOperatorLabel(operatorKey, type);

	const eventOperator = valueIMap.get('operator');

	const occurenceCount = valueIMap.get('value');

	const conjunctionCriterion = (
		getFilterCriterionIMap(valueIMap, 2) ||
		Map({propertyName: 'completeDate'})
	).toJS();

	if (
		options?.length &&
		options.some((option) => option.label === 'hidden' && option.value)
	) {
		return (
			<b className="undefined-property">
				{Liferay.Language.get('custom-event-no-longer-exists')}
			</b>
		);
	}

	return (
		<>
			<span className="sentence-start">
				{Liferay.Language.get('individual')}
			</span>

			<span>{operatorLabel}</span>

			<span>{Liferay.Language.get('performed').toLowerCase()}</span>

			<b>{label}</b>

			{segmentType === SegmentTypes.Batch && (
				<>
					<OccurenceConjunctionDisplay
						operatorName={eventOperator}
						value={occurenceCount}
					/>

					<DateFilterConjunctionDisplay
						conjunctionCriterion={conjunctionCriterion}
					/>
				</>
			)}
		</>
	);
};

export default EventDisplay;
