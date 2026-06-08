/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import React from 'react';
import DateInput from '~/shared/components/DateInput';

import Row from '../components/Row';

export default class DateInputKit extends React.Component {
	state = {
		value: '',
	};

	@autobind
	handleChange(value) {
		this.setState({
			value,
		});
	}

	render() {
		const {value} = this.state;

		return (
			<div>
				<Row>
					<DateInput
						format="YYYY-MM-DD LT"
						onDateInputChange={this.handleChange}
						placeholder="Pick a date..."
						showTimeSelector
						value={value}
					/>
				</Row>

				<div>{`Selected Date: ${value}`}</div>
			</div>
		);
	}
}
