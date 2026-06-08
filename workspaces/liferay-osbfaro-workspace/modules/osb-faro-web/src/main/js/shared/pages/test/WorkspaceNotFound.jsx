/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {BrowserRouter} from 'react-router-dom';
import * as Router from '~/shared/util/router';
import mockStore from '~/test/mock-store';

import WorkspaceNotFound from '../WorkspaceNotFound';

Router.navigate = jest.fn();

jest.unmock('react-dom');

const renderWithProvider = (props) => (
	<Provider store={mockStore()}>
		<BrowserRouter>
			<WorkspaceNotFound {...props} />
		</BrowserRouter>
	</Provider>
);

describe('WorkspaceNotFound', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(renderWithProvider());

		expect(container).toMatchSnapshot();
	});

	it('contains the link to go back to the workspaces page', () => {
		const {getByText} = render(renderWithProvider());

		expect(getByText('Go Back to Your Workspaces')).toHaveAttribute(
			'href',
			'/workspaces'
		);
	});

	it('adds a custom classname', () => {
		const {container} = render(
			renderWithProvider({className: 'custom-classname'})
		);

		expect(
			container.querySelector('.workspace-not-found-root').classList
		).toContain('custom-classname');
	});
});
