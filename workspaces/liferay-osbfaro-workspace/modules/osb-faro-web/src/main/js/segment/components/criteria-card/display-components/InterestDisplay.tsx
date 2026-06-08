/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {INTEREST_BOOLEAN_OPTIONS} from '~/segment/segment-editor/dynamic/utils/constants';
import {getPropertyValue} from '~/segment/segment-editor/dynamic/utils/custom-inputs';
import {CustomValue} from '~/shared/util/records';

import {IDisplayComponentProps} from '../types';
import {maybeFormatValue} from '../utils';

const InterestDisplay: React.FC<IDisplayComponentProps> = ({
	criterion,
	property,
	timeZoneId,
}) => {
	const valueIMap = criterion.value as CustomValue;

	const {entityName, type} = property;

	const interestName = getPropertyValue(valueIMap, 'value', 0);

	const operatorLabel = INTEREST_BOOLEAN_OPTIONS.find(
		({value}) => value === getPropertyValue(valueIMap, 'value', 1)
	)?.label;

	return (
		<>
			{entityName}

			<span>{operatorLabel}</span>

			<span>{Liferay.Language.get('interested-in').toLowerCase()}</span>

			<b>{maybeFormatValue(interestName, type, timeZoneId)}</b>
		</>
	);
};

export default InterestDisplay;
