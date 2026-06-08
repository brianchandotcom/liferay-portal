/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {addAlert} from '~/shared/actions/alerts';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import {Project, User} from '~/shared/util/records';
import * as data from '~/test/data';
import mockStore from '~/test/mock-store';

import {Workspace} from '../Workspace';

jest.unmock('react-dom');

jest.mock('~/shared/actions/alerts', () => ({
	actionTypes: {},
	addAlert: jest.fn(() => ({meta: {}, payload: {}, type: 'addAlert'})),
}));

jest.mock('~/shared/hooks/useCurrentUser', () => ({
	useCurrentUser: jest.fn(),
}));

const defaultProps = {
	addAlert,
	emailAddressDomains: ['liferay.com'],
	groupId: '23',
	project: new Project(data.mockProject()),
};

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<MemoryRouter initialEntries={['/workspace/23/settings/workspace']}>
			<Route path="/workspace/:groupId/settings/workspace">
				<Workspace {...defaultProps} {...props} />
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('Workspace Settting', () => {
	afterEach(cleanup);

	it('renders', () => {
		useCurrentUser.mockImplementation(() => ({
			isAdmin: () => true,
		}));

		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('renders as disabled if the user is not an AC admin', () => {
		useCurrentUser.mockImplementation(() => ({
			isAdmin: () => false,
		}));

		const {container, getByDisplayValue, getByLabelText} = render(
			<DefaultComponent
				currentUser={data.getImmutableMock(User, data.mockMemberUser)}
			/>
		);

		expect(getByLabelText('Workspace Name')).toBeDisabled();
		expect(getByDisplayValue('Oregon, USA')).toBeDisabled();
		expect(
			container.querySelector('.input-list-root input')
		).toBeDisabled();
	});
});
