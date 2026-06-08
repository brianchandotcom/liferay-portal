/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import TimeSelector from '../TimeSelector';

jest.unmock('react-dom');

describe('TimeSelector', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<TimeSelector timeZoneId="UTC" value="2020-08-30T21:00:00Z" />
		);

		expect(container).toMatchSnapshot();
	});
});
