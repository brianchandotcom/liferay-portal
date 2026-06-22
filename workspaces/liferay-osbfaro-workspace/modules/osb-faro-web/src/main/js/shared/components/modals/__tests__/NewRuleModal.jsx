import mockStore from 'test/mock-store';
import NewRuleModal from '../NewRuleModal';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {InMemoryCache} from '@apollo/client';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/client/testing';
import {mockRecommendationPageAssetsReq} from 'test/graphql-data';
import {Provider} from 'react-redux';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

const DefaultWrapper = ({children, mocks = []}) => (
	<Provider store={mockStore()}>
		<MemoryRouter initialEntries={['/']}>
			<Route path='/'>
				<MockedProvider
					cache={
						new InMemoryCache({
							addTypename: false,
							freezeResults: false
						})
					}
					mocks={mocks}
				>
					{children}
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('NewRuleModal', () => {
	afterEach(cleanup);

	it('should render', async () => {
		const {container} = render(
			<DefaultWrapper mocks={[mockRecommendationPageAssetsReq([])]}>
				<NewRuleModal />
			</DefaultWrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
