/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {
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

const AccountDisplay: React.FC<IDisplayComponentProps> = ({
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

	return (
		<>
			{entityName}

			<b>{label}</b>

			<span>{operatorLabel}</span>

			{!isOfKnownType(operatorKey) && (
				<b>{maybeFormatValue(value, type, timeZoneId)}</b>
			)}
		</>
	);
};

export default AccountDisplay;
