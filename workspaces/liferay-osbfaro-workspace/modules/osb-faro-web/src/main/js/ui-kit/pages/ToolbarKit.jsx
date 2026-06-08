/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map, Set} from 'immutable';
import {noop} from 'lodash';
import React from 'react';
import Toolbar from '~/shared/components/toolbar';
import FaroConstants from '~/shared/util/constants';

import Row from '../components/Row';

const DefaultToolbar = (props = {}) => (
	<Toolbar
		loading={false}
		onOrderByFieldChange={noop}
		onOrderClick={noop}
		onSearchSubmit={noop}
		onSelectAll={noop}
		onSelectEntirePage={noop}
		order={FaroConstants.orderAscending}
		orderBy=""
		selectedMessage=""
		{...props}
	/>
);

const DefaultRow = (props = {}) => (
	<Row flex={false} {...props}>
		{props.children}
	</Row>
);

export default class ToolbarKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<DefaultRow>
					<h3>Default</h3>

					<DefaultToolbar />
				</DefaultRow>

				<DefaultRow>
					<h3>Disabled</h3>

					<div>
						<DefaultToolbar disabled />
					</div>

					<div>
						<DefaultToolbar disableSearch />
					</div>
				</DefaultRow>

				<DefaultRow>
					<h3>With Ordering</h3>

					<div>
						<DefaultToolbar
							orderByOptions={[{label: 'Foo', value: 'foo'}]}
						/>
					</div>
				</DefaultRow>

				<DefaultRow>
					<h3>Search values</h3>

					<div>
						<DefaultToolbar placeholder="Placeholder" />
					</div>

					<div>
						<DefaultToolbar
							placeholder="Placeholder"
							query="cat pictures"
							searchValue="dog pictures"
							total={42}
						/>
					</div>
				</DefaultRow>

				<DefaultRow>
					<h3>Item selection</h3>

					<div>
						<DefaultToolbar selectEntirePage />
					</div>

					<div>
						<DefaultToolbar selectEntirePageIndeterminate />
					</div>

					<div>
						<DefaultToolbar
							placeholder="Placeholder"
							query="cat pictures"
							searchValue="dog pictures"
							selectEntirePageIndeterminate
							total={42}
						/>
					</div>
				</DefaultRow>

				<DefaultRow>
					<h3>No checkbox</h3>

					<div>
						<DefaultToolbar showCheckbox={false} />
					</div>
				</DefaultRow>

				<DefaultRow>
					<h3>With Filter Tags</h3>

					<DefaultToolbar
						filterBy={new Map({fooField: new Set(['fooValue'])})}
						filterByOptions={[
							{
								key: 'fooField',
								values: [
									{label: 'fooLabel', value: 'fooValue'},
								],
							},
						]}
					/>
				</DefaultRow>
			</div>
		);
	}
}
