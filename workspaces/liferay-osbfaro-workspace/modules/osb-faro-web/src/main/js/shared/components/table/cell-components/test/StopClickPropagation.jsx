/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import StopClickPropagation from '../StopClickPropagation';

jest.unmock('react-dom');

describe('StopClickPropagation', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<StopClickPropagation />);

		expect(container).toMatchSnapshot();
	});

	it('stops event propagation from reaching any parent elements', () => {
		const clickSpy = jest.fn();

		const {container} = render(
			<button onClick={clickSpy}>
				<StopClickPropagation />
			</button>
		);

		fireEvent.click(container.querySelector('span'));

		expect(clickSpy).not.toBeCalled();
	});
});
