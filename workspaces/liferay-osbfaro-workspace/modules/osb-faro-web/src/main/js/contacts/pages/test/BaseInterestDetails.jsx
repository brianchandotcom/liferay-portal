/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';
import {Account, Segment} from '~/shared/util/records';
import {ACCOUNTS, Routes, SEGMENTS} from '~/shared/util/router';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockDate from '~/test/mock-date';

import BaseInterestDetails from '../BaseInterestDetails';

jest.unmock('react-dom');

describe('BaseInterestDetails', () => {
	beforeAll(() => mockDate());

	afterAll(() => jest.restoreAllMocks());

	it('renders', async () => {
		const {container, getByText} = render(
			<StaticRouter>
				<BaseInterestDetails
					channelId="123"
					entity={new Segment(data.mockSegment())}
					groupId="23"
					id="test"
					interestDetailsRoute={
						Routes.CONTACTS_SEGMENT_INTEREST_DETAILS
					}
					interestId="1"
					tabId="individuals"
					type={SEGMENTS}
				/>
			</StaticRouter>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(getByText('Back to Interests')).toBeInTheDocument();
	});

	it('renders an individuals list tab', () => {
		const {getByText} = render(
			<StaticRouter>
				<BaseInterestDetails
					entity={new Account(data.mockAccount())}
					groupId="23"
					id="test"
					interestDetailsRoute={
						Routes.CONTACTS_ACCOUNT_INTEREST_DETAILS
					}
					interestId="1"
					tabId="individuals"
					type={ACCOUNTS}
				/>
			</StaticRouter>
		);

		jest.runAllTimers();

		const individualsGrandparentElement =
			getByText('Individuals').parentElement?.parentElement;

		expect(getByText('Individuals')).toBeTruthy();

		expect(individualsGrandparentElement).toHaveClass('active');
	});

	it('renders an active pages list tab', () => {
		const {getByText} = render(
			<StaticRouter>
				<BaseInterestDetails
					active="true"
					entity={new Account(data.mockAccount())}
					groupId="23"
					id="test"
					interestDetailsRoute={
						Routes.CONTACTS_ACCOUNT_INTEREST_DETAILS
					}
					interestId="1"
					tabId="pages"
					type={ACCOUNTS}
				/>
			</StaticRouter>
		);

		jest.runAllTimers();

		const activePagesGrandparentElement =
			getByText('Active Pages').parentElement?.parentElement;

		expect(getByText('Active Pages')).toBeTruthy();

		expect(activePagesGrandparentElement).toHaveClass('active');
	});

	it('renders a pages list tab of inactive pages', () => {
		const {getByText} = render(
			<StaticRouter>
				<BaseInterestDetails
					active="false"
					entity={new Account(data.mockAccount())}
					groupId="23"
					id="test"
					interestDetailsRoute={
						Routes.CONTACTS_ACCOUNT_INTEREST_DETAILS
					}
					interestId="1"
					tabId="pages"
					type={ACCOUNTS}
				/>
			</StaticRouter>
		);

		jest.runAllTimers();

		const InactivePagesGrandparentElement =
			getByText('Inactive Pages').parentElement?.parentElement;

		expect(getByText('Inactive Pages')).toBeTruthy();

		expect(InactivePagesGrandparentElement).toHaveClass('active');
	});
});
