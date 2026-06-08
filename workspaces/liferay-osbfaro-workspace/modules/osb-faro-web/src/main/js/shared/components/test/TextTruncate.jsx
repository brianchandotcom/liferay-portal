/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {range} from 'lodash';
import React from 'react';

import TextTruncate from '../TextTruncate';

jest.unmock('react-dom');

describe('TextTruncate', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<TextTruncate title="foo" />);

		expect(container).toMatchSnapshot();
	});

	it('renders with the title pre-truncated if maxCharLength is given', () => {
		const {getByText} = render(
			<TextTruncate
				maxCharLength={10}
				title={range(20)
					.map(() => 'a')
					.join('')}
			/>
		);

		expect(
			getByText(
				`${range(7)
					.map(() => 'a')
					.join('')}...`
			)
		).toBeTruthy();
	});
});
