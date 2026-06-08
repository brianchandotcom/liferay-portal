/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import RelativeMetricBar from '../RelativeMetricBar';

jest.unmock('react-dom');

describe('RelativeMetricBar', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<RelativeMetricBar
				data={{
					count: 6,
					name: 'Test Test',
				}}
				maxCount={10}
				showTitle={false}
				totalCount={12}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders w/ name', () => {
		const {getByText} = render(
			<RelativeMetricBar
				data={{
					count: 6,
					name: 'Test Test',
				}}
				maxCount={10}
				showName
				totalCount={12}
			/>
		);

		expect(getByText('Test Test')).toBeTruthy();
	});

	it('renders w/ data-tooltip attr', () => {
		const {container} = render(
			<RelativeMetricBar
				data={{
					count: 6,
					name: 'Test Test',
				}}
				maxCount={10}
				showName
				totalCount={12}
			/>
		);

		expect(container.querySelector('[data-tooltip]')).toBeTruthy();
	});
});
