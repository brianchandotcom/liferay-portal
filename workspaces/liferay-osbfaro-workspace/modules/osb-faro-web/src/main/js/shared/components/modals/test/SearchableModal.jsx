/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {noop, times} from 'lodash';
import React from 'react';
import {MemoryRouter, Route} from 'react-router-dom';
import {createOrderIOMap} from '~/shared/util/pagination';
import {Routes} from '~/shared/util/router';
import {mockSegment} from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import SearchableModal from '../SearchableModal';

jest.unmock('react-dom');

describe('SearchableModal', () => {
	afterEach(cleanup);

	it('renders', async () => {
		const {container} = render(
			<MemoryRouter
				initialEntries={['/workspace/23/settings/data-source']}
			>
				<Route path={Routes.SETTINGS_DATA_SOURCE_LIST}>
					<SearchableModal
						dataSourceFn={() =>
							Promise.resolve({
								items: times(3, (i) => mockSegment(i)),
								total: 3,
							})
						}
						initialOrderIOMap={createOrderIOMap('name')}
						onChange={noop}
						onClose={noop}
					/>
				</Route>
			</MemoryRouter>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('renders with an empty state', async () => {
		const {container, queryByText} = render(
			<MemoryRouter
				initialEntries={['/workspace/23/settings/data-source']}
			>
				<Route path={Routes.SETTINGS_DATA_SOURCE_LIST}>
					<SearchableModal
						dataSourceFn={() =>
							Promise.resolve({items: [], total: 0})
						}
						onChange={noop}
						onClose={noop}
					/>
				</Route>
			</MemoryRouter>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(queryByText('There are no items found.')).toBeTruthy();
	});
});
