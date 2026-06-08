/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {fireEvent, render, screen, waitFor} from '@testing-library/react';
import React from 'react';
import {MemoryRouter, Route} from 'react-router-dom';
import {INTERVAL_KEY_MAP} from '~/shared/util/time';
import {mockPreferenceReq, mockTimeRangeReq} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import * as pedantic from '~/test/pedantic';

import HeaderDefault from '../HeaderDefault';

jest.unmock('react-dom');

const DefaultComponent = ({
	mocks = [mockTimeRangeReq(), mockPreferenceReq()],
	onChangeInterval = jest.fn(),
	...props
}) => (
	<MemoryRouter>
		<Route path="/">
			<MockedProvider addTypename={false} mocks={mocks}>
				<HeaderDefault
					label="Title"
					onChangeInterval={onChangeInterval}
					{...props}
				/>
			</MockedProvider>
		</Route>
	</MemoryRouter>
);

describe('HeaderDefault', () => {
	beforeEach(() => {
		pedantic.disable();
	});

	afterEach(() => {
		pedantic.enable();
	});

	it('renders', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('calls the onChangeInterval prop fn with "day" if the rangekey is changed to an hourly value', async () => {
		const spy = jest.fn();
		render(
			<DefaultComponent onChangeInterval={spy} rangeKey="LAST_30_DAYS" />
		);

		await waitForLoadingToBeRemoved();

		const dropdownToggle = screen.getByRole('button', {
			name: /select date range/i,
		});

		fireEvent.click(dropdownToggle);

		const last24HoursBtn = screen.getByText('Last 24 hours');

		fireEvent.click(last24HoursBtn);

		await waitFor(() =>
			expect(spy).toHaveBeenCalledWith(INTERVAL_KEY_MAP.day)
		);
	});
});
