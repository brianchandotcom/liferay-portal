/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OrderedMap} from 'immutable';
import React from 'react';
import {Columns} from '~/shared/types';

import {EntityType} from '../../context/referencedObjects';
import {getPropertyValue, setPropertyValue} from '../../utils/custom-inputs';
import {ISegmentEditorCustomInputBase} from '../../utils/types';
import SelectEntityInput from './SelectEntityInput';

interface ICustomSelectEntityInputProps extends ISegmentEditorCustomInputBase {
	columns: Columns;
	entityLabel: string;
	entityType: EntityType;
	touched: boolean;
	valid: boolean;
	[key: string]: any;
}

const CustomSelectEntityInput: React.FC<ICustomSelectEntityInputProps> = ({
	onChange,
	value,
	...otherProps
}) => {
	const handleItemsChange = (items: OrderedMap<string, any>) => {
		const entity = items.first();

		if (items.size === 1) {
			onChange({
				valid: true,
				value: setPropertyValue(value, 'value', 0, entity.id),
			});
		}
		else {
			onChange(
				items
					.valueSeq()
					.toArray()
					.map(({id}) => ({
						valid: true,
						value: setPropertyValue(value, 'value', 0, id),
					}))
			);
		}
	};

	return (
		<SelectEntityInput
			{...otherProps}
			onItemsChange={handleItemsChange}
			onValidChange={onChange}
			value={getPropertyValue(value, 'value', 0)}
		/>
	);
};

export default CustomSelectEntityInput;
