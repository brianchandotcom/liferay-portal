import mockStore from 'test/mock-store';
import React from 'react';
import {ENABLE_BLOCKLIST_KEYWORDS} from 'shared/util/feature-flags';
import {Overview} from '../Overview';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {MemoryRouter} from 'react-router-dom';

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '456',
		groupId: '2000'
	})
}));

// TODO: LRAC-4511 Remove DEVELOPER_MODE

jest.mock('shared/util/constants', () => ({
	__esModule: true,
	...jest.requireActual('shared/util/constants'),
	DEVELOPER_MODE: true,
}));

describe('Definitions Overview', () => {
	it('should render a list of definitions that includes individuals, accounts, behaviors, events, search and interests', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<MemoryRouter>
					<Overview groupId='23' />
				</MemoryRouter>
			</Provider>
		);

		jest.runAllTimers();

		expect(
			container.querySelectorAll('.list-group-title a')[0]
		).toHaveTextContent(/individuals/i);

		expect(
			container.querySelectorAll('.list-group-title a')[1]
		).toHaveTextContent(/accounts/i);

		expect(
			container.querySelectorAll('.list-group-title a')[2]
		).toHaveTextContent(/behaviors/i);

		expect(
			container.querySelectorAll('.list-group-title a')[3]
		).toHaveTextContent(/events/i);

		expect(
			container.querySelectorAll('.list-group-title a')[4]
		).toHaveTextContent(/event attributes/i);

		expect(
			container.querySelectorAll('.list-group-title a')[5]
		).toHaveTextContent(/search/i);

		if (ENABLE_BLOCKLIST_KEYWORDS) {
			expect(
				container.querySelectorAll('.list-group-title a')[6]
			).toHaveTextContent(/interests/i);
		}
	});
});
