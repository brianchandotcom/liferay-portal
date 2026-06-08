/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter} from 'react-router-dom';
import mockStore from '~/test/mock-store';
import * as pedantic from '~/test/pedantic';

import TimeZoneSelectionModal from '../TimeZoneSelectionModal';

jest.unmock('react-dom');

jest.mock('~/shared/util/date', () => ({
	...jest.requireActual('~/shared/util/date'),
	getDateNow: jest.fn(() => require('moment').utc('2019-01-01T12:10:00Z')),
}));

const mockGroupId = '23';

const DefaultComponent = (props) => (
	<Provider
		store={mockStore(
			fromJS({
				projects: {
					[mockGroupId]: {
						data: {
							timeZone: {
								timeZoneId: 'UTC',
							},
						},
					},
				},
			})
		)}
	>
		<MemoryRouter>
			<MockedProvider
				cache={
					new InMemoryCache({
						addTypename: false,
					})
				}
				mocks={[]}
			>
				<TimeZoneSelectionModal
					groupId={mockGroupId}
					notificationId="123"
					onClose={jest.fn()}
					{...props}
				/>
			</MockedProvider>
		</MemoryRouter>
	</Provider>
);

describe('TimeZoneSelectionModal', () => {
	beforeEach(() => {
		pedantic.disable();
	});

	afterEach(() => {
		pedantic.enable();
		cleanup();
	});

	it('renders', () => {
		const {getByText} = render(<DefaultComponent />);

		expect(getByText('Workspace Timezone')).toBeInTheDocument();
		expect(
			getByText(/Your workspace now supports custom timezones/)
		).toBeInTheDocument();

		expect(getByText('Current Time:')).toBeInTheDocument();
		expect(getByText('12:10 PM')).toBeInTheDocument();

		expect(getByText('Do This Later')).toBeInTheDocument();
		expect(getByText('Set Timezone')).toBeInTheDocument();
	});
});
