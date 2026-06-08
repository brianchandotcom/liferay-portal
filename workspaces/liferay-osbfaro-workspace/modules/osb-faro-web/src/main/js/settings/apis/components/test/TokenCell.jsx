/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, getByText, render} from '@testing-library/react';
import React from 'react';
import {getISODate} from '~/shared/util/date';
import * as data from '~/test/data';
import {mockGetDateNow} from '~/test/mock-date';

import TokenCell from '../TokenCell';

jest.unmock('react-dom');

describe('TokenCell', () => {
	beforeAll(() => {
		mockGetDateNow(getISODate(data.getTimestamp(0)));
	});

	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<TokenCell data={data.mockApiToken()} />);

		expect(container).toMatchSnapshot();
	});

	it('renders with an expired label if the Token is expired', () => {
		const {container} = render(
			<TokenCell
				data={data.mockApiToken({
					expirationDate: getISODate(data.getTimestamp(-1)),
				})}
			/>
		);

		expect(
			getByText(container.querySelector('.label-root'), 'Expired')
		).toBeTruthy();

		jest.restoreAllMocks();
	});
});
