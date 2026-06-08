/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import React from 'react';
import {DataTypes} from '~/event-analysis/utils/types';
import Form from '~/shared/components/form';

import {
	FunctionalOperators,
	RelationalOperators,
} from '../../../utils/constants';
import {Criterion} from '../../../utils/types';
import {getDefaultAttributeValue, getOperatorOptions} from './utils';

interface IOperatorSelectProps {
	dataType: DataTypes;
	onChange: (params: {criterion: Criterion}) => void;
	operatorName: Criterion['operatorName'];
}

const OperatorSelect: React.FC<IOperatorSelectProps> = ({
	dataType,
	onChange,
	operatorName,
}) => {
	if (dataType === DataTypes.Boolean) {
		return (
			<Form.GroupItem className="conjunction ml-1 mr-1" label shrink>
				{Liferay.Language.get('is')}
			</Form.GroupItem>
		);
	}

	return (
		<Form.GroupItem shrink>
			<Picker
				className="operator-input"
				items={
					getOperatorOptions(dataType) as {
						label: string;
						value: string;
					}[]
				}
				onSelectionChange={(newOperatorName) => {
					let criterion: Criterion = {
						operatorName:
							newOperatorName as unknown as Criterion['operatorName'],
					};

					if (
						(newOperatorName === FunctionalOperators.Between &&
							operatorName !== FunctionalOperators.Between) ||
						(newOperatorName !== FunctionalOperators.Between &&
							operatorName === FunctionalOperators.Between)
					) {
						criterion = {
							...criterion,
							value: getDefaultAttributeValue(
								dataType,
								newOperatorName as
									| FunctionalOperators
									| RelationalOperators
							),
						};
					}

					onChange({criterion});
				}}
				selectedKey={operatorName}
			>
				{({label, value}) => <Option key={value}>{label}</Option>}
			</Picker>
		</Form.GroupItem>
	);
};

export default OperatorSelect;
