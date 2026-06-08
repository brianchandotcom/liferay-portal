/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import moment from 'moment';
import React from 'react';
import DateInput from '~/shared/components/DateInput';
import Form from '~/shared/components/form';

import {INPUT_DATE_FORMAT, PropertyTypes} from '../utils/constants';
import {ISegmentEditorInputBase} from '../utils/types';

interface IEditorDateInputProps extends ISegmentEditorInputBase {
	displayFormat: string;
	value: string;
}

export default class EditorDateInput extends React.Component<IEditorDateInputProps> {
	@autobind
	handleDateChange(value: string) {
		this.props.onChange({
			type: PropertyTypes.Date,
			value,
		});
	}

	render() {
		const {
			className,
			displayFormat,
			displayValue,
			operatorRenderer: OperatorDropdown,
			property: {entityName},
			value,
		} = this.props;

		const date = moment(value, INPUT_DATE_FORMAT).format(INPUT_DATE_FORMAT);

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
						<DateInput
							className={className}
							displayFormat={displayFormat}
							onDateInputChange={this.handleDateChange}
							showRetentionPeriod={false}
							value={date}
						/>
					</Form.GroupItem>
				</Form.Group>
			</div>
		);
	}
}
