/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import Footer from '../Footer';

jest.unmock('react-dom');

describe('Modal Footer', () => {
	it('renders', () => {
		const {container} = render(<Footer />);

		expect(container).toMatchSnapshot();
	});

	it('renders with children', () => {
		const {queryByText} = render(
			<Footer>
				<button type="button">Footer button</button>
			</Footer>
		);

		expect(queryByText('Footer button')).toBeTruthy();
	});
});
