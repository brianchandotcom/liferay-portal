/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';
import React from 'react';

import ErrorDisplay from '../ErrorDisplay';

jest.unmock('react-dom');

describe('ErrorDisplay', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<ErrorDisplay />);
		expect(container).toMatchSnapshot();
	});

	it('renders with button', () => {
		const {queryByText} = render(<ErrorDisplay onReload={noop} />);
		expect(queryByText('Reload')).toBeTruthy();
	});
});
