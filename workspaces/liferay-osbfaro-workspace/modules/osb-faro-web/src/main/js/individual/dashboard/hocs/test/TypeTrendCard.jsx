/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render} from '@testing-library/react';
import React from 'react';
import ReactDOM from 'react-dom';
import {mockIndividualMetricsReq} from '~/test/graphql-data';

import TypeTrendCard from '../TypeTrendCard';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '123123',
	}),
}));

ReactDOM.createPortal = jest.fn();

describe('TypeTrendCard', () => {
	afterEach(cleanup);

	xit('renders', () => {
		const {container} = render(
			<MockedProvider mocks={[mockIndividualMetricsReq()]}>
				<TypeTrendCard channelId="123123" />
			</MockedProvider>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
