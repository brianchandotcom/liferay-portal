/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';

import PaginationBar from '../PaginationBar';

jest.unmock('react-dom');

const DefaultComponent = (props) => (
	<StaticRouter>
		<PaginationBar
			href=""
			page={3}
			selectedDelta={10}
			totalItems={100}
			{...props}
		/>
	</StaticRouter>
);

describe('PaginationBar', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<DefaultComponent />);

		expect(
			container.querySelector('.pagination-bar-root')
		).toBeInTheDocument();
	});

	it('renders with small size', () => {
		const {container} = render(<DefaultComponent size="sm" />);

		expect(container.querySelector('.pagination-sm')).toBeTruthy();
	});

	it('renders with large size', () => {
		const {container} = render(<DefaultComponent size="lg" />);

		expect(container.querySelector('.pagination-lg')).toBeTruthy();
	});

	it('renders different deltas', () => {
		const {container} = render(
			<DefaultComponent
				deltas={[1, 2, 3, 4]}
				page={1}
				selectedDelta={1}
				totalItems={10}
			/>
		);

		expect(container.querySelector('.pagination-bar-root')).toBeTruthy();
		expect(
			container.querySelector('.pagination-items-per-page')
		).toBeTruthy();
	});
});
