/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import autobind from 'autobind-decorator';
import React from 'react';
import Form from '~/shared/components/form';

import {
	INPUT_DATE_FORMAT,
	PropertyTypes,
	SUPPORTED_OPERATORS_MAP,
} from '../utils/constants';
import {
	getCompleteDate,
	getOperator,
	setCompleteDate,
	setOperator,
} from '../utils/custom-inputs';
import {Criterion, ISegmentEditorCustomInputBase} from '../utils/types';
import DateInput from './DateInput';

const DATE_OPERATORS = SUPPORTED_OPERATORS_MAP[PropertyTypes.Date];

export default class CustomDateInput extends React.Component<ISegmentEditorCustomInputBase> {
	@autobind
	handleDateChange(newDate: Criterion | Criterion[]) {
		const {onChange, value} = this.props;

		const newValue = Array.isArray(newDate)
			? newDate[0]?.value
			: newDate.value;

		onChange({value: setCompleteDate(value, newValue)});
	}

	@autobind
	handleOperatorChange(newValue: React.Key) {
		const {onChange, value} = this.props;

		onChange({value: setOperator(value, 0, newValue)});
	}

	@autobind
	renderOperatorDropdown() {
		const {value} = this.props;

		return (
			<Form.GroupItem className="operator" shrink>
				<Picker
					className="criterion-input operator-input"
					items={DATE_OPERATORS.map(({key, label}) => ({
						label,
						value: key,
					}))}
					onSelectionChange={this.handleOperatorChange}
					selectedKey={getOperator(value, 0)}
				>
					{({label, value}) => <Option key={value}>{label}</Option>}
				</Picker>
			</Form.GroupItem>
		);
	}

	render() {

		// eslint-disable-next-line @typescript-eslint/no-unused-vars
		const {onChange: _onChange, value, ...otherProps} = this.props;

		return (
			<DateInput
				{...otherProps}
				displayFormat={INPUT_DATE_FORMAT}
				onChange={this.handleDateChange}
				operatorRenderer={this.renderOperatorDropdown}
				value={getCompleteDate(value)}
			/>
		);
	}
}
