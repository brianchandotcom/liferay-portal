/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import client from '~/shared/apollo/client';
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
	it('renders', () => {
		const {getByTestId} = render(
			<WrapperComponent>
				<DateInput />
			</WrapperComponent>
		);

		expect(getByTestId('date-input')).toBeInTheDocument();
	});

	it('uses the displayFormat prop for displaying the date', () => {
		const {getByDisplayValue} = render(
			<WrapperComponent>
				<DateInput
					displayFormat="YYYY MM DD HH:mm"
					onDateInputChange={jest.fn()}
					value="1970-01-01"
				/>
			</WrapperComponent>
		);

		expect(getByDisplayValue('1970 01 01 00:00')).toBeTruthy();
	});
});
