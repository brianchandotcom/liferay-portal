/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, cleanup, render, screen} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import * as API from '~/shared/api';
import {ChannelContext} from '~/shared/context/channel';
import {UnassignedSegmentsContext} from '~/shared/context/unassignedSegments';
import {SegmentTypes} from '~/shared/util/constants';
import {User} from '~/shared/util/records';
import {Routes} from '~/shared/util/router';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import {mockChannelContext} from '~/test/mock-channel-context';
import mockStore from '~/test/mock-store';

import List from '../List';

jest.unmock('react-dom');

const MOCK_UNASSIGNED_SEGMENTS_CONTEXT = {
	showUnassignedAlert: false,
	unassignedSegments: [],
	unassignedSegmentsDispatch: jest.fn(),
};

const store = mockStore();

const DefaultComponent = ({queryString = '', ...otherProps}) => (
	<Provider store={store}>
		<MemoryRouter
			initialEntries={[
				`/workspace/23/123/contacts/segments${queryString}`,
			]}
		>
			<Route path={Routes.CONTACTS_LIST_SEGMENT}>
				<UnassignedSegmentsContext.Provider
					value={MOCK_UNASSIGNED_SEGMENTS_CONTEXT}
				>
					<ChannelContext.Provider value={mockChannelContext()}>
						<List
							channelId="123"
							currentUser={data.getImmutableMock(
								User,
								data.mockUser
							)}
							groupId="23"
							{...otherProps}
						/>
					</ChannelContext.Provider>
				</UnassignedSegmentsContext.Provider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('List', () => {
	beforeEach(() => {
		jest.clearAllMocks();

		jest.useFakeTimers();
	});

	afterEach(() => {
		cleanup();

		jest.useRealTimers();
	});

	it('disables batch segment when limit is reached', async () => {
		API.projects.fetchFeatureUsages.mockReturnValueOnce(
			Promise.resolve([
				{
					currentUsage: 1,
					limit: 3,
					name: 'Segment',
					type: 'Real Time',
				},
				{
					currentUsage: 5,
					limit: 5,
					name: 'Segment',
					type: 'Batch',
				},
			])
		);

		render(<DefaultComponent />);

		await act(async () => {
			jest.runAllTimers();
		});

		const batchOption = screen.getByTestId('batch-segment-dropdown-item');
		expect(batchOption.closest('a')).toHaveClass('disabled');

		const realTimeOption = screen.getByTestId(
			'real-time-segment-dropdown-item'
		);
		expect(realTimeOption.closest('a')).not.toHaveClass('disabled');
	});

	it('disables real time segment when limit is reached', async () => {
		API.projects.fetchFeatureUsages.mockReturnValueOnce(
			Promise.resolve([
				{
					currentUsage: 3,
					limit: 3,
					name: 'Segment',
					type: 'Real Time',
				},
				{
					currentUsage: 2,
					limit: 5,
					name: 'Segment',
					type: 'Batch',
				},
			])
		);

		render(<DefaultComponent />);

		await act(async () => {
			jest.runAllTimers();
		});

		const realTimeOption = screen.getByTestId(
			'real-time-segment-dropdown-item'
		);
		expect(realTimeOption.closest('a')).toHaveClass('disabled');

		const batchOption = screen.getByTestId('batch-segment-dropdown-item');
		expect(batchOption.closest('a')).not.toHaveClass('disabled');
	});

	it('enables segments when usage is under the limit', async () => {
		API.projects.fetchFeatureUsages.mockReturnValueOnce(
			Promise.resolve([
				{
					currentUsage: 2,
					limit: 3,
					name: 'Segment',
					type: 'Real Time',
				},
				{
					currentUsage: 2,
					limit: 5,
					name: 'Segment',
					type: 'Batch',
				},
			])
		);

		render(<DefaultComponent />);

		await act(async () => {
			jest.runAllTimers();
		});

		const realTimeOption = screen.getByTestId(
			'real-time-segment-dropdown-item'
		);
		expect(realTimeOption.closest('a')).not.toHaveClass('disabled');

		const batchOption = screen.getByTestId('batch-segment-dropdown-item');
		expect(batchOption.closest('a')).not.toHaveClass('disabled');
	});

	it('enables segment options when the limit is set to -1 (unlimited)', async () => {
		API.projects.fetchFeatureUsages.mockReturnValueOnce(
			Promise.resolve([
				{
					currentUsage: 2,
					limit: -1,
					name: 'Segment',
					type: 'Real Time',
				},
				{
					currentUsage: 2,
					limit: -1,
					name: 'Segment',
					type: 'Batch',
				},
			])
		);

		render(<DefaultComponent />);

		await act(async () => {
			jest.runAllTimers();
		});

		const realTimeOption = screen.getByTestId(
			'real-time-segment-dropdown-item'
		);
		expect(realTimeOption.closest('a')).not.toHaveClass('disabled');

		const batchOption = screen.getByTestId('batch-segment-dropdown-item');
		expect(batchOption.closest('a')).not.toHaveClass('disabled');
	});

	it('renders', async () => {
		API.projects.fetchFeatureUsages.mockResolvedValueOnce([
			{
				currentUsage: 0,
				limit: -1,
				name: 'Segment',
				type: 'Real Time',
			},
			{
				currentUsage: 0,
				limit: -1,
				name: 'Segment',
				type: 'Batch',
			},
		]);

		render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(document.body);

		expect(screen.getByText('Segments')).toBeInTheDocument();
	});

	it('shows the sequential info icon for real time sequential segments', async () => {
		API.projects.fetchFeatureUsages.mockResolvedValueOnce([]);
		API.individualSegment.search.mockReturnValue(
			Promise.resolve(
				data.mockSearch(data.mockSegment, 1, {
					segmentType: SegmentTypes.RealTime,
					sequential: true,
				})
			)
		);

		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(document.body);

		expect(container.querySelector('.sticker-info')).toBeInTheDocument();
	});

	it('does not show the sequential info icon for real time non-sequential segments', async () => {
		API.projects.fetchFeatureUsages.mockResolvedValueOnce([]);
		API.individualSegment.search.mockReturnValue(
			Promise.resolve(
				data.mockSearch(data.mockSegment, 1, {
					segmentType: SegmentTypes.RealTime,
					sequential: false,
				})
			)
		);

		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(document.body);

		expect(
			container.querySelector('.sticker-info')
		).not.toBeInTheDocument();
	});

	it('does not show the sequential info icon for batch segments', async () => {
		API.projects.fetchFeatureUsages.mockResolvedValueOnce([]);
		API.individualSegment.search.mockReturnValue(
			Promise.resolve(
				data.mockSearch(data.mockSegment, 1, {
					segmentType: SegmentTypes.Batch,
					sequential: true,
				})
			)
		);

		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(document.body);

		expect(
			container.querySelector('.sticker-info')
		).not.toBeInTheDocument();
	});
});
