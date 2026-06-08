/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import IntervalSelector from '../IntervalSelector';

jest.unmock('react-dom');

describe('IntervalSelector', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<IntervalSelector onChange={jest.fn()} />);

		expect(container).toMatchSnapshot();
	});

	it('renders with an active item', () => {
		const {getByText} = render(
			<IntervalSelector activeInterval="W" onChange={jest.fn()} />
		);

		expect(getByText('W')).not.toHaveClass('.active');
	});

	it('calls the onChange callback when an interval is clicked', () => {
		const spy = jest.fn();
		const {getByText} = render(<IntervalSelector onChange={spy} />);

		fireEvent.click(getByText('M'));

		expect(spy).toHaveBeenCalled();
	});

	it('renders as disabled', () => {
		const {container} = render(
			<IntervalSelector disabled onChange={jest.fn()} />
		);

		container.querySelectorAll('button').forEach((element) => {
			expect(element).toBeDisabled();
		});
	});
});
