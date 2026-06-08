/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import mockStore from '~/test/mock-store';

import HelpWidget from '../HelpWidget';

jest.unmock('react-dom');

describe('HelpWidget', () => {
	it('renders', () => {
		const {getByRole} = render(
			<Provider store={mockStore()}>
				<HelpWidget />
			</Provider>
		);

		expect(getByRole('button', {name: 'Help'})).toBeInTheDocument();
	});

	it('renders a dropdown', () => {
		const {getByText} = render(
			<Provider store={mockStore()}>
				<HelpWidget />
			</Provider>
		);

		expect(getByText('Report an Issue')).toBeTruthy();
	});
});
