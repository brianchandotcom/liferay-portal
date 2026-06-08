/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import {noop} from 'lodash';
import React from 'react';
import {BrowserRouter} from 'react-router-dom';

import ReadyToGo from '../ReadyToGo';

jest.unmock('react-dom');

describe('ReadyToGo', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<BrowserRouter>
				<ReadyToGo groupId="123" onClose={noop} />
			</BrowserRouter>
		);

		expect(container).toMatchSnapshot();
	});

	it('calls onClose when "Done" is clicked', () => {
		const spy = jest.fn();

		const {queryByText} = render(
			<BrowserRouter>
				<ReadyToGo groupId="123" onClose={spy} />
			</BrowserRouter>
		);

		expect(spy).not.toBeCalled();

		fireEvent.click(queryByText('Done'));

		expect(spy).toBeCalled();
	});
});
