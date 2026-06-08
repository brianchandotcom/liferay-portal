/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {act, cleanup, render, waitFor} from '@testing-library/react';
import {omit} from 'lodash';
import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import {
	mockCompositionBag,
	mockIndividualInterestsReq,
} from '~/test/graphql-data';

import InterestsCard from '../InterestsCard';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '456',
		groupId: '123',
	}),
}));

describe('InterestsCard', () => {
	afterEach(cleanup);

	it('renders', async () => {
		const {getByText} = render(
			<MockedProvider
				mocks={[
					mockIndividualInterestsReq((variables) =>
						omit(variables, 'keywords')
					),
				]}
			>
				<BrowserRouter>
					<InterestsCard />
				</BrowserRouter>
			</MockedProvider>
		);

		await act(async () => {
			await jest.advanceTimersByTimeAsync(0);
		});

		await waitFor(() =>
			expect(
				getByText('Top Interests as of Yesterday')
			).toBeInTheDocument()
		);
	});

	it('renders with empty data', async () => {
		const {getByText} = render(
			<MockedProvider
				mocks={[
					mockIndividualInterestsReq(
						(variables) => omit(variables, 'keywords'),
						{
							data: mockCompositionBag({
								compositionBagName: 'individualInterests',
								compositions: [],
							}),
						}
					),
				]}
			>
				<BrowserRouter>
					<InterestsCard />
				</BrowserRouter>
			</MockedProvider>
		);

		await act(async () => {
			await jest.advanceTimersByTimeAsync(0);
		});

		await waitFor(() =>
			expect(
				getByText('There are no interests found.')
			).toBeInTheDocument()
		);
	});
});
