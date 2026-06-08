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

import DateInput from '../DateInput';

jest.unmock('react-dom');

const WrapperComponent = ({children}) => (
	<ApolloProvider client={client}>
		<MockedProvider mocks={[mockPreferenceReq()]}>
			{children}
		</MockedProvider>
	</ApolloProvider>
);

describe('DateInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {getByText} = render(
			<WrapperComponent>
				<DateInput
					operatorRenderer={() => <div>operator</div>}
					property={new Property()}
				/>
			</WrapperComponent>
		);

		expect(getByText('operator')).toBeInTheDocument();
	});

	it('renders with data', () => {
		const {getByText} = render(
			<WrapperComponent>
				<DateInput
					displayValue="Start Date"
					operatorRenderer={() => <div>operator</div>}
					property={new Property()}
					value="12/12/12"
				/>
			</WrapperComponent>
		);

		expect(getByText('Start Date')).toBeInTheDocument();
		expect(getByText('operator')).toBeInTheDocument();
	});
});
