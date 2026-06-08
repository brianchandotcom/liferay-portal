/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {ENTITY_MAP} from '~/segment/segment-editor/dynamic/inputs/IndividualSelectInput';
import {PropertyTypes} from '~/segment/segment-editor/dynamic/utils/constants';
import {isOfKnownType} from '~/segment/segment-editor/dynamic/utils/utils';

import {IDisplayComponentProps} from '../types';
import {
	getOperatorLabel,
	maybeFormatToKnownType,
	maybeFormatValue,
} from '../utils';
import ReferencedEntityDisplay from './ReferencedEntityDisplay';

const IndividualDisplay: React.FC<IDisplayComponentProps> = ({
	criterion,
	property,
	timeZoneId,
}) => {
	const {operatorName, propertyName, value} = criterion;

	const {entityName, label, type} = property;

	const renderContent = () => {
		switch (type) {
			case PropertyTypes.SelectText:
				return (
					<ReferencedEntityDisplay
						id={value}
						label={label}
						type={
							ENTITY_MAP[propertyName as keyof typeof ENTITY_MAP]
						}
					/>
				);
			default:
				return <b>{maybeFormatValue(value, type, timeZoneId)}</b>;
		}
	};

	const operatorKey = maybeFormatToKnownType(operatorName ?? '', value);
	const operatorLabel = getOperatorLabel(operatorKey, type);

	return (
		<>
			{entityName}

			<b>{label}</b>

			<span>{operatorLabel}</span>

			{!isOfKnownType(operatorKey) && renderContent()}
		</>
	);
};

export default IndividualDisplay;
