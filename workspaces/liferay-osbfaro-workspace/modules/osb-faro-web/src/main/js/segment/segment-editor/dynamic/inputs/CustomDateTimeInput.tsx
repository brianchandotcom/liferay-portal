/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import autobind from 'autobind-decorator';
import React from 'react';
import Form from '~/shared/components/form';

import {PropertyTypes, SUPPORTED_OPERATORS_MAP} from '../utils/constants';
import {
	getCompleteDate,
	getOperator,
	setCompleteDate,
	setOperator,
} from '../utils/custom-inputs';
import {Criterion, ISegmentEditorCustomInputBase} from '../utils/types';
import DateTimeInput from './DateTimeInput';

const DATE_TIME_OPERATORS = SUPPORTED_OPERATORS_MAP[PropertyTypes.DateTime];

export default class CustomDateTimeInput extends React.Component<ISegmentEditorCustomInputBase> {
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
					items={DATE_TIME_OPERATORS.map(({key, label}) => ({
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
		const {timeZoneId, value, ...otherProps} = this.props;

		return (
			<DateTimeInput
				{...otherProps}
				onChange={this.handleDateChange}
				operatorRenderer={this.renderOperatorDropdown}
				timeZoneId={timeZoneId}
				value={getCompleteDate(value)}
			/>
		);
	}
}
