/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import * as API from '~/shared/api';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import EmailReports from '../EmailReports';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		groupId: '2000',
	}),
}));

const WrappedComponent = ({reports, ...otherProps}: any) => {

	// @ts-ignore

	API.preferences.fetchEmailReport = jest.fn(() => Promise.resolve(reports));

	return (

		// @ts-ignore

		<Provider store={mockStore()}>
			<EmailReports channelId="1234" {...otherProps} />
		</Provider>
	);
};

describe('EmailReports', () => {
	it('renders', async () => {
		const {container} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('renders with config btn enabled', async () => {
		const {container} = render(<WrappedComponent sitesSynced />);

		await waitForLoadingToBeRemoved(container);

		const configBtn = container.querySelector('button');

		expect(container).toContainElement(configBtn);
		expect(configBtn).toBeInTheDocument();
		expect(configBtn).toBeEnabled();
	});

	it('renders email report message w/ status disabled', async () => {
		const {container, getByText} = render(
			<WrappedComponent
				reports={{
					1234: {enabled: false},
				}}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(getByText('Email Reports: Disabled'));
	});

	it('renders email report message w/ status enabled', async () => {
		const {container, getByText} = render(
			<WrappedComponent
				reports={{
					1234: {enabled: true},
				}}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(getByText('Email Reports: Enabled'));
	});

	it('renders with config btn disabled', async () => {
		const {container} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(container);

		const configBtn = container.querySelector('button');

		expect(container).toContainElement(configBtn);
		expect(configBtn).toBeInTheDocument();
		expect(configBtn).toBeDisabled();
	});
});
