/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import autobind from 'autobind-decorator';
import React from 'react';
import Form from '~/shared/components/form';

import {INTEREST_BOOLEAN_OPTIONS} from '../utils/constants';
import {getPropertyValue, setPropertyValue} from '../utils/custom-inputs';
import {ISegmentEditorCustomInputBase} from '../utils/types';

export default class InterestBooleanInput extends React.Component<ISegmentEditorCustomInputBase> {
	@autobind
	handleChange(newValue: React.Key) {
		const {onChange, value} = this.props;

		onChange({
			value: setPropertyValue(value, 'value', 1, newValue),
		});
	}

	render() {
		const {
			property: {entityName},
			value,
		} = this.props;

		return (
			<div className="criteria-statement">
				<Form.Group autoFit>
					<Form.GroupItem className="entity-name" label shrink>
						{entityName}
					</Form.GroupItem>

					<Form.GroupItem shrink>
						<Picker
							className="criterion-input"
							items={INTEREST_BOOLEAN_OPTIONS}
							onSelectionChange={this.handleChange}
							selectedKey={getPropertyValue(value, 'value', 1)}
						>
							{({label, value}) => (
								<Option key={value}>{label}</Option>
							)}
						</Picker>
					</Form.GroupItem>

					<Form.GroupItem className="operator" label shrink>
						{Liferay.Language.get('interested-in').toLowerCase()}
					</Form.GroupItem>

					<Form.GroupItem className="display-value" label shrink>
						{getPropertyValue(value, 'value', 0)}
					</Form.GroupItem>
				</Form.Group>
			</div>
		);
	}
}
