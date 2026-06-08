/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import moment from 'moment';
import React from 'react';
import {getTimestamp} from '~/test/data';

import DateRenderer from '../DateRenderer';

jest.unmock('react-dom');

describe('DateRenderer', () => {
	it('renders', () => {
		const {container} = render(
			<DateRenderer
				data={{
					dateCreated: getTimestamp(),
				}}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders with date provided in the datePath String', () => {
		const {container} = render(
			<DateRenderer
				data={{
					dateCreated: 0,
					dateUpdated: getTimestamp(),
				}}
				datePath="dateUpdated"
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders with date provided in the datePath Array', () => {
		const {container} = render(
			<DateRenderer
				data={{
					properties: {
						dateAdded: getTimestamp(),
						dateCreated: 0,
					},
				}}
				datePath={['properties', 'dateAdded']}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('uses a custom date formatter', () => {
		const {container} = render(
			<DateRenderer
				data={{
					dateCreated: getTimestamp(),
				}}
				dateFormatter={(date) => moment(date).format('YYYY MMMM DD')}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
