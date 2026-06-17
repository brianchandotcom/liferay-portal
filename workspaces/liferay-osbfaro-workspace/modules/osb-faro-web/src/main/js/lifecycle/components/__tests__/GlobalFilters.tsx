import GlobalFilter from '../GlobalFilters';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {LifecycleContextProvider} from '../../context/LifecycleContext';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({channelId: '456', groupId: '2000'}),
}));

describe('GlobalFilter', () => {
	afterEach(cleanup);

	it('should render both industry and country filters', () => {
		const useRequest = require('shared/hooks/useRequest');
		useRequest.useRequest = jest.fn(() => ({
			data: {items: []},
			loading: false,
		}));

		const {getByText} = render(
			<LifecycleContextProvider lifecycleId="1">
				<GlobalFilter />
			</LifecycleContextProvider>
		);

		expect(getByText('All Industries')).toBeInTheDocument();
		expect(getByText('All Countries')).toBeInTheDocument();
	});
});
