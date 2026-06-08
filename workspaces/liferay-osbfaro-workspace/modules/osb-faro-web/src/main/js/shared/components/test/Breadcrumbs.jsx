/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';

import Breadcrumbs from '../Breadcrumbs';

const ITEMS = ['one', 'two', 'three', 'four', 'five', 'six', 'seven'];

jest.unmock('react-dom');

const defaultProps = {
	items: ITEMS.map((item, i) => ({
		active: i === 0,
		href: `#${item}`,
		label: item,
	})),
};

const DefaultComponent = (props) => (
	<StaticRouter>
		<Breadcrumbs {...defaultProps} {...props} />
	</StaticRouter>
);

describe('Breadcrumbs', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {getByText} = render(<DefaultComponent />);

		expect(getByText('one')).toBeInTheDocument();
	});

	it('renders with two items visible', () => {
		const {container} = render(<DefaultComponent bufferSize={2} />);

		expect(
			container.querySelectorAll('.breadcrumb-item:not(.dropdown-root)')
				.length
		).toBe(2);
	});

	it('renders with no dropdown', () => {
		const {container} = render(<DefaultComponent bufferSize={0} />);

		expect(container.querySelector('.dropdown-root')).toBeNull();
	});

	it('renders using onClick rather than hrefs', () => {
		const {container} = render(
			<DefaultComponent
				items={ITEMS.map((item, i) => ({
					active: i === 0,
					label: item,
				}))}
				onClick={jest.fn()}
			/>
		);

		expect(container.querySelector('a')).toBeNull();
	});

	it('renders the active breadcrumb as a span element', () => {
		const {container} = render(
			<DefaultComponent
				items={ITEMS.map((item, i) => ({
					active: i === ITEMS.length - 1,
					label: item,
				}))}
				onClick={jest.fn()}
			/>
		);

		expect(
			container.querySelector('.breadcrumb-item.active span')
		).toHaveTextContent('seven');
	});
});
