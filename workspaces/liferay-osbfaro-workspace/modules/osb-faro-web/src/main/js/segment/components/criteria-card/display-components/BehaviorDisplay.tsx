/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map} from 'immutable';
import React from 'react';
import {EntityType} from '~/segment/segment-editor/dynamic/context/referencedObjects';
import {
	getFilterCriterionIMap,
	getPropertyValue,
} from '~/segment/segment-editor/dynamic/utils/custom-inputs';
import {parseActivityKey} from '~/segment/segment-editor/dynamic/utils/utils';
import {SegmentTypes} from '~/shared/util/constants';
import {ASSET_TYPE_LANG_MAP} from '~/shared/util/lang';
import {CustomValue} from '~/shared/util/records';

import {IDisplayComponentProps} from '../types';
import {getOperatorLabel, maybeFormatToKnownType} from '../utils';
import DateFilterConjunctionDisplay from './DateFilterConjunctionDisplay';
import OccurenceConjunctionDisplay from './OccurenceConjunctionDisplay';
import ReferencedEntityDisplay from './ReferencedEntityDisplay';

const BehaviorDisplay: React.FC<IDisplayComponentProps> = ({
	criterion,
	property,
	segmentType,
}) => {
	const {operatorName, value} = criterion;

	const valueIMap = value as CustomValue;

	const {entityName, label, type} = property;

	const {id, objectType} = parseActivityKey(
		getPropertyValue(valueIMap, 'value', 0)
	);

	const operatorKey = maybeFormatToKnownType(operatorName ?? '', name);

	const operatorLabel = getOperatorLabel(operatorKey, type);

	const eventOperator = valueIMap.get('operator');

	const occurenceCount = valueIMap.get('value');

	const conjunctionCriterion = (
		getFilterCriterionIMap(valueIMap, 1) ||
		Map({propertyName: 'completeDate'})
	).toJS();

	return (
		<>
			{entityName}

			<span>{operatorLabel}</span>

			<span>{label}</span>

			<ReferencedEntityDisplay
				id={id}
				label={
					ASSET_TYPE_LANG_MAP[
						objectType as keyof typeof ASSET_TYPE_LANG_MAP
					]
				}
				type={EntityType.Assets}
			/>

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

export default BehaviorDisplay;
