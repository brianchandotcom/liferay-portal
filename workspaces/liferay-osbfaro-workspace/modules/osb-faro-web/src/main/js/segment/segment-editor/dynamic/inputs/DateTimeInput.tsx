/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import React from 'react';
import DateInput from '~/shared/components/DateInput';
import Form from '~/shared/components/form';
import {formatDateToTimeZone} from '~/shared/util/date';

import {
	INPUT_DATE_TIME_FORMAT,
	INPUT_DISPLAY_DATE_TIME_FORMAT,
	PropertyTypes,
} from '../utils/constants';
import {ISegmentEditorInputBase} from '../utils/types';

interface IDateTimeInputProps extends ISegmentEditorInputBase {
	value: string;
}

export default class DateTimeInput extends React.Component<IDateTimeInputProps> {
	@autobind
	handleDateChange(value: string) {
		this.props.onChange({
			type: PropertyTypes.Date,
			value: formatDateToTimeZone(value, undefined, 'UTC'),
		});
	}

	render() {
		const {
			className,
			displayValue,
			operatorRenderer: OperatorDropdown,
			property: {entityName},
			timeZoneId,
			value,
		} = this.props;

		const date = formatDateToTimeZone(
			value,
			INPUT_DATE_TIME_FORMAT,
			timeZoneId
		);

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

					<Form.GroupItem>
						<DateInput
							className={className}
							displayFormat={INPUT_DISPLAY_DATE_TIME_FORMAT}
							format={INPUT_DATE_TIME_FORMAT}
							onDateInputChange={this.handleDateChange}
							showTimeSelector
							timeZoneId={timeZoneId}
							value={date}
						/>
					</Form.GroupItem>
				</Form.Group>
			</div>
		);
	}
}
