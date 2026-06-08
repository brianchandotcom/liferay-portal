/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import IntervalSelector from '~/shared/components/IntervalSelector';

import withInterval from '../WithInterval';

jest.unmock('react-dom');

describe('WithInterval', () => {
	afterEach(cleanup);

	it('renders the original component', () => {
		const WrappedComponent = withInterval(() => 'wrapped component text');

		const {container} = render(<WrappedComponent />);

		expect(container.textContent).toBe('wrapped component text');
	});

	it('passes interval as a prop to the wrapped component', () => {
		const WrappedComponent = withInterval(IntervalSelector);

		const {getByText} = render(<WrappedComponent activeInterval="W" />);

		expect(getByText('W').classList.contains('active')).toBe(true);
	});
});
