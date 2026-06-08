/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';
import {Provider} from 'react-redux';
import {BrowserRouter} from 'react-router-dom';
import {Project} from '~/shared/util/records';
import * as Router from '~/shared/util/router';
import {Routes, toRoute} from '~/shared/util/router';
import * as data from '~/test/data';
import mockStore from '~/test/mock-store';

import {AddWorkspace, routingFn} from '../AddWorkspace';

const mockConfiguredProject = new Project(fromJS(data.mockProject(23)));

const mockUnconfiguredProject = new Project(
	fromJS(data.mockProject(0, {name: ''}))
);

jest.unmock('react-dom');

jest.mock('~/shared/components/workspaces/AddWorkspaceForm', () => () => null);

describe('AddWorkspace', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {getByText} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<AddWorkspace
						history={{}}
						project={mockUnconfiguredProject}
					/>
				</BrowserRouter>
			</Provider>
		);

		expect(getByText('Create Workspace')).toBeInTheDocument();
	});
});

describe('routingFn', () => {
	it('routes the user to the workspace home page if the project is already configred', () => {
		const expectedRoute = toRoute(Routes.WORKSPACE_WITH_ID, {
			groupId: '23',
		});

		expect(routingFn({project: mockConfiguredProject})).toBe(expectedRoute);
	});
});
