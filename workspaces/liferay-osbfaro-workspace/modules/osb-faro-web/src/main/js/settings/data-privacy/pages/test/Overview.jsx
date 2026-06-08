/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {fireEvent, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import PreferenceQuery from '~/shared/queries/PreferenceQuery';
import {DATA_RETENTION_PERIOD_KEY} from '~/shared/util/constants';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import {Overview} from '../Overview';

jest.unmock('react-dom');

jest.mock('~/shared/hooks/useCurrentUser', () => ({
	useCurrentUser: jest.fn(),
}));

const mockPreferenceReq = (value = '18144000000') => ({
	request: {
		query: PreferenceQuery,
		variables: {
			key: DATA_RETENTION_PERIOD_KEY,
		},
	},
	result: {
		data: {
			preference: {
				__typename: 'Preference',
				key: DATA_RETENTION_PERIOD_KEY,
				value,
			},
		},
	},
});

const DefaultComponent = ({mocks = [mockPreferenceReq()], ...props}) => (
	<Provider store={mockStore()}>
		<MemoryRouter initialEntries={['/workspace/23/settings/data-privacy']}>
			<Route path="/workspace/:groupId/settings/data-privacy">
				<MockedProvider mocks={mocks}>
					<Overview groupId="23" {...props} />
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('Data Privacy Overview', () => {
	it('renders', async () => {
		useCurrentUser.mockImplementation(() => ({
			isAdmin: () => true,
		}));

		const {container, getByText} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		fireEvent.click(getByText('Select an option'));

		expect(getByText('7 Months')).toBeTruthy();
		expect(getByText('13 Months')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});

	it('renders with disabled buttons in the Suppressed Users section if the user is not an AC admin', async () => {
		useCurrentUser.mockImplementation(() => ({
			isAdmin: () => false,
		}));

		const {container, getByTestId} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(getByTestId('export-suppressed-user-button')).toBeDisabled();
		expect(
			getByTestId('data-retention-period-select-input')
		).toBeDisabled();
	});
});
