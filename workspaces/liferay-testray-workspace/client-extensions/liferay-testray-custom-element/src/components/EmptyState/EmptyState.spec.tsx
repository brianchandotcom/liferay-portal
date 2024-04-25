/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';

import EmptyState from '.';

describe('EmptyState', () => {
	it('renders with default properties', () => {
		const {asFragment, container, queryByText} = render(<EmptyState />);

		expect(queryByText('No results found')).toBeTruthy();
		expect(queryByText('Sorry, there are no results found')).toBeTruthy();
		expect(container.querySelector('img')?.src).toBe(
			'http://localhost:3000/states/empty_state.gif'
		);
		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with custom properties', () => {
		const {container, queryByText} = render(
			<EmptyState
				description="No users found"
				title="No users"
				type="BLANK"
			/>
		);

		expect(queryByText('No users')).toBeTruthy();
		expect(queryByText('No users found')).toBeTruthy();
		expect(container.querySelector('img')?.src).toBeUndefined();
	});
});
