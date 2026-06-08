/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Conjunction from '../Conjunction';

jest.unmock('react-dom');

describe('Conjunction', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<Conjunction conjunctionName="and" />);

		expect(container).toMatchSnapshot();
	});

	it('renders the button as disabled when the disabled prop is set', () => {
		const {getByRole} = render(
			<Conjunction conjunctionName="and" disabled />
		);

		expect(getByRole('button')).toBeDisabled();
	});

	it('renders the Then label for an AND conjunction in sequential mode', () => {
		const {getByRole} = render(
			<Conjunction conjunctionName="and" sequential />
		);

		expect(getByRole('button')).toHaveTextContent('Then');
	});

	it('renders the And label for an AND conjunction outside sequential mode', () => {
		const {getByRole} = render(<Conjunction conjunctionName="and" />);

		expect(getByRole('button')).toHaveTextContent('And');
	});
});
