/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router-dom';
import client from '~/shared/apollo/client';
import {AssetTypes, RangeKeyTimeRanges} from '~/shared/util/constants';
import {
	mockAssetAppearsOnReq,
	mockPreferenceReq,
	mockTimeRangeReq,
} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import {
	Accessor,
	AssetAppearsOnCard,
	EmptyStateLink,
	EmptyStateText,
} from '../AssetAppearsOnCard';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		assetId: 'myBlogId',
		channelId: '123',
		groupId: '456',
		query: {
			rangeKey: RangeKeyTimeRanges.Last30Days,
		},
		title: 'Blog Title',
	}),
}));

const WrappedComponent = ({
	accessors,
	assetType,
	empty = false,
	emptyStateLink,
	emptyStateText,
}: {
	accessors: Accessor[];
	assetType: AssetTypes;
	empty?: boolean;
	emptyStateLink: EmptyStateLink;
	emptyStateText: EmptyStateText;
}) => (
	<Provider store={mockStore()}>
		<ApolloProvider client={client}>
			<StaticRouter>
				<MockedProvider
					mocks={[
						mockTimeRangeReq(),
						mockPreferenceReq(),
						mockAssetAppearsOnReq(
							{
								assetType: assetType.toUpperCase(),
								selectedMetrics: accessors,
							},
							empty
						),
					]}
				>
					<AssetAppearsOnCard
						accessors={accessors}
						assetType={assetType}
						emptyStateLink={emptyStateLink}
						emptyStateText={emptyStateText}
					/>
				</MockedProvider>
			</StaticRouter>
		</ApolloProvider>
	</Provider>
);

describe('AssetAppearsOnCard', () => {
	afterEach(cleanup);

	it('renders', async () => {
		const {getByText} = render(
			<WrappedComponent
				accessors={[Accessor.ViewsMetric]}
				assetType={AssetTypes.Blog}
				emptyStateLink={EmptyStateLink.Blog}
				emptyStateText={EmptyStateText.Blog}
			/>
		);

		await waitForLoadingToBeRemoved(document.body);

		expect(getByText('Asset Appears On')).toBeInTheDocument();
		expect(getByText('Views')).toBeInTheDocument();
	});

	it('has a Views column for Blog', async () => {
		const {container, getByText} = render(
			<WrappedComponent
				accessors={[Accessor.ViewsMetric]}
				assetType={AssetTypes.Blog}
				emptyStateLink={EmptyStateLink.Blog}
				emptyStateText={EmptyStateText.Blog}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(getByText('Views')).toBeInTheDocument();
	});

	it('has [Downloads, Impressions] columns for Document', async () => {
		const {container, getByText} = render(
			<WrappedComponent
				accessors={[
					Accessor.DownloadsMetric,
					Accessor.ImpressionMadeMetric,
				]}
				assetType={AssetTypes.Document}
				emptyStateLink={EmptyStateLink.Document}
				emptyStateText={EmptyStateText.Document}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(getByText('Downloads')).toBeInTheDocument();
		expect(getByText('Impressions')).toBeInTheDocument();
	});

	it('has a [Submissions, Views] column for Forms', async () => {
		const {container, getByText} = render(
			<WrappedComponent
				accessors={[Accessor.SubmissionsMetric, Accessor.ViewsMetric]}
				assetType={AssetTypes.Form}
				emptyStateLink={EmptyStateLink.Form}
				emptyStateText={EmptyStateText.Form}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(getByText('Submissions')).toBeInTheDocument();
		expect(getByText('Views')).toBeInTheDocument();
	});

	it('has a Views column for WebContent', async () => {
		const {container, getByText} = render(
			<WrappedComponent
				accessors={[Accessor.ViewsMetric]}
				assetType={AssetTypes.Journal}
				emptyStateLink={EmptyStateLink.Journal}
				emptyStateText={EmptyStateText.Journal}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(getByText('Views')).toBeInTheDocument();
	});

	it('renders empty state for Blog', async () => {
		const {container, getByText} = render(
			<WrappedComponent
				accessors={[Accessor.ImpressionMadeMetric]}
				assetType={AssetTypes.Blog}
				empty
				emptyStateLink={EmptyStateLink.Blog}
				emptyStateText={EmptyStateText.Blog}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const linkText = getByText('Learn more about blogs.');

		expect(
			getByText('There are no assets on the selected period.')
		).toBeInTheDocument();
		expect(
			getByText(
				'Check back later to verify if data has been received from your data sources.'
			)
		).toBeInTheDocument();
		expect(linkText).toBeInTheDocument();
		expect(linkText).toHaveAttribute('href', EmptyStateLink.Blog);
	});

	it('renders empty state for Documents and Media', async () => {
		const {container, getByText} = render(
			<WrappedComponent
				accessors={[Accessor.ImpressionMadeMetric]}
				assetType={AssetTypes.Document}
				empty
				emptyStateLink={EmptyStateLink.Document}
				emptyStateText={EmptyStateText.Document}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const linkText = getByText('Learn more about documents and media.');

		expect(
			getByText('There are no assets on the selected period.')
		).toBeInTheDocument();
		expect(
			getByText(
				'Check back later to verify if data has been received from your data sources.'
			)
		).toBeInTheDocument();
		expect(linkText).toBeInTheDocument();
		expect(linkText).toHaveAttribute('href', EmptyStateLink.Document);
	});

	it('renders empty state for Forms', async () => {
		const {container, getByText} = render(
			<WrappedComponent
				accessors={[Accessor.ImpressionMadeMetric]}
				assetType={AssetTypes.Form}
				empty
				emptyStateLink={EmptyStateLink.Form}
				emptyStateText={EmptyStateText.Form}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const linkText = getByText('Learn more about forms.');

		expect(
			getByText('There are no assets on the selected period.')
		).toBeInTheDocument();
		expect(
			getByText(
				'Check back later to verify if data has been received from your data sources.'
			)
		).toBeInTheDocument();
		expect(linkText).toBeInTheDocument();
		expect(linkText).toHaveAttribute('href', EmptyStateLink.Form);
	});

	it('renders empty state for Web content', async () => {
		const {container, getByText} = render(
			<WrappedComponent
				accessors={[Accessor.ImpressionMadeMetric]}
				assetType={AssetTypes.Journal}
				empty
				emptyStateLink={EmptyStateLink.Journal}
				emptyStateText={EmptyStateText.Journal}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const linkText = getByText('Learn more about web content.');

		expect(
			getByText('There are no assets on the selected period.')
		).toBeInTheDocument();
		expect(
			getByText(
				'Check back later to verify if data has been received from your data sources.'
			)
		).toBeInTheDocument();
		expect(linkText).toBeInTheDocument();
		expect(linkText).toHaveAttribute('href', EmptyStateLink.Journal);
	});
});
