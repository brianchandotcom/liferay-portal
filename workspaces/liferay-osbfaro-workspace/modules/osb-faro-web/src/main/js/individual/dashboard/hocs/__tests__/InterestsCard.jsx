import InterestsCard from '../InterestsCard';
import React from 'react';
import {act, cleanup, render} from '@testing-library/react';
import {BrowserRouter} from 'react-router-dom';
import {
	mockCompositionBag,
	mockIndividualInterestsReq
} from 'test/graphql-data';
import {MockedProvider} from '@apollo/client/testing';
import {omit} from 'lodash';

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '456',
		groupId: '123'
	})
}));

describe('InterestsCard', () => {
	afterEach(cleanup);

	it('renders', async () => {
		const {findByText, getByText} = render(
			<MockedProvider
				mocks={[
					mockIndividualInterestsReq(variables =>
						omit(variables, 'keywords')
					)
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

		expect(
			await findByText(/^top interests as of yesterday$/i)
		).toBeInTheDocument();
	});

	it('renders with empty data', async () => {
		const {findByText, getByText} = render(
			<MockedProvider
				mocks={[
					mockIndividualInterestsReq(
						variables => omit(variables, 'keywords'),
						{
							data: mockCompositionBag({
								compositionBagName: 'individualInterests',
								compositions: []
							})
						}
					)
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

		expect(
			await findByText(/^no interests were found\.$/i)
		).toBeInTheDocument();
	});
});
