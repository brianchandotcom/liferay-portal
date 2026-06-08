/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';

import AccountMembership from '../AccountMembership';

jest.unmock('react-dom');

describe('Account Membership', () => {
	const mockData = {
		accountName: 'Acme Corporation',
		accountType: 'Customer',
		annualRevenue: '1000000',
		country: 'United States',
		createdDate: '2020-01-01T00:00:00.000Z',
		currencyCode: 'USD',
		customerSince: 2015,
		id: '001xx000003DGbYAAW',
		industry: 'Manufacturing',
		lastActivityDate: '2021-12-01T00:00:00.000Z',
		numberOfEmployees: '500',
		state: 'California',
	};

	it('renders the snapshot', () => {
		const {container} = render(
			<AccountMembership accountData={fromJS(mockData)} />
		);
		expect(container).toMatchSnapshot();
	});

	it('renders the empty state when showEmptyState is true', () => {
		const {getByText, queryByText} = render(
			<AccountMembership accountData={fromJS(mockData)} showEmptyState>
				<div>empty state rendered</div>
			</AccountMembership>
		);

		expect(getByText('empty state rendered')).toBeTruthy();
		expect(queryByText('industry')).toBeNull();
	});

	it('correctlies format the time entries', () => {
		const {getByText} = render(
			<AccountMembership accountData={fromJS(mockData)} />
		);

		expect(getByText('2015')).toBeTruthy();
		expect(getByText('2021-12-01')).toBeTruthy();
		expect(getByText('2020-01-01')).toBeTruthy();
	});

	it('displays the fallback dash for missing account values', () => {
		const {getAllByText} = render(
			<AccountMembership accountData={fromJS({})} />
		);

		const dashes = getAllByText('-');
		expect(dashes.length).toBeGreaterThan(0);
	});

	it('renders annualRevenue without throwing when currencyCode is null', () => {
		expect(() =>
			render(
				<AccountMembership
					accountData={fromJS({
						...mockData,
						currencyCode: null,
					})}
				/>
			)
		).not.toThrow();
	});
});
