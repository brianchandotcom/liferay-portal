/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import {Set} from 'immutable';
import React from 'react';
import ListView from '~/shared/components/ListView';
import ListGroup from '~/shared/components/list-group';

import Row from '../components/Row';

const ITEMS = [
	{
		id: 'Portland',
	},
	{
		id: 'San Diego',
	},
	{
		id: 'Seattle',
	},
];

class ItemComponent extends React.Component {
	render() {
		return (
			<ListGroup.ItemField
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				{this.props.item.id}
			</ListGroup.ItemField>
		);
	}
}

class ListViewKit extends React.Component {
	state = {
		selectedItemsISet: new Set(),
	};

	@autobind
	handleSelectItemsChange(newVal) {
		this.setState({
			selectedItemsISet: newVal,
		});
	}

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<ListView
						className="w-100"
						itemRenderer={ItemComponent}
						items={ITEMS}
						onClick={this.handleClick}
					/>
				</Row>

				<Row>
					<ListView
						className="w-100"
						itemRenderer={ItemComponent}
						items={ITEMS}
						onSelectItemsChange={this.handleSelectItemsChange}
						selectedItemsISet={this.state.selectedItemsISet}
					>
						<ListGroup.Item flex header>
							<ListGroup.ItemField expand>
								These are selectable
							</ListGroup.ItemField>
						</ListGroup.Item>
					</ListView>
				</Row>
			</div>
		);
	}
}

export default ListViewKit;
