/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import SubscriptionStatusDropdown from '.';

describe('Subscription Status Dropdown', () => {
	it('Change the Status with the user click in DropDown', async () => {
		const user = userEvent.setup();
		render(<SubscriptionStatusDropdown />);

		const statusDropdown = screen.getByRole('button');

		expect(statusDropdown).toBeInTheDocument();
		await user.click(statusDropdown);

		const activeStatus = screen.getByRole('menuitem', {name: 'Active'});
		expect(activeStatus).toBeInTheDocument();
		await user.click(statusDropdown);

		const expiredStatus = screen.getByRole('menuitem', {name: 'Expired'});
		expect(expiredStatus).toBeInTheDocument();
		await user.click(statusDropdown);

		const futureStatus = screen.getByRole('menuitem', {name: 'Future'});
		expect(futureStatus).toBeInTheDocument();
	});
});
