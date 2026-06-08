/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import mockStore from '~/test/mock-store';

import {TrackedBehaviors} from '../TrackedBehaviors';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		groupId: '23',
	}),
}));

ReactDOM.createPortal = jest.fn();

describe('TrackedBehaviorsList', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<TrackedBehaviors groupId="23" />
				</StaticRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
