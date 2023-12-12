/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';

import StatusBadge from './StatusBadge';

describe('StatusBadge', () => {
	it('renders StatusBadge with success', () => {
		const {asFragment, queryByText} = render(
			<StatusBadge type="blocked">Blocked</StatusBadge>
		);

		expect(asFragment()).toMatchSnapshot();
		expect(queryByText('Blocked')).toBeTruthy();
	});
});
