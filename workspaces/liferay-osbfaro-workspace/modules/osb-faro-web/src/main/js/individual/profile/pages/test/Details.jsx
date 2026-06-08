/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import {Individual} from '~/shared/util/records';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import Details from '../Details';

jest.unmock('react-dom');

describe('IndividualDetails', () => {
	afterEach(cleanup);

	it('renders', async () => {
		const {container} = render(
			<StaticRouter>
				<Provider store={mockStore()}>
					<Details
						groupId="23"
						id="test"
						individual={data.getImmutableMock(
							Individual,
							data.mockIndividual
						)}
					/>
				</Provider>
			</StaticRouter>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
