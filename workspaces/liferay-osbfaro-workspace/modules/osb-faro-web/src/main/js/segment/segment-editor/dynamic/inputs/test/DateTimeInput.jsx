/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render} from '@testing-library/react';
import React from 'react';
import client from '~/shared/apollo/client';
import {Property} from '~/shared/util/records';
import {mockPreferenceReq} from '~/test/graphql-data';

import DateTimeInput from '../DateTimeInput';

jest.unmock('react-dom');

describe('DateTimeInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {getByText} = render(
			<ApolloProvider client={client}>
				<MockedProvider mocks={[mockPreferenceReq()]}>
					<DateTimeInput
						displayValue="Start Date Time"
						operatorRenderer={() => <div>operator</div>}
						property={new Property()}
						value="2012-12-12T00:00:00.000Z"
					/>
				</MockedProvider>
			</ApolloProvider>
		);

		expect(getByText('Start Date Time')).toBeInTheDocument();
		expect(getByText('operator')).toBeInTheDocument();
	});
});
