import * as API from 'shared/api';
import * as data from 'test/data';
import ActivitiesCard from '../ActivitiesCard';
import React from 'react';
import {Account} from 'shared/util/records';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

describe('ActivitiesCard', () => {
	it('should render', async () => {
		const {container} = render(
			<StaticRouter>
				<ActivitiesCard
					account={data.getImmutableMock(
						Account,
						data.mockAccount,
						'test'
					)}
					groupId='23'
				/>
			</StaticRouter>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('should render w/ loading', () => {
		const {container} = render(
			<StaticRouter>
				<ActivitiesCard
					account={data.getImmutableMock(
						Account,
						data.mockAccount,
						'test'
					)}
					groupId='23'
				/>
			</StaticRouter>
		);

		expect(container.querySelector('.loading-root')).toBeTruthy();
	});

	it('should render w/ ErrorDisplay', async () => {
		API.activities.fetchHistory.mockReturnValueOnce(Promise.reject({}));

		const {container, getByText} = render(
			<StaticRouter>
				<ActivitiesCard
					account={data.getImmutableMock(
						Account,
						data.mockAccount,
						'test'
					)}
					groupId='23'
				/>
			</StaticRouter>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(getByText('An unexpected error occurred.')).toBeTruthy();
	});
});
