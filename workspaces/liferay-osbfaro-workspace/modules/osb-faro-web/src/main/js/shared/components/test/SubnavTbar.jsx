/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import SubnavTbar from '../SubnavTbar';

jest.unmock('react-dom');

describe('SubnavTbar', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<SubnavTbar />);
		expect(container).toMatchSnapshot();
	});
});

describe('SubnavTbar.Item', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<SubnavTbar.Item />);
		expect(container).toMatchSnapshot();
	});
});

describe('SubnavTbar.Section', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<SubnavTbar.Section />);
		expect(container).toMatchSnapshot();
	});
});
