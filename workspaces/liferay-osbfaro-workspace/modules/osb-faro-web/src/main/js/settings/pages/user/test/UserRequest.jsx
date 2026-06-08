/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import {noop} from 'lodash';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {Routes} from '~/shared/util/router';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import UserRequest from '../UserRequest';

jest.unmock('react-dom');

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={['/workspace/23/settings/users/requests']}
		>
			<Route path={Routes.SETTINGS_USERS_REQUESTS}>
				<MockedProvider freezeResults={false}>
					<UserRequest {...props} onSetUserRequest={noop} />
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('UserRequest', () => {
	it('renders', async () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
