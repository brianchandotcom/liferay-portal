/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import AccountNames from '../AccountNames';

jest.unmock('react-dom');

const tableRow = document.createElement('tr');

describe('AccountNames', () => {
	it('renders', () => {
		const {container} = render(
			<AccountNames
				data={{
					accountName: 'foo',
				}}
			/>,
			{container: document.body.appendChild(tableRow)}
		);

		expect(container).toMatchSnapshot();
	});

	it('renders a fallback display', () => {
		const {getByText} = render(
			<AccountNames
				data={{
					accountName: null,
				}}
			/>,
			{container: document.body.appendChild(tableRow)}
		);

		expect(getByText('-')).toBeTruthy();
	});
});
