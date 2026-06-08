/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import React from 'react';
import SelectInput from '~/shared/components/SelectInput';

const Item = ({name}) => <span>{name}</span>;

class SelectInputKit extends React.Component {
	state = {
		selectedItem: null,
	};

	getItems() {
		return Promise.resolve([
			{
				id: 1,
				name: 'test1',
			},
			{
				id: 2,
				name: 'test2',
			},
			{
				id: 3,
				name: 'test3',
			},
		]);
	}

	@autobind
	handleSelect(item) {
		this.setState({
			selectedItem: item,
		});
	}

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<SelectInput
					dataSourceFn={this.getItems}
					itemRenderer={Item}
					menuTitle="Header Title"
					onSelect={this.handleSelect}
					placeholder="Search..."
					selectedItem={this.state.selectedItem}
				/>
			</div>
		);
	}
}

export default SelectInputKit;
