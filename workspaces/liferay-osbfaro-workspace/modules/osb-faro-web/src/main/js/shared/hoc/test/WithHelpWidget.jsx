/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';
import {Provider} from 'react-redux';
import * as data from '~/test/data';
import mockStore from '~/test/mock-store';

import withHelpWidget from '../WithHelpWidget';

jest.unmock('react-dom');

const faroSubscription = fromJS(data.mockSubscription());
const wrappedComponentText = () => 'wrapped component text';

describe('withHelpWidget', () => {
	it('renders a wrapped component', () => {
		const WrappedComponent = withHelpWidget(wrappedComponentText);

		const {container} = render(
			<Provider store={mockStore()}>
				<WrappedComponent
					faroSubscriptionIMap={faroSubscription}
					groupId="123"
				/>
			</Provider>
		);

		expect(container.textContent).toBe('wrapped component text');
	});

	it('renders a HelpWidget Component', () => {
		const WrappedComponent = withHelpWidget(wrappedComponentText);

		const {container} = render(
			<Provider store={mockStore()}>
				<WrappedComponent
					faroSubscriptionIMap={faroSubscription}
					groupId="123"
				/>
			</Provider>
		);

		expect(container.querySelector('.help-widget-root')).toBeTruthy();
	});
});
