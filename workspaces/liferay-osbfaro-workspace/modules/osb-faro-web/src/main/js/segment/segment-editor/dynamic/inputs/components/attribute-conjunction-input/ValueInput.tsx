/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import getCN from 'classnames';
import React from 'react';
import {DataTypes} from '~/event-analysis/utils/types';
import {BOOLEAN_OPTIONS} from '~/event-analysis/utils/utils';
import DateInput from '~/shared/components/DateInput';
import DateRangeInput, {DateRange} from '~/shared/components/DateRangeInput';
import DurationInput from '~/shared/components/DurationInput';
import Input from '~/shared/components/Input';
import Form from '~/shared/components/form';

import {FunctionalOperators} from '../../../utils/constants';
import {Criterion} from '../../../utils/types';
import {isValid} from '../../../utils/utils';
import {BetweenNumber} from '../BetweenNumberInput';
import NumberInput from '../NumberInput';
import {createOption, validateAttributeValue} from './utils';

interface IValueInputProps {
	dataType: DataTypes;
	onChange: (params: {
		criterion?: Criterion;
		touched?: {attributeValue: boolean};
		valid?: {attributeValue: boolean};
	}) => void;
	operatorName: Criterion['operatorName'];
	touched: boolean;
	valid: boolean;
	value: string | number | BetweenNumber | DateRange;
}

const ValueInput: React.FC<IValueInputProps> = ({
	dataType,
	onChange,
	operatorName,
	touched,
	valid,
	value,
}) => {
	const handleAttributeValueBlur = () => {
		onChange({
			touched: {attributeValue: true},
		});
	};

	switch (dataType) {
		case DataTypes.Boolean:
			return (
				<Picker
					className="boolean-input"
					data-testid="attribute-value-boolean-input"
					items={BOOLEAN_OPTIONS.map((option) =>
						createOption(option, dataType)
					)}
					onBlur={handleAttributeValueBlur}
					onSelectionChange={(value) => {
						onChange({
							criterion: {value: value as string},
							touched: {attributeValue: true},
							valid: {
								attributeValue: validateAttributeValue(
									value as string,
									dataType
								),
							},
						});
					}}
					placeholder={Liferay.Language.get('true')}
				>
					{({label, value}) => <Option key={value}>{label}</Option>}
				</Picker>
			);
		case DataTypes.Date:
			if (operatorName === FunctionalOperators.Between) {
				return (
					<Form.GroupItem
						className={getCN({
							'has-error': !valid && touched,
						})}
						shrink
					>
						<DateRangeInput
							onBlur={handleAttributeValueBlur}
							onChange={(value) => {
								onChange({
									criterion: {
										value,
									},
									touched: {attributeValue: true},
									valid: {
										attributeValue:
											!!value.end && !!value.start,
									},
								});
							}}
							overlayAlignment="rightCenter"
							value={value as DateRange}
						/>
					</Form.GroupItem>
				);
			}

			return (
				<Form.GroupItem
					className={getCN({
						'has-error': !valid && touched,
					})}
					shrink
				>
					<DateInput
						onDateInputBlur={handleAttributeValueBlur}
						onDateInputChange={(value) => {
							onChange({
								criterion: {
									value,
								},
								touched: {attributeValue: true},
								valid: {
									attributeValue: validateAttributeValue(
										value,
										dataType
									),
								},
							});
						}}
						overlayAlignment="rightCenter"
						showRetentionPeriod={false}
						value={value}
					/>
				</Form.GroupItem>
			);
		case DataTypes.Duration:
			return (
				<Form.GroupItem
					className={getCN({
						'has-error': !valid && touched,
					})}
					shrink
				>
					<DurationInput
						onChange={(value) => {
							onChange({
								criterion: {
									value,
								},
								touched: {attributeValue: true},
								valid: {attributeValue: isValid(value)},
							});
						}}
						value={value as string}
					/>
				</Form.GroupItem>
			);
		case DataTypes.Number:
			return (
				<NumberInput
					onChange={({touched, valid, value}) =>
						onChange({
							criterion: {value},
							touched: {attributeValue: !!touched},
							valid: {attributeValue: !!valid},
						})
					}
					operatorName={
						operatorName as FunctionalOperators &
							import('../../../utils/constants').RelationalOperators
					}
					touched={touched}
					valid={valid}
					value={value as number | string | BetweenNumber}
				/>
			);
		case DataTypes.String:
		default:
			return (
				<Form.GroupItem
					className={getCN({
						'has-error': !valid && touched,
					})}
					shrink
				>
					<Input
						data-testid="attribute-value-string-input"
						onBlur={handleAttributeValueBlur}
						onChange={(
							event: React.ChangeEvent<HTMLInputElement>
						) => {
							const {value} = event.target;

							onChange({
								criterion: {value},
								touched: {attributeValue: true},
								valid: {
									attributeValue: validateAttributeValue(
										value,
										dataType
									),
								},
							});
						}}
						value={value}
					/>
				</Form.GroupItem>
			);
	}
};

export default ValueInput;
