/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {EntityType} from '~/segment/segment-editor/dynamic/context/referencedObjects';
import {PropertyTypes} from '~/segment/segment-editor/dynamic/utils/constants';
import {
	getOperator,
	getPropertyValue,
} from '~/segment/segment-editor/dynamic/utils/custom-inputs';
import {isOfKnownType} from '~/segment/segment-editor/dynamic/utils/utils';

import {ICustomDisplayComponentProps} from '../types';
import {
	getOperatorLabel,
	maybeFormatToKnownType,
	maybeFormatValue,
} from '../utils';
import ReferencedEntityDisplay from './ReferencedEntityDisplay';

const OrganizationDisplay: React.FC<ICustomDisplayComponentProps> = ({
	criterion,
	property,
	timeZoneId,
}) => {
	const value = getPropertyValue(criterion.value, 'value', 0);

	const {entityName, label, type} = property;

	const referencedEntity = type === PropertyTypes.OrganizationSelectText;

	const operatorName = referencedEntity
		? criterion.operatorName
		: getOperator(criterion.value, 0);

	const propertyDataType = type.replace('organization-', '');

	const operatorKey = maybeFormatToKnownType(operatorName ?? '', value);

	const operatorLabel = getOperatorLabel(operatorKey, type);

	const renderContent = () => {
		if (referencedEntity) {
			return (
				<ReferencedEntityDisplay
					id={value}
					label={Liferay.Language.get('organization')}
					type={EntityType.Organizations}
				/>
			);
		}
		else {
			return (
				<b>{maybeFormatValue(value, propertyDataType, timeZoneId)}</b>
			);
		}
	};

	return (
		<>
			{entityName}

			{property.name !== 'id' && <b>{label}</b>}

			<span>{operatorLabel}</span>

			{!isOfKnownType(operatorKey) && renderContent()}
		</>
	);
};

export default OrganizationDisplay;
