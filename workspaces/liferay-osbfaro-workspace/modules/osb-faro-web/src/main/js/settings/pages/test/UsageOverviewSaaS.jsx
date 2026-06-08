/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import {Project} from '~/shared/util/records';
import {SubscriptionNames} from '~/shared/util/subscriptions';
import * as data from '~/test/data';
import mockStore from '~/test/mock-store';

import UsageOverviewSaaS from '../UsageOverviewSaaS';

jest.unmock('react-dom');

jest.mock('~/shared/hooks/useTimeZone', () => ({
	useTimeZone: () => ({timeZoneId: 'UTC'}),
}));

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		groupId: '23',
	}),
}));

jest.mock('~/shared/hooks/useCurrentUser', () => ({
	useCurrentUser: jest.fn(),
}));

const defaultProps = {
	groupId: '23',
	project: new Project(
		data.mockProject(23, {
			faroSubscription: fromJS(
				data.mockSubscription({
					name: SubscriptionNames.LiferaySaasEnterprisePlan,
				})
			),
		})
	),
};

const WrappedComponent = (props) => (
	<Provider store={mockStore()}>
		<StaticRouter>
			<UsageOverviewSaaS {...props} />
		</StaticRouter>
	</Provider>
);

describe('UsageOverviewSaaS', () => {
	it('renders', () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const {container} = render(<WrappedComponent {...defaultProps} />);

		expect(container).toMatchSnapshot();
	});

	it('renders the Hero Banner', () => {
		const {container} = render(<WrappedComponent {...defaultProps} />);

		expect(container.querySelector('.saas-banner')).toBeTruthy();
	});

	it('renders the "Usage Overview" page for SaaS title', () => {
		const {getByText} = render(<WrappedComponent {...defaultProps} />);

		expect(getByText('View Your SaaS Project Metrics')).toBeInTheDocument();
	});

	it('renders the "Go to Customer Portal" button for admin user', () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const {queryByText} = render(<WrappedComponent {...defaultProps} />);

		expect(queryByText('Go to Customer Portal')).toBeInTheDocument();
	});

	it('does not render the "Go to Customer Portal" button for member user', () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => false}));

		const {queryByText} = render(<WrappedComponent {...defaultProps} />);

		expect(queryByText('Go to Customer Portal')).toBeNull();
	});

	it('renders the "Sites and Users" and "Resource Usage" sections', () => {
		const {getByText} = render(<WrappedComponent {...defaultProps} />);

		expect(getByText('Sites and Users')).toBeInTheDocument();
		expect(getByText('Resource Usage')).toBeInTheDocument();
	});
});
