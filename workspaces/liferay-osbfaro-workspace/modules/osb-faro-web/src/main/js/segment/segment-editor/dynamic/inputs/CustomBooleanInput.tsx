/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {getPropertyValue, setPropertyValue} from '../utils/custom-inputs';
import {ISegmentEditorCustomInputBase} from '../utils/types';
import BooleanInput from './BooleanInput';

const CustomBooleanInput: React.FC<ISegmentEditorCustomInputBase> = ({
	displayValue,
	id,
	onChange,
	operatorRenderer,
	property,
	value: valueIMap,
}) => {
	const handleChange = ({value}: {value: React.Key}) =>
		onChange({value: setPropertyValue(valueIMap, 'value', 0, value)});

	return (
		<BooleanInput
			displayValue={displayValue ?? ''}
			id={id}
			onChange={handleChange}
			operatorRenderer={operatorRenderer ?? 'div'}
			property={property}
			value={getPropertyValue(valueIMap, 'value', 0)}
		/>
	);
};

export default CustomBooleanInput;
