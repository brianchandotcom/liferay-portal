/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router-dom';
import * as API from '~/shared/api';
import {ProjectStates} from '~/shared/util/constants';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import WorkspaceListItem from '../ListItem';

jest.unmock('react-dom');

describe('WorkspaceListItem', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<StaticRouter>
				<WorkspaceListItem
					accountName=""
					projectState={ProjectStates.Ready}
				/>
			</StaticRouter>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders a workspace item as enabled if is unavailable and the user clicks the item to reload and it then changes to available', async () => {
		API.projects.fetch.mockImplementationOnce(() =>
			Promise.resolve(data.mockProject('23'))
		);

		const {container, queryByText} = render(
			<StaticRouter>
				<WorkspaceListItem projectState={ProjectStates.Unavailable} />
			</StaticRouter>
		);

		const button = queryByText('Workspace unavailable; click to reload.');

		expect(button).toBeTruthy();

		fireEvent.click(button);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(
			queryByText('Workspace unavailable; click to reload.')
		).toBeNull();
	});

	it('renders a workspace with projects you can join', () => {
		const {queryByText} = render(
			<StaticRouter>
				<WorkspaceListItem
					isJoinableProjects
					projectState={ProjectStates.Ready}
				/>
			</StaticRouter>
		);

		expect(queryByText('Request Access')).toBeTruthy();
	});

	it('renders a workspace with a deactivated project', () => {
		const {queryByText} = render(
			<StaticRouter>
				<WorkspaceListItem projectState={ProjectStates.Deactivated} />
			</StaticRouter>
		);
		expect(queryByText('Activate')).toBeTruthy();
	});

	it('renders contact sales and limit message when isLDPEnabled is true', () => {
		const {getByText} = render(
			<StaticRouter>
				<WorkspaceListItem
					hasLimitReached
					projectState={ProjectStates.Ready}
				/>
			</StaticRouter>
		);

		expect(
			getByText(
				'Access to Liferay Data Platform has been restricted because your workspace has reached the known individuals or page view limit. Please contact sales to proceed.'
			)
		).toBeTruthy();

		expect(getByText('Contact Sales')).toBeTruthy();
	});
});
