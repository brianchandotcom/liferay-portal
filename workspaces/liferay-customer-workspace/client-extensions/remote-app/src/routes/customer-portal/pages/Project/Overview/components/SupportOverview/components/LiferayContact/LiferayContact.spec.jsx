/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import LiferayContact from './LiferayContact';

describe('LiferayContact', () => {
	const koroneikiAccount = {
		liferayContactEmailAddress: 'janedoe@company.com',
		liferayContactName: 'Jane Doe',
		liferayContactRole: 'Administrator',
	};

	it('displays project support liferay contact name', () => {
		render(<LiferayContact koroneikiAccount={koroneikiAccount} />);
		const linkElementContactName = screen.getByText('Jane Doe');

		expect(linkElementContactName).toBeInTheDocument();
	});

	it('displays project support liferay contact email address', () => {
		render(<LiferayContact koroneikiAccount={koroneikiAccount} />);

		const linkElementContactEmailAddress = screen.getByText(
			/janedoe@company/i,
			{exact: false}
		);

		expect(linkElementContactEmailAddress).toBeInTheDocument();
	});

	it('displays project support liferay contact role', () => {
		render(<LiferayContact koroneikiAccount={koroneikiAccount} />);

		const linkElementContactRole = screen.getByText(/administrator/i);

		expect(linkElementContactRole).toBeInTheDocument();
	});
});
