/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import {TimeSpans} from '../../../utils/constants';
import TimePeriodInput from '../TimePeriodInput';

jest.unmock('react-dom');

describe('TimePeriodInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container, getAllByText, getByText} = render(
			<TimePeriodInput onChange={jest.fn()} value={TimeSpans.Last7Days} />
		);
		fireEvent.click(getByText('Last 7 days'));

		expect(getByText('Last 24 hours')).toBeTruthy();
		expect(getByText('Yesterday')).toBeTruthy();
		expect(getAllByText('Last 7 days')[1]).toBeTruthy();
		expect(getByText('Last 28 days')).toBeTruthy();
		expect(getByText('Last 30 days')).toBeTruthy();
		expect(getByText('Last 90 days')).toBeTruthy();

		expect(container).toMatchSnapshot();
	});
});
