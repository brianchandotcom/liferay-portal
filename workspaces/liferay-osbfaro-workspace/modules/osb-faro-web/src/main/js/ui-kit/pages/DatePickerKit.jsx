import autobind from 'autobind-decorator';
import DatePicker from 'shared/components/date-picker';
import moment from 'moment';
import React from 'react';
import Row from '../components/Row';

class DatePickerKit extends React.Component {
	state = {
		date: moment(),
		range: {end: null, start: null}
	};

	@autobind
	handleSelect(date) {
		this.setState({
			date
		});
	}

	@autobind
	handleSelectRange(range) {
		this.setState({
			range
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
