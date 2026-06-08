/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import autobind from 'autobind-decorator';
import getCN from 'classnames';
import {isNull} from 'lodash';
import React from 'react';
import * as API from '~/shared/api';
import AutocompleteInput from '~/shared/components/AutocompleteInput';
import Form from '~/shared/components/form';

import {ISegmentEditorInputBase} from '../utils/types';
import {isValid} from '../utils/utils';

interface IStringInputProps extends ISegmentEditorInputBase {
	touched: boolean;
	valid: boolean;
	value: string;
}

export default class StringInput extends React.Component<IStringInputProps> {
	@autobind
	fieldValuesDataSourceFn() {
		const {
			channelId,
			groupId,
			property: {id},
			value,
		} = this.props;

		return API.individuals
			.fetchFieldValues({
				channelId,
				fieldMappingFieldName: id,
				groupId,
				query: value,
			})
			.then(({items}) => items);
	}

	@autobind
	handleBlur() {
		const {onChange, value} = this.props;

		onChange({touched: true, valid: isValid(value)});
	}

	@autobind
	handleChange(value: string | React.Key) {
		this.props.onChange({valid: isValid(value), value: String(value)});
	}

	render() {
		const {
			className,
			displayValue,
			operatorRenderer: OperatorDropdown,
			property: {entityName, options = []},
			touched,
			valid,
			value,
		} = this.props;

		const showError = !valid && touched;

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

					{!isNull(value) && (
						<Form.GroupItem>
							{!options.length ? (
								<AutocompleteInput
									className={getCN(className, {
										'has-error': showError,
									})}
									data-testid="value-input"
									dataSourceFn={this.fieldValuesDataSourceFn}
									onBlur={this.handleBlur}
									onChange={this.handleChange}
									value={value}
								/>
							) : (
								<Picker
									className={getCN({
										'has-error': showError,
									})}
									data-testid="value-select"
									items={
										options.map(({label, value}) => ({
											label,
											value,
										})) as {label: string; value: string}[]
									}
									onBlur={this.handleBlur}
									onSelectionChange={this.handleChange}
									selectedKey={value}
								>
									{({label, value}) => (
										<Option key={value}>{label}</Option>
									)}
								</Picker>
							)}
						</Form.GroupItem>
					)}
				</Form.Group>
			</div>
		);
	}
}
