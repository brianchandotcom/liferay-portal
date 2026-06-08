/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {
	ASSET_TYPE_OPTIONS,
	EVENT_TYPE_OPTIONS,
	OCCURRENCE_OPTIONS,
	getAssetTypeFromValue,
	getConjunctionCriterionFromValue,
	getEventTypeFromValue,
} from '~/segment/segment-editor/dynamic/inputs/shared/remote-filter-input-helpers';
import {getIndexFromPropertyName} from '~/segment/segment-editor/dynamic/utils/custom-inputs';
import {CustomValue} from '~/shared/util/records';

import {IDisplayComponentProps} from '../types';
import {getOperatorLabel, maybeFormatToKnownType} from '../utils';
import DateFilterConjunctionDisplay from './DateFilterConjunctionDisplay';

const TagDisplay: React.FC<IDisplayComponentProps> = ({
	criterion,
	property,
}) => {
	const {operatorName, value} = criterion;
	const {entityName, label, type} = property;

	const valueIMap = value as CustomValue | undefined;

	const operatorKey = maybeFormatToKnownType(operatorName ?? '', value);
	const operatorLabel = getOperatorLabel(operatorKey, type);

	const assetType = getAssetTypeFromValue(valueIMap);
	const eventType = getEventTypeFromValue(valueIMap);

	const occurrenceOperator = valueIMap?.get('operator') as string | null;
	const occurrenceCount = valueIMap?.get('value') as number | null;

	const eventTypeLabel =
		EVENT_TYPE_OPTIONS.find(({value}) => value === eventType)?.label ??
		String(eventType);

	const assetTypeLabel =
		ASSET_TYPE_OPTIONS.find(({value}) => value === assetType)?.label ??
		String(assetType);

	const occurrenceLabel =
		OCCURRENCE_OPTIONS.find(({value}) => value === occurrenceOperator)
			?.label ?? '';

	const tagNameIndex = valueIMap
		? getIndexFromPropertyName(valueIMap, 'tags/name')
		: -1;

	const tagName =
		tagNameIndex >= 0
			? (valueIMap?.getIn([
					'criterionGroup',
					'items',
					tagNameIndex,
					'value',
				]) as string) ?? label
			: label;

	const conjunctionCriterion = getConjunctionCriterionFromValue(valueIMap);

	return (
		<>
			{entityName}

			<span>{operatorLabel}</span>

			<span>{Liferay.Language.get('triggered').toLowerCase()}</span>

			<b>{eventTypeLabel}</b>

			<span>{Liferay.Language.get('on-the-tag').toLowerCase()}</span>

			<b>{tagName}</b>

			<span>{Liferay.Language.get('for').toLowerCase()}</span>

			<b>{assetTypeLabel}</b>

			<span>{Liferay.Language.get('asset-type').toLowerCase()}</span>

			<span>{occurrenceLabel}</span>

			{occurrenceCount !== null && (
				<>
					<b>{occurrenceCount}</b>

					<span>{Liferay.Language.get('times')}</span>
				</>
			)}

			<DateFilterConjunctionDisplay
				conjunctionCriterion={conjunctionCriterion}
			/>
		</>
	);
};

export default TagDisplay;
