/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import MetricBar from '../MetricBar';

jest.unmock('react-dom');

describe('MetricBar', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<MetricBar />);
		expect(container).toMatchSnapshot();
	});

	it('renders full MetricBar', () => {
		const {container} = render(<MetricBar percent={1} />);
		expect(container).toMatchSnapshot();
	});

	it('renders small MetricBar', () => {
		const {container} = render(<MetricBar size="sm" />);
		expect(container).toMatchSnapshot();
	});

	it('renders w/ children', () => {
		const {queryByText} = render(
			<MetricBar>
				<div data-testid="child">Test Test</div>
			</MetricBar>
		);
		expect(queryByText('Test Test')).toBeTruthy();
	});
});
