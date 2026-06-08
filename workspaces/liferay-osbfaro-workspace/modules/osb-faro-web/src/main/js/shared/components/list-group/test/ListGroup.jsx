/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import ListGroup from '../index';

jest.unmock('react-dom');

describe('ListGroup', () => {
	it('renders', () => {
		const {container} = render(<ListGroup />);

		expect(container).toMatchSnapshot();
	});

	it('renders with children', () => {
		const stringToMatch = 'List Item';

		const {queryByText} = render(
			<ListGroup>
				<li>{stringToMatch}</li>
			</ListGroup>
		);

		expect(queryByText(stringToMatch)).toBeTruthy();
	});
});
