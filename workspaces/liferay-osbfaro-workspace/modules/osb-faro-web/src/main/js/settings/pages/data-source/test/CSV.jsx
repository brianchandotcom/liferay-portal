/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import {UserRoleNames} from '~/shared/util/constants';
import {DataSource, User} from '~/shared/util/records';
import * as data from '~/test/data';
import mockStore from '~/test/mock-store';

import {CSV} from '../CSV';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		groupId: '23',
	}),
}));

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<StaticRouter>
			<CSV
				currentUser={new User(data.mockUser())}
				dataSource={new DataSource(data.mockCSVDataSource())}
				groupId="23"
				id="test"
				{...props}
			/>
		</StaticRouter>
	</Provider>
);

describe('CSV', () => {
	it('renders', () => {
		const {container, queryByText} = render(<DefaultComponent />);

		expect(queryByText(/Edit CSV/)).toBeTruthy();
		expect(container).toMatchSnapshot();
	});

	it('does not render an Edit CSV Configuration button if the user role is member', () => {
		const {queryByText} = render(
			<DefaultComponent
				currentUser={
					new User(data.mockUser(0, {roleName: UserRoleNames.Member}))
				}
			/>
		);

		expect(queryByText(/Edit CSV/)).toBeNull();
	});
});
