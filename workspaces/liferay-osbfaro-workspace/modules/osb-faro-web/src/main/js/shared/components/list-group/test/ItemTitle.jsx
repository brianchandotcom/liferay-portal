/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import ItemTitle from '../ItemTitle';

jest.unmock('react-dom');

describe('ItemTitle', () => {
	it('renders', () => {
		const {container} = render(<ItemTitle />);

		expect(container).toMatchSnapshot();
	});

	it('renders with children', () => {
		const stringToMatch = 'Item title text';

		const {queryByText} = render(
			<ItemTitle>
				<span>{stringToMatch}</span>
			</ItemTitle>
		);

		expect(queryByText(stringToMatch)).toBeTruthy();
	});
});
