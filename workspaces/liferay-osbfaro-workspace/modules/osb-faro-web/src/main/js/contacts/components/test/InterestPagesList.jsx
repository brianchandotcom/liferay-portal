/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, waitFor} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';

import InterestPagesList from '../InterestPagesList';

jest.unmock('react-dom');

/**
 * For ActivePagesList (active: true) validation, It's possible to order by
 * unique visits count, whereas for InactivePagesList (active: false)
 * it's not, that's why we're validating by the presence
 * of the order button on those tests.
 */
describe('InterestPagesList', () => {
	it('renders', async () => {
		const {container} = render(
			<StaticRouter>
				<InterestPagesList dataSourceParams={{active: true}} />
			</StaticRouter>
		);

		await waitFor(() => {});

		expect(
			container.querySelector('.searchable-table-root')
		).toBeInTheDocument();
	});
});
