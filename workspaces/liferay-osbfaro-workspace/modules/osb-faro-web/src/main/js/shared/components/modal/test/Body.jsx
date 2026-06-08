/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import Body from '../Body';

jest.unmock('react-dom');

describe('Modal Body', () => {
	it('renders', () => {
		const {container} = render(<Body />);

		expect(container).toMatchSnapshot();
	});

	it('renders with scroller', () => {
		const {container} = render(<Body inlineScroller />);

		expect(container.querySelector('.inline-scroller')).toBeTruthy();
	});

	it('renders with children', () => {
		const {queryByText} = render(
			<Body>
				<span>Body content</span>
			</Body>
		);

		expect(queryByText('Body content')).toBeTruthy();
	});
});
