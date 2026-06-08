/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, queryByText, render} from '@testing-library/react';
import React from 'react';
import ReactDOM from 'react-dom';
import {StaticRouter} from 'react-router';
import * as API from '~/shared/api';
import {CredentialTypes} from '~/shared/util/constants';
import {DataSource} from '~/shared/util/records';
import {
	getImmutableMock,
	mockCSVDataSource,
	mockLiferayDataSource,
	mockSalesforceDataSource,
} from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import EnrichedProfilesCard from '../EnrichedProfilesCard';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		groupId: '23',
	}),
}));

ReactDOM.createPortal = jest.fn();

const DefaultComponent = (props) => (
	<StaticRouter>
		<EnrichedProfilesCard {...props} />
	</StaticRouter>
);

describe('EnrichedProfilesCard', () => {
	afterEach(() => {
		jest.clearAllMocks();

		cleanup();
	});

	it('renders', async () => {
		const {container} = render(<EnrichedProfilesCard />);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('renders a fallback display if count is not finite', async () => {
		API.individuals.fetchEnrichedProfilesCount.mockReturnValue(
			Promise.resolve({total: 0})
		);

		const {container} = render(<EnrichedProfilesCard />);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(container.querySelector('.total')).toHaveTextContent(
			'0 Profiles'
		);
	});
});

describe('EnrichedProfilesCard Enrich Profiles Prompt', () => {
	const baseDescription =
		'should render a prompt to enrich profiles if the only connected datasource is DXP with no contacts configuration';

	const enrichPrompt = /Know Your Audience Better/;

	const assertHasEnrichPrompt = (container) => {
		expect(queryByText(container, enrichPrompt)).toBeTruthy();
	};

	it(baseDescription, () => {
		const mockLiferay = getImmutableMock(
			DataSource,
			mockLiferayDataSource,
			0,
			{
				credentials: {type: CredentialTypes.Token},
				details: {contactsSelected: false},
			}
		);

		const {container} = render(
			<DefaultComponent dataSources={[mockLiferay]} />
		);

		assertHasEnrichPrompt(container);
	});

	it(`${baseDescription} and has a legacy authentication method`, () => {
		const mockLiferay = getImmutableMock(
			DataSource,
			mockLiferayDataSource,
			0,
			{credentials: {type: CredentialTypes.OAuth2}}
		);

		const {container} = render(
			<DefaultComponent dataSources={[mockLiferay]} />
		);

		assertHasEnrichPrompt(container);
	});

	it('renders a prompt to enrich profiles if there are no connected datasources', () => {
		const {container} = render(<DefaultComponent dataSources={[]} />);

		assertHasEnrichPrompt(container);
	});

	it('renders a prompt to enrich profiles if there are multiple DXPs without a contacts configuration', () => {
		const {container} = render(
			<DefaultComponent
				dataSources={[
					getImmutableMock(DataSource, mockLiferayDataSource),
					getImmutableMock(DataSource, mockLiferayDataSource),
				]}
			/>
		);

		assertHasEnrichPrompt(container);
	});

	it('does not render a prompt if the only connected datasources are non-DXP', () => {
		const {queryByText} = render(
			<DefaultComponent
				dataSources={[
					getImmutableMock(DataSource, mockSalesforceDataSource),
					getImmutableMock(DataSource, mockCSVDataSource),
				]}
			/>
		);

		expect(queryByText(enrichPrompt)).toBeNull();
	});
});
