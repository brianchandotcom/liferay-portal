/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';
import {SEGMENTS} from '~/shared/util/router';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import InterestsCard, {InterestsList} from '../InterestsCard';

jest.unmock('react-dom');

describe('InterestsCard', () => {
	afterEach(cleanup);

	it('renders', async () => {
		const {container} = render(
			<StaticRouter>
				<InterestsCard
					entity={data.mockSegment(15)}
					groupId="23"
					type={SEGMENTS}
				/>
			</StaticRouter>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});

describe('InterestsList', () => {
	it('renders', () => {
		const {container} = render(
			<StaticRouter>
				<InterestsList
					groupId="23"
					id="foo"
					interests={[data.mockInterestData()]}
					type={SEGMENTS}
				/>
			</StaticRouter>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
