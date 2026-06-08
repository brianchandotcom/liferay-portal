/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import autobind from 'autobind-decorator';
import React from 'react';
import Form from '~/shared/components/form';
import {Property} from '~/shared/util/records';

import {BOOLEAN_OPTIONS} from '../utils/constants';

interface IBooleanInputProps {
	displayValue: string;
	id?: string;
	onChange: (params: {value: React.Key}) => void;
	operatorRenderer: React.ElementType;
	property: Property;
	value: string;
}
export default class BooleanInput extends React.Component<IBooleanInputProps> {
	@autobind
	handleChange(value: React.Key) {
		this.props.onChange({value});
	}

	render() {
		const {
			displayValue,
			operatorRenderer: OperatorDropdown,
			property: {entityName},
			value,
		} = this.props;

		return (
			<div className="criteria-statement">
				<Form.Group autoFit>
					<Form.GroupItem className="entity-name" label shrink>
						{entityName}
					</Form.GroupItem>

					<Form.GroupItem className="display-value" label shrink>
						{displayValue}
					</Form.GroupItem>

					<OperatorDropdown />

					<Form.GroupItem shrink>
						<Picker
							className="criterion-input"
							items={BOOLEAN_OPTIONS}
							onSelectionChange={this.handleChange}
							selectedKey={value}
						>
							{({label, value}) => (
								<Option key={value}>{label}</Option>
							)}
						</Picker>
					</Form.GroupItem>
				</Form.Group>
			</div>
		);
	}
}
