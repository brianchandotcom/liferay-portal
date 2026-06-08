/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import WorkspaceList from '..';
import {cleanup, render} from '@testing-library/react';
import {fromJS} from 'immutable';
import {range, uniqueId} from 'lodash';
import React from 'react';
import {StaticRouter} from 'react-router';
import {DataSourceStates} from '~/shared/util/constants';
import {Project} from '~/shared/util/records';
import * as data from '~/test/data';

const FRIENDLY_URL_BASE = '/faro-liferay-';

jest.unmock('react-dom');

const mockAccountList = range(3).map(
	(i) =>
		new Project(
			fromJS(
				data.mockProject(i, {
					groupId: Number(uniqueId()),
					name: `mockProject_AccountA ${i}`,
				})
			)
		)
);

const mockAccountWithFriendlyURLList = range(2).map(
	(i) =>
		new Project(
			fromJS(
				data.mockProject(i, {
					corpProjectUuid: null,
					friendlyURL: `${FRIENDLY_URL_BASE}${i}`,
					groupId: 123,
					name: `mockProject_AccountA ${i}`,
				})
			)
		)
);

const mockAccountWithNullStateList = range(2).map(
	(i) =>
		new Project(
			fromJS(
				data.mockProject(i, {
					corpProjectUuid: null,
					friendlyURL: `${FRIENDLY_URL_BASE}${i}`,
					groupId: null,
					name: `mockProject_AccountA ${i}`,
					state: null,
				})
			)
		)
);

const mockAccountWithUnconfiguredList = range(2).map(
	(i) =>
		new Project(
			fromJS(
				data.mockProject(i, {
					corpProjectUuid: '12345',
					groupId: 123,
					name: `mockProject_AccountA ${i}`,
					state: DataSourceStates.Unconfigured,
				})
			)
		)
);

const DefaultComponent = (props) => (
	<StaticRouter>
		<WorkspaceList {...props} />
	</StaticRouter>
);

describe('WorkspaceList', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<DefaultComponent accounts={mockAccountList} />
		);
		expect(container).toMatchSnapshot();
	});

	it('renders WorkspaceList with friendlyURL', () => {
		const {container} = render(
			<DefaultComponent accounts={mockAccountWithFriendlyURLList} />
		);
		expect(container).toMatchSnapshot();
	});

	it('renders an unconfigured WorkspaceList', () => {
		const {queryAllByText} = render(
			<DefaultComponent accounts={mockAccountWithUnconfiguredList} />
		);

		expect(queryAllByText('Configuration Required')).toBeTruthy();
	});

	it('renders an unconfigured WorkspaceList when the state and groupId is null', () => {
		const {queryAllByText} = render(
			<DefaultComponent accounts={mockAccountWithNullStateList} />
		);

		expect(queryAllByText('Configuration Required')).toBeTruthy();
	});
});
