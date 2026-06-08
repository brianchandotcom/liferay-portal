/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import Trend from '../Trend';

jest.unmock('react-dom');

describe('Trend', () => {
	it('renders', () => {
		const {container, getByText} = render(
			<Trend color="red" label="Trend component" />
		);

		expect(container.querySelector('.analytics-trend')).toHaveStyle(
			'color: red'
		);
		expect(getByText('Trend component')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});

	it('renders w/ icon', () => {
		const {container} = render(
			<Trend color="red" icon="home" label="Trend component" />
		);

		expect(container.querySelector('.lexicon-icon-home')).toBeTruthy();
	});
});
