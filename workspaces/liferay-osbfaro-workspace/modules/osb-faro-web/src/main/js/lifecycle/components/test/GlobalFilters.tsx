/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import {LifecycleContextProvider} from '../../context/LifecycleContext';
import GlobalFilter from '../GlobalFilters';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({channelId: '456', groupId: '2000'}),
}));

describe('GlobalFilter', () => {
	afterEach(cleanup);

	it('renders both industry and country filters', () => {
		const useRequest = require('~/shared/hooks/useRequest');
		useRequest.useRequest = jest.fn(() => ({
			data: {items: []},
			loading: false,
		}));

		const {getByText} = render(
			<LifecycleContextProvider lifecycleId="1">
				<GlobalFilter />
			</LifecycleContextProvider>
		);

		expect(getByText('All Industries')).toBeInTheDocument();
		expect(getByText('All Countries')).toBeInTheDocument();
	});
});
