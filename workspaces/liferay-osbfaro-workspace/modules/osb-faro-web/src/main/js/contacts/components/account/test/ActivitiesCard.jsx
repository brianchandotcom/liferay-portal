/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';
import * as API from '~/shared/api';
import {Account} from '~/shared/util/records';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import ActivitiesCard from '../ActivitiesCard';

jest.unmock('react-dom');

describe('ActivitiesCard', () => {
	it('renders', async () => {
		const {container} = render(
			<StaticRouter>
				<ActivitiesCard
					account={data.getImmutableMock(
						Account,
						data.mockAccount,
						'test'
					)}
					groupId="23"
				/>
			</StaticRouter>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('renders w/ loading', () => {
		const {container} = render(
			<StaticRouter>
				<ActivitiesCard
					account={data.getImmutableMock(
						Account,
						data.mockAccount,
						'test'
					)}
					groupId="23"
				/>
			</StaticRouter>
		);

		expect(container.querySelector('.loading-root')).toBeTruthy();
	});

	it('renders w/ ErrorDisplay', async () => {
		API.activities.fetchHistory.mockReturnValueOnce(Promise.reject({}));

		const {container, getByText} = render(
			<StaticRouter>
				<ActivitiesCard
					account={data.getImmutableMock(
						Account,
						data.mockAccount,
						'test'
					)}
					groupId="23"
				/>
			</StaticRouter>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(getByText('An unexpected error occurred.')).toBeTruthy();
	});
});
