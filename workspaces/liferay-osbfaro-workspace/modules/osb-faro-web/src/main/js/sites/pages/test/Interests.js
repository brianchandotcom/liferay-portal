/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import configureStore from '~/shared/store/store-dev';
import {Routes} from '~/shared/util/router';
import {mockPreferenceReq, mockTimeRangeReq} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import Interests from '../Interests';

jest.unmock('react-dom');

const DefaultComponent = ({
	mocks = [mockTimeRangeReq(), mockPreferenceReq()],
	...props
}) => (
	<Provider store={configureStore()}>
		<MemoryRouter initialEntries={['/workspace/23/sites/interests']}>
			<Route path={Routes.SITES_INTERESTS}>
				<MockedProvider mocks={mocks}>
					<Interests
						router={{params: {groupId: '23'}, query: {}}}
						{...props}
					/>
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('Sites Dashboard Interests', () => {
	it('render', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
