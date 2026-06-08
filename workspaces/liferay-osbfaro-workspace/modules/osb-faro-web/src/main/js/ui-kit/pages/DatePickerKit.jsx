/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import moment from 'moment';
import React from 'react';
import DatePicker from '~/shared/components/date-picker';

import Row from '../components/Row';

class DatePickerKit extends React.Component {
	state = {
		date: moment(),
		range: {end: null, start: null},
	};

	@autobind
	handleSelect(date) {
		this.setState({
			date,
		});
	}

	@autobind
	handleSelectRange(range) {
		this.setState({
			range,
		});
	}

	render() {
		const {date, range} = this.state;

		return (
			<div>
				<Row>
					<DatePicker
						date={range}
						maxRange={365}
						onSelect={this.handleSelectRange}
					/>
				</Row>

				<Row>
					<DatePicker date={date} onSelect={this.handleSelect} />
				</Row>

				<Row>
					<DatePicker
						date={date}
						maxDate={moment().add(2, 'days')}
						minDate={moment()}
						onSelect={this.handleSelect}
					/>
				</Row>

				<Row>
					<DatePicker disabled />
				</Row>
			</div>
		);
	}
}

export default DatePickerKit;
