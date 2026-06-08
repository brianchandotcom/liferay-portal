/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import {PropTypes} from 'prop-types';
import React from 'react';
import AutocompleteInput from '~/shared/components/AutocompleteInput';
import sendRequest from '~/shared/util/request';

class AutocompleteInputKit extends React.Component {
	static propTypes = {
		groupId: PropTypes.string.isRequired,
	};

	state = {
		value: '',
	};

	@autobind
	getItems() {
		const {
			props: {groupId},
			state: {value},
		} = this;

		return sendRequest({
			data: {
				delta: 5,
				query: value,
			},
			method: 'GET',
			path: `contacts/${groupId}/field_mapping/suggestions`,
		}).then(({items}) => items.map(({name}) => name));
	}

	@autobind
	handleChange(value) {
		this.setState({
			value,
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
					placeholder="Search..."
					value={this.state.value}
				/>
			</div>
		);
	}
}

export default AutocompleteInputKit;
