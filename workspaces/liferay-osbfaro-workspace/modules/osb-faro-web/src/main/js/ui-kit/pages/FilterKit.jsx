/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {range} from 'lodash';
import React from 'react';
import Filter from '~/shared/components/filter';

import Row from '../components/Row';

const mockItems = range(1).map((i) => ({
	items: [
		{
			category: 'FOO NAME',
			hasSearch: true,
			inputType: 'radio',
			label: `child foo label${i}`,
			value: `child foo value${i}`,
		},
	],
	label: `foo label${i}`,
	name: 'FOO NAME',
	value: `${i}`,
}));

export default class FilterKit extends React.Component {
	handleApplyFilters(appliedFilters) {
		console.log('applied filters changed!', appliedFilters); // eslint-disable-line no-console
	}

	render() {
		return (
			<div>
				<Row>
					<h3>Filter</h3>

					<div style={{height: '50vh', position: 'relative'}}>
						<Filter
							items={mockItems}
							onChange={this.handleApplyFilters}
						/>
					</div>
				</Row>
			</div>
		);
	}
}
