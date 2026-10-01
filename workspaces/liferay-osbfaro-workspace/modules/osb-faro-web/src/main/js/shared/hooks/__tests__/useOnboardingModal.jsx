import 'test/mock-modal';
import mockStore, {mockStoreData} from 'test/mock-store';
import React from 'react';
import SitesDashboardQuery from 'shared/queries/SitesDashboardQuery';
import {InMemoryCache, useQuery} from '@apollo/client';
import {mockDataSourcesReq} from 'test/graphql-data';
import {MockedProvider} from '@apollo/client/testing';
import {modalTypes, open} from 'shared/actions/modals';
import {OnboardingContext} from 'shared/context/onboarding';
import {Provider} from 'react-redux';
import {renderHook, waitFor} from '@testing-library/react';
import {useOnboardingModal} from 'shared/hooks/useOnboardingModal';

jest.unmock('react-dom');

const renderUseOnboardingModal = ({
	mocks = [mockDataSourcesReq()],
	onboardingTriggered = false,
	setOnboardingTriggered = jest.fn(),
	store = mockStore(),
} = {}) =>
	renderHook(
		() => {
			useOnboardingModal({groupId: '23'});

			return useQuery(SitesDashboardQuery, {variables: {type: null}});
		},
		{
			wrapper: ({children}) => (
				<Provider store={store}>
					<OnboardingContext.Provider
						value={{onboardingTriggered, setOnboardingTriggered}}
					>
						<MockedProvider
							cache={new InMemoryCache({addTypename: false})}
							mocks={mocks}
						>
							{children}
						</MockedProvider>
					</OnboardingContext.Provider>
				</Provider>
			),
		}
	);

describe('useOnboardingModal', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('opens the onboarding modal when the workspace has no data sources', async () => {
		const setOnboardingTriggered = jest.fn();

		renderUseOnboardingModal({setOnboardingTriggered});

		await waitFor(() =>
			expect(open).toHaveBeenCalledWith(
				modalTypes.ONBOARDING_MODAL,
				expect.objectContaining({groupId: '23'})
			)
		);

		expect(setOnboardingTriggered).toHaveBeenCalled();
	});

	it('does not open the onboarding modal when the workspace has data sources', async () => {
		const setOnboardingTriggered = jest.fn();

		const {result} = renderUseOnboardingModal({
			mocks: [
				mockDataSourcesReq([
					{
						__typename: 'DataSource',
						id: '123',
						name: 'foo datasource',
						url: 'foo.url',
					},
				]),
			],
			setOnboardingTriggered,
		});

		await waitFor(() => expect(result.current.loading).toBe(false));

		expect(open).not.toHaveBeenCalled();
		expect(setOnboardingTriggered).not.toHaveBeenCalled();
	});

	it('does not open the onboarding modal for non-admin users', async () => {
		const {result} = renderUseOnboardingModal({
			store: mockStore(mockStoreData.setIn(['currentUser', 'data'], '24')),
		});

		await waitFor(() => expect(result.current.loading).toBe(false));

		expect(open).not.toHaveBeenCalled();
	});

	it('does not open the onboarding modal twice', async () => {
		const {result} = renderUseOnboardingModal({onboardingTriggered: true});

		await waitFor(() => expect(result.current.loading).toBe(false));

		expect(open).not.toHaveBeenCalled();
	});
});
