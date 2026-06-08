/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map} from 'immutable';
import React from 'react';
import {PropertyTypes} from '~/segment/segment-editor/dynamic/utils/constants';
import {
	getFilterCriterionIMap,
	getIndexFromPropertyName,
	getOperator,
	getPropertyValue,
} from '~/segment/segment-editor/dynamic/utils/custom-inputs';
import {isOfKnownType} from '~/segment/segment-editor/dynamic/utils/utils';
import {CustomValue} from '~/shared/util/records';

import {IDisplayComponentProps} from '../types';
import {
	getOperatorLabel,
	maybeFormatToKnownType,
	maybeFormatValue,
} from '../utils';
import DateFilterConjunctionDisplay from './DateFilterConjunctionDisplay';

const SessionDisplay: React.FC<IDisplayComponentProps> = ({
	criterion,
	property,
	timeZoneId,
}) => {
	const valueIMap = criterion.value as CustomValue;

	const {entityName, label, type} = property;

	const operatorName = getOperator(valueIMap, 0);

	const value = getPropertyValue(valueIMap, 'value', 0);

	const operatorKey = maybeFormatToKnownType(operatorName, value);

	const operatorLabel = getOperatorLabel(operatorKey, type);

	let values = [0];

	if (type === PropertyTypes.SessionGeolocation) {
		const cityIndex = getIndexFromPropertyName(valueIMap, 'context/city');
		const countryIndex = getIndexFromPropertyName(
			valueIMap,
			'context/country'
		);
		const regionIndex = getIndexFromPropertyName(
			valueIMap,
			'context/region'
		);

		values = [cityIndex, regionIndex, countryIndex].filter(
			(index) => index > -1
		);
	}

	const conjunctionCriterion = (
		getFilterCriterionIMap(valueIMap, 1) || Map({propertyName: 'date'})
	).toJS();

	return (
		<>
			{entityName}

			<b>{label}</b>

			<span>{operatorLabel}</span>

			{!isOfKnownType(operatorKey) && (
				<b>
					{values
						.map((index) =>
							maybeFormatValue(
								getPropertyValue(valueIMap, 'value', index),
								type,
								timeZoneId
							)
						)
						.join(', ')}
				</b>
			)}

			{type !== PropertyTypes.SessionDateTime && (
				<DateFilterConjunctionDisplay
					conjunctionCriterion={conjunctionCriterion}
				/>
			)}
		</>
	);
};

export default SessionDisplay;
