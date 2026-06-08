/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import CurrentStatusCell from '../CurrentStatus';

jest.unmock('react-dom');

const tableRow = document.createElement('tr');

describe('CurrentStatusCell', () => {
	it('renders', () => {
		const {container} = render(
			<CurrentStatusCell
				data={{
					currentMember: true,
				}}
			/>,
			{container: document.body.appendChild(tableRow)}
		);

		expect(container).toMatchSnapshot();
	});

	it('renders as a non-member', () => {
		const {container} = render(
			<CurrentStatusCell
				data={{
					currentMember: false,
				}}
			/>,
			{container: document.body.appendChild(tableRow)}
		);

		expect(container).toMatchSnapshot();
	});
});
