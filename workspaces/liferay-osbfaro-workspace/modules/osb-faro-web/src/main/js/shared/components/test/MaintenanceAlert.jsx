/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {ProjectStates} from '~/shared/util/constants';
import {Project, RemoteData} from '~/shared/util/records';
import * as data from '~/test/data';
import {mockStoreData} from '~/test/mock-store';

import {MaintenanceAlert, mapState} from '../MaintenanceAlert';

jest.unmock('react-dom');

const store = mockStoreData.setIn(
	['projects', '23'],
	new RemoteData({
		data: data.getImmutableMock(Project, data.mockProject, '23', {
			state: ProjectStates.Scheduled,
			stateStartDate: data.getTimestamp(),
		}),
	})
);

const mockProject = data.getImmutableMock(Project, data.mockProject, '23', {
	state: ProjectStates.Scheduled,
	stateStartDate: data.getTimestamp(),
});

describe('MaintenanceAlert', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container, queryByText} = render(
			<MaintenanceAlert project={new Project()} />
		);

		// No alert shown when project state is not Scheduled

		expect(
			container.querySelector('.maintenance-alert-root')
		).toBeInTheDocument();
		expect(queryByText('Scheduled Maintenance')).toBeNull();
	});

	it('renders w/ maintenance alert', () => {
		const {getByText} = render(<MaintenanceAlert project={mockProject} />);

		// Alert should be shown when project state is Scheduled

		expect(getByText(/Scheduled Maintenance/)).toBeInTheDocument();
	});
});

describe('mapState', () => {
	it('maps store state to props', () => {
		const router = {match: {params: {groupId: '23'}}};

		const result = mapState(store, router);

		expect(result).toHaveProperty('project');
		expect(result).toHaveProperty('groupId', '23');
		expect(result).toHaveProperty('alertDismissed');
	});
});
