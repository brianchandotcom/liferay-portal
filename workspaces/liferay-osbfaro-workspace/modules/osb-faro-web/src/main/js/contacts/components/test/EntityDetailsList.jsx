/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';
import {MemoryRouter, Route} from 'react-router-dom';
import {Routes} from '~/shared/util/router';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import EntityDetailsList from '../EntityDetailsList';

jest.unmock('react-dom');

const defaultProps = {
	groupId: '23',
	title: 'Test Test',
};

const DefaultComponent = (props) => (
	<MemoryRouter
		initialEntries={[
			'/workspace/23/321321/contacts/accounts/123123/interests/test',
		]}
	>
		<Route path={Routes.CONTACTS_ACCOUNT_INTEREST_DETAILS}>
			<EntityDetailsList {...defaultProps} {...props} />
		</Route>
	</MemoryRouter>
);

describe('EntityDetailsList', () => {
	it('renders', async () => {
		const {container} = render(
			<DefaultComponent
				demographicsIMap={fromJS(data.mockAccountDetails())}
			/>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('filters results by query', async () => {
		const {container, getByPlaceholderText} = render(
			<DefaultComponent
				demographicsIMap={fromJS(data.mockAccountDetails())}
			/>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		fireEvent.change(getByPlaceholderText('Search'), {
			target: {value: 'Agriculture'},
		});

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(
			container.querySelector('.subnav-tbar .tbar-item')
		).toHaveTextContent('1 Result for "Agriculture"');

		expect(container.querySelectorAll('table > tbody').length).toBe(1);
	});
});
