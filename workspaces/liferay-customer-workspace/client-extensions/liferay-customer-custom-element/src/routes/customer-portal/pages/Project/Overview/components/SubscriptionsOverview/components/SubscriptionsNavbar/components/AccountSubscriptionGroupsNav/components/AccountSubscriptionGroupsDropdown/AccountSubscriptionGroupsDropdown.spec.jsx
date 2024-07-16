/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {vi} from 'vitest';
import AccountSubscriptionGroupsDropdown from '.';

const accountSubscriptionGroups = [
	{
		name: 'account test',
	},
	{
		name: 'account test 2',
	},
];

describe('Account Subscription Dropdown', () => {
	const functionMock = vi.fn();

	it('Change Subscription With the user click in Dropdown', async () => {
		const user = userEvent.setup();

		render(
			<AccountSubscriptionGroupsDropdown
				accountSubscriptionGroups={accountSubscriptionGroups}
				onSelect={functionMock}
				selectedIndex={0}
			/>
		);

		const accountSubscriptionGroupsDropdown = screen.getByTestId(
			'subscriptionDropDown'
		);

		expect(accountSubscriptionGroupsDropdown).toBeInTheDocument();

		await user.click(accountSubscriptionGroupsDropdown);

		const accountSubscriptionGroupsFirstItem = screen.getByRole(
			'menuitem',
			{
				name: 'account test',
			}
		);

		expect(accountSubscriptionGroupsFirstItem).toBeInTheDocument();

		await user.click(accountSubscriptionGroupsDropdown);

		const accountSubscriptionGroupsSecondItem = screen.getByRole(
			'menuitem',
			{name: 'account test 2'}
		);

		expect(accountSubscriptionGroupsSecondItem).toBeInTheDocument();
	});
});
