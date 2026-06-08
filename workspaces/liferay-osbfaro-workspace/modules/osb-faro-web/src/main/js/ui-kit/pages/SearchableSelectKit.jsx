/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import {times} from 'lodash';
import React from 'react';
import SearchableSelect from '~/shared/components/SearchableSelect';

import Row from '../components/Row';

const items = times(30, (i) => ({name: `item${i}`, value: i}));

function includes(source, target) {
	return source.toLocaleUpperCase().includes(target.toLocaleUpperCase());
}

class SearchableSelectKit extends React.Component {
	state = {
		inputValue: '',
		selectedItem: undefined,
	};

	@autobind
	handleInput(value) {
		this.setState({
			inputValue: value,
		});
	}

	@autobind
	handleSelect(item) {
		this.setState({
			inputValue: '',
			selectedItem: item,
		});
	}

	render() {
		const {inputValue, selectedItem} = this.state;

		return (
			<div>
				<Row>
					<SearchableSelect
						buttonPlaceholder="Select an Item"
						inputPlaceholder="Search for..."
						inputValue={inputValue}
						items={items.filter(({name}) =>
							includes(name, inputValue)
						)}
						onSearchChange={this.handleInput}
						onSelect={this.handleSelect}
						selectedItem={selectedItem}
					/>
				</Row>

				<Row>
					<SearchableSelect
						buttonPlaceholder="Select an Item"
						disabled
						inputPlaceholder="Search for..."
						items={items}
						selectedItem={selectedItem}
					/>
				</Row>

				<Row>
					<SearchableSelect
						buttonPlaceholder="Select an Item"
						inputPlaceholder="Search for..."
						items={items}
						selectedItem={selectedItem}
						showSearch={false}
					/>
				</Row>

				<Row>
					<SearchableSelect
						buttonPlaceholder="Has sub-headers"
						inputPlaceholder="Search for..."
						items={[
							{
								name: 'Test Subheader',
								subheader: true,
							},
							...items,
						]}
						selectedItem={selectedItem}
						showSearch={false}
					/>
				</Row>
			</div>
		);
	}
}

export default SearchableSelectKit;
