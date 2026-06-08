/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import React from 'react';
import Input from '~/shared/components/Input';
import InputList from '~/shared/components/InputList';

import Row from '../components/Row';

class InputKit extends React.Component {
	state = {
		inputValue: '',
		items: ['one', 'two'],
	};

	@autobind
	handleInputChange(value) {
		this.setState({
			inputValue: value,
		});
	}

	@autobind
	handleItemsChange(items) {
		this.setState({
			items,
		});
	}

	validateItem(item) {
		return item.length === 3;
	}

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<Input.Group>
						<InputList
							errorMessage="Items must be three letters long."
							inputValue={this.state.inputValue}
							items={this.state.items}
							onInputChange={this.handleInputChange}
							onItemsChange={this.handleItemsChange}
							placeholder="Add Item"
							validationFn={this.validateItem}
						/>
					</Input.Group>
				</Row>
			</div>
		);
	}
}

export default InputKit;
