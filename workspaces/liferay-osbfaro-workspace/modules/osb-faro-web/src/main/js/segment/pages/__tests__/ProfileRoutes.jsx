import * as API from 'shared/api';
import DataSourcesProvider from 'shared/context/dataSources';
import mockStore from 'test/mock-store';
import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import {ChannelContext} from 'shared/context/channel';
import {cleanup, render, screen} from '@testing-library/react';
import {mockChannelContext} from 'test/mock-channel-context';
import {mockSegment} from 'test/data';
import {Provider} from 'react-redux';
import {SegmentProfileRoutes} from '../ProfileRoutes';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '123',
		groupId: '23',
		id: 'test'
	})
}));

describe('SegmentProfileRoutes', () => {
	afterEach(cleanup);

	beforeAll(() => {
		delete window.location;
	});

	it('should render', async () => {
		window.location = {pathname: '/'};

		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<ChannelContext.Provider value={mockChannelContext()}>
						<DataSourcesProvider groupId='23'>
							<SegmentProfileRoutes />
						</DataSourcesProvider>
					</ChannelContext.Provider>
				</BrowserRouter>
			</Provider>
		);

		await waitForLoadingToBeRemoved(container);

		expect(screen.getAllByText('Seattle0').length).toBeGreaterThan(0);
	});

	it('should render the external reference code with its label', async () => {
		window.location = {pathname: '/'};

		API.individualSegment.fetch.mockReturnValueOnce(
			Promise.resolve(mockSegment(0, {externalReferenceCode: 'my-erc'}))
		);

		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<ChannelContext.Provider value={mockChannelContext()}>
						<DataSourcesProvider groupId='23'>
							<SegmentProfileRoutes />
						</DataSourcesProvider>
					</ChannelContext.Provider>
				</BrowserRouter>
			</Provider>
		);

		await waitForLoadingToBeRemoved(container);

		expect(screen.getByText('ERC: my-erc')).toBeTruthy();
	});
});
