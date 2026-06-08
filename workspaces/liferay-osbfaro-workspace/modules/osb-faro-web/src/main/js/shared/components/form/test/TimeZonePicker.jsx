/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter} from 'react-router-dom';
import mockStore from '~/test/mock-store';
import * as pedantic from '~/test/pedantic';

import TimeZonePicker from '../TimeZonePicker';
import Form from '../index';

jest.unmock('react-dom');

const DefaultComponent = (props) => (
	<Provider
		store={mockStore(
			fromJS({
				projects: {
					23: {
						data: {
							timeZone: {
								timeZoneId: 'UTC',
							},
						},
					},
				},
			})
		)}
	>
		<MemoryRouter>
			<MockedProvider
				cache={
					new InMemoryCache({
						addTypename: false,
					})
				}
				mocks={[]}
			>
				<Form
					initialValues={{
						timeZoneId: 'UTC',
					}}
					onSubmit={jest.fn()}
				>
					<TimeZonePicker
						fieldName="timeZoneId"
						setFieldTouched={jest.fn()}
						setFieldValue={jest.fn()}
						{...props}
					/>
				</Form>
			</MockedProvider>
		</MemoryRouter>
	</Provider>
);

describe('TimeZonePicker', () => {
	beforeEach(() => {
		pedantic.disable();
	});

	afterEach(() => {
		pedantic.enable();
		cleanup();
	});

	it('renders', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});
});
