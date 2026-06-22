import mockStore from 'test/mock-store';
import ProfileRoutes from '../ProfileRoutes';
import React from 'react';
import {ChannelContext} from 'shared/context/channel';
import {cleanup, render, screen} from '@testing-library/react';
import {createMemoryHistory} from 'history';
import {mockChannelContext} from 'test/mock-channel-context';
import {Provider} from 'react-redux';
import {Router} from 'react-router-dom';
import {useRequest} from 'shared/hooks/useRequest';

jest.unmock('react-dom');

jest.mock('shared/hooks/useRequest', () => ({
	useRequest: jest.fn()
}));

jest.mock('shared/util/breadcrumbs', () => ({
	getAccounts: jest.fn(() => ({active: false, label: 'Accounts'})),
	getEntityName: jest.fn(({label}: {label?: string} = {}) => ({
		active: true,
		label
	})),
	getHome: jest.fn(({label}: {label?: string} = {}) => ({
		active: false,
		label: label || 'Home'
	}))
}));

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '123',
		groupId: '23',
		id: 'acc-1'
	})
}));

jest.mock('../Activities', () => ({
	__esModule: true,
	default: () => <div data-testid='account-activities' />
}));

jest.mock('../Profile', () => ({
	__esModule: true,
	default: () => <div data-testid='account-profile' />
}));

const mockedUseRequest = useRequest as jest.Mock;

const store = mockStore();

const renderProfileRoutes = (
	history = createMemoryHistory({
		initialEntries: ['/workspace/23/123/accounts/acc-1']
	})
) =>
	render(
		<Provider store={store}>
			<ChannelContext.Provider value={mockChannelContext() as any}>
				<Router history={history}>
					<ProfileRoutes />
				</Router>
			</ChannelContext.Provider>
		</Provider>
	);

describe('AccountProfileRoutes', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	afterEach(cleanup);

	it('renders a loading indicator while the account request is in flight', () => {
		mockedUseRequest.mockReturnValue({
			data: null,
			error: false,
			loading: true
		});

		const {container} = renderProfileRoutes();

		expect(container.querySelector('.loading-root')).toBeInTheDocument();
		expect(screen.queryByText('Account Not Found')).not.toBeInTheDocument();
	});

	it('renders the not-found error page when the account request errors', () => {
		mockedUseRequest.mockReturnValue({
			data: null,
			error: true,
			loading: false
		});

		renderProfileRoutes();

		expect(screen.getByText('Account Not Found')).toBeInTheDocument();
		expect(
			screen.getByText('The account you are looking for does not exist.')
		).toBeInTheDocument();
		expect(screen.getByText('Go to Accounts')).toBeInTheDocument();
	});

	it('renders the not-found error page when the account does not exist', () => {
		mockedUseRequest.mockReturnValue({
			data: null,
			error: false,
			loading: false
		});

		renderProfileRoutes();

		expect(screen.getByText('Account Not Found')).toBeInTheDocument();
	});

	it('points the error page link back to the accounts list', () => {
		mockedUseRequest.mockReturnValue({
			data: null,
			error: true,
			loading: false
		});

		renderProfileRoutes();

		expect(screen.getByText('Go to Accounts').closest('a')).toHaveAttribute(
			'href',
			expect.stringContaining('accounts')
		);
	});

	it('renders the account page when the account exists', () => {
		mockedUseRequest.mockReturnValue({
			data: {accountName: 'Acme Corp'},
			error: false,
			loading: false
		});

		renderProfileRoutes();

		expect(screen.getAllByText('Acme Corp').length).toBeGreaterThan(0);
		expect(screen.queryByText('Account Not Found')).not.toBeInTheDocument();
	});
});
