/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter} from 'react-router-dom';
import {Project, User} from '~/shared/util/records';
import * as data from '~/test/data';
import mockStore from '~/test/mock-store';

import AddWorkspaceForm from '../AddWorkspaceForm';
import {BasePageContext} from '../BasePage';

jest.unmock('react-dom');

const mockUser = new User(data.mockUser());

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<BasePageContext.Provider value={{currentUser: mockUser}}>
			<MemoryRouter>
				<AddWorkspaceForm
					onSubmit={jest.fn()}
					project={new Project(data.mockProject())}
					{...props}
				/>
			</MemoryRouter>
		</BasePageContext.Provider>
	</Provider>
);

describe('AddWorkspaceForm', () => {
	it('renders', () => {
		const {container} = render(<DefaultComponent />);
		expect(container).toMatchSnapshot();
	});

	it('renders the edit version', () => {
		const {container, getByTestId, queryByText} = render(
			<DefaultComponent
				editing
				project={data.getImmutableMock(Project, data.mockProject)}
			/>
		);

		expect(
			queryByText('You can only set your friendly workspace url once')
		).toBeNull();

		expect(container.querySelector('.select-root')).toBeDisabled();
		expect(queryByText('Save')).not.toBeNull();
		expect(getByTestId('server-location-input')).toBeDisabled();
	});

	it('disables friendlyURL input if friendlyURL exists on Project', () => {
		const {getByTestId} = render(
			<DefaultComponent
				project={data.getImmutableMock(Project, data.mockProject, 1, {
					friendlyURL: 'foo',
				})}
			/>
		);

		expect(getByTestId('friendly-url-input')).toBeDisabled();
	});
});
