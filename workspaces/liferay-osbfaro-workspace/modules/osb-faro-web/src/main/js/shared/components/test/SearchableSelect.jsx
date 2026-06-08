/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import {times} from 'lodash';
import React from 'react';

import SearchableSelect from '../SearchableSelect';

jest.unmock('react-dom');

describe('SearchableSelect', () => {
	afterEach(cleanup);

	const items = times(5, (i) => ({name: `item${i}`, value: i}));

	it('renders', () => {
		const {getByText} = render(
			<SearchableSelect
				buttonPlaceholder="Bar"
				inputPlaceholder="Foo"
				items={items}
			/>
		);

		fireEvent.click(getByText(/Bar/));

		expect(document.body).toMatchSnapshot();
	});

	it('renders with a subheader', () => {
		const {getByText} = render(
			<SearchableSelect
				buttonPlaceholder="Bar"
				inputPlaceholder="Foo"
				items={[{name: 'foo bar', subheader: true}, ...items]}
			/>
		);

		fireEvent.click(getByText(/Bar/));

		expect(getByText('foo bar')).toBeTruthy();
	});
});
