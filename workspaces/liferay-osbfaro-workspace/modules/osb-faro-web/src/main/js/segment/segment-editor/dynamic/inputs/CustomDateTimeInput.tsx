import DateTimeInput from './DateTimeInput';
import Form from 'shared/components/form';
import React from 'react';
import {Criterion, ISegmentEditorCustomInputBase} from '../utils/types';
import {
	getCompleteDate,
	getOperator,
	setCompleteDate,
	setOperator,
} from '../utils/custom-inputs';
import {Option, Picker} from '@clayui/core';
import {PropertyTypes, SUPPORTED_OPERATORS_MAP} from '../utils/constants';

const DATE_TIME_OPERATORS = SUPPORTED_OPERATORS_MAP[PropertyTypes.DateTime];

export default class CustomDateTimeInput extends React.Component<ISegmentEditorCustomInputBase> {
	constructor(props: ISegmentEditorCustomInputBase) {
		super(props);
		this.handleDateChange = this.handleDateChange.bind(this);
		this.handleOperatorChange = this.handleOperatorChange.bind(this);
		this.renderOperatorDropdown = this.renderOperatorDropdown.bind(this);
	}

	handleDateChange(newDate: Criterion | Criterion[]) {
		const {onChange, value} = this.props;

		const newValue = Array.isArray(newDate)
			? newDate[0]?.value
			: newDate.value;

		onChange({value: setCompleteDate(value, newValue)});
	}

	handleOperatorChange(newValue: React.Key) {
		const {onChange, value} = this.props;

		onChange({value: setOperator(value, 0, newValue)});
	}

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
