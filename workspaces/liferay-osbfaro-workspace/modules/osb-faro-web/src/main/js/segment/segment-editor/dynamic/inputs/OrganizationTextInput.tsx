/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import * as API from '~/shared/api';

import {getPropertyValue} from '../utils/custom-inputs';
import CustomStringInput, {ICustomStringInputProps} from './CustomStringInput';

const OrganizationTextInput: React.FC<ICustomStringInputProps> = (props) => {
	const {
		channelId,
		groupId,
		property: {id},
		value,
	} = props;

	const fieldValuesDataSourceFn = () =>
		API.individuals
			.fetchFieldValues({
				channelId,
				fieldMappingFieldName: id,
				groupId,
				query: getPropertyValue(value, 'value', 0),
			})
			.then(({items}) => items);

	return (
		<CustomStringInput
			{...props}
			autocomplete={!!id}
			fieldValuesDataSourceFn={fieldValuesDataSourceFn}
		/>
	);
};
export default OrganizationTextInput;
