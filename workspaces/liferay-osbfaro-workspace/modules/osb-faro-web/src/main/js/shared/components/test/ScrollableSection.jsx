/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import ScrollableSection from '../ScrollableSection';

jest.unmock('react-dom');

describe('ScrollableSection', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<ScrollableSection>
				<ul>
					<li>test</li>
					<li>test 1</li>
					<li>test 2</li>
				</ul>
			</ScrollableSection>
		);

		expect(container).toMatchSnapshot();
	});
});
