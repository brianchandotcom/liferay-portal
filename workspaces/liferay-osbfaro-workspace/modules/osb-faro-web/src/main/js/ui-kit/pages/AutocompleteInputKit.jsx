import autobind from 'autobind-decorator';
import AutocompleteInput from 'shared/components/AutocompleteInput';
import React from 'react';
import sendRequest from 'shared/util/request';
import {PropTypes} from 'prop-types';

class AutocompleteInputKit extends React.Component {
	static propTypes = {
		groupId: PropTypes.string.isRequired
	};

	state = {
		value: ''
	};

	@autobind
	getItems() {
		const {
			props: {groupId},
			state: {value}
		} = this;

		return sendRequest({
			data: {
				delta: 5,
				query: value
			},
			method: 'GET',
			path: `contacts/${groupId}/field_mapping/suggestions`
		}).then(({items}) => items.map(({name}) => name));
	}

	@autobind
	handleChange(value) {
		this.setState({
			value
		});
	}

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<AutocompleteInput
					dataSourceFn={this.getItems}
					onChange={this.handleChange}
					placeholder='Search...'
					value={this.state.value}
				/>
			</div>
		);
	}
}

export default AutocompleteInputKit;
