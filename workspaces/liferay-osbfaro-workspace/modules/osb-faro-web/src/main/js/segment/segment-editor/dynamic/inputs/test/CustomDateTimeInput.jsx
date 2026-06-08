/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';
import client from '~/shared/apollo/client';
import {Property} from '~/shared/util/records';
import {mockPreferenceReq} from '~/test/graphql-data';

import {RelationalOperators} from '../../utils/constants';
import {createCustomValueMap} from '../../utils/custom-inputs';
import CustomDateTimeInput from '../CustomDateTimeInput';

jest.unmock('react-dom');

const mockValue = createCustomValueMap([
	{
		key: 'criterionGroup',
		value: [
			{
				operatorName: RelationalOperators.GT,
				propertyName: 'completeDate',
				value: '2020-01-17T00:00:00.000Z',
			},
		],
	},
]);

describe('CustomDateTimeInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {getAllByText, getByText} = render(
			<ApolloProvider client={client}>
				<MockedProvider mocks={[mockPreferenceReq()]}>
					<CustomDateTimeInput
						property={new Property()}
						timeZoneId="UTC"
						value={mockValue}
					/>
				</MockedProvider>
			</ApolloProvider>
		);

		expect(getByText('is after')).toBeInTheDocument();

		fireEvent.click(getByText('is after'));

		expect(getByText('is before')).toBeInTheDocument();
		expect(getByText('is')).toBeInTheDocument();
		expect(getAllByText('is after')[1]).toBeInTheDocument();
	});
});
