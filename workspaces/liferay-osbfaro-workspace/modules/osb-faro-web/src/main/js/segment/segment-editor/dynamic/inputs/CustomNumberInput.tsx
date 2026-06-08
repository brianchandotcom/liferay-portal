/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import autobind from 'autobind-decorator';
import getCN from 'classnames';
import {Map} from 'immutable';
import React from 'react';
import Input from '~/shared/components/Input';
import Form from '~/shared/components/form';

import {
	PropertyTypes,
	RelationalOperators,
	SUPPORTED_OPERATORS_MAP,
	isKnown,
	isUnknown,
} from '../utils/constants';
import {getPropertyValue} from '../utils/custom-inputs';
import {ISegmentEditorCustomInputBase} from '../utils/types';
import {isOfKnownType, isValid} from '../utils/utils';

const NUMBER_OPERATORS = SUPPORTED_OPERATORS_MAP[PropertyTypes.Number];

interface ICustomNumberInputProps extends ISegmentEditorCustomInputBase {
	touched: boolean;
	valid: boolean;
}

export default class CustomNumberInput extends React.Component<ICustomNumberInputProps> {
	getSelectedOperatorKey() {
		const criterionIMap = this.props.value.getIn(
			['criterionGroup', 'items', 0],
			Map()
		);

		const operatorName = criterionIMap.get('operatorName');
		const value = criterionIMap.get('value');

		let operatorKey = operatorName;

		const valueNull = value === null;

		if (operatorName === RelationalOperators.EQ && valueNull) {
			operatorKey = isUnknown;
		}
		else if (operatorName === RelationalOperators.NE && valueNull) {
			operatorKey = isKnown;
		}

		return NUMBER_OPERATORS.find(({key}) => key === operatorKey)?.key;
	}

	@autobind
	handleBlur() {
		const {onChange, value} = this.props;

		onChange({
			touched: true,
			valid: isValid(getPropertyValue(value, 'value', 0)),
			value,
		});
	}

	@autobind
	handleOperatorChange(operator: React.Key) {
		const {onChange, value: valueIMap} = this.props;

		let newVal = null;

		newVal = valueIMap.setIn(
			['criterionGroup', 'items', 0, 'operatorName'],
			NUMBER_OPERATORS.find(({key}) => key === operator)?.name
		);

		if (isOfKnownType(String(operator))) {
			newVal = newVal.setIn(
				['criterionGroup', 'items', 0, 'value'],
				null
			);
		}
		else if (getPropertyValue(valueIMap, 'value', 0) === null) {
			newVal = newVal.setIn(['criterionGroup', 'items', 0, 'value'], '');
		}

		onChange({
			valid: isValid(
				newVal.getIn(['criterionGroup', 'items', 0, 'value'])
			),
			value: newVal,
		});
	}

	@autobind
	handleValueChange(event: React.ChangeEvent<HTMLInputElement>) {
		const {value} = event.target;

		const {onChange, value: valueIMap} = this.props;

		let numberVal: string | number = '';

		const valid = isValid(value);

		if (valid) {
			numberVal = value;
		}

		onChange({
			valid,
			value: valueIMap.setIn(
				['criterionGroup', 'items', 0, 'value'],
				numberVal
			),
		});
	}

	render() {
		const {
			className,
			displayValue,
			property: {entityName},
			touched,
			valid,
			value,
		} = this.props;

		const selectedOperatorKey = this.getSelectedOperatorKey();
		const knownType = isOfKnownType(selectedOperatorKey ?? '');

		return (
			<div className="criteria-statement">
				<Form.Group autoFit>
					<Form.GroupItem className="entity-name" label shrink>
						{entityName}
					</Form.GroupItem>

					<Form.GroupItem className="display-value" label shrink>
						{displayValue}
					</Form.GroupItem>

					<Form.GroupItem shrink>
						<Picker
							items={NUMBER_OPERATORS.map(({key, label}) => ({
								label,
								value: key,
							}))}
							onSelectionChange={this.handleOperatorChange}
							selectedKey={selectedOperatorKey}
						>
							{({label, value}) => (
								<Option key={value}>{label}</Option>
							)}
						</Picker>
					</Form.GroupItem>

					{!knownType && (
						<Form.GroupItem
							className={getCN(className, {
								'has-error': !valid && touched,
							})}
							shrink
						>
							<Input
								data-testid="number-input"
								onBlur={this.handleBlur}
								onChange={this.handleValueChange}
								type={knownType ? 'text' : 'number'}
								value={getPropertyValue(value, 'value', 0)}
							/>
						</Form.GroupItem>
					)}
				</Form.Group>
			</div>
		);
	}
}
