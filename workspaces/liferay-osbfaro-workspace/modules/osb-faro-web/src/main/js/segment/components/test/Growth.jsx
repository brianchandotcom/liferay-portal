/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';
import {MemoryRouter, Route} from 'react-router-dom';
import {Routes} from '~/shared/util/router';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import SegmentGrowthWithList, {
	SegmentGrowthChart,
	SelectedPointInfo,
} from '../Growth';

jest.unmock('react-dom');

describe('SegmentGrowthWithList', () => {
	it('renders', async () => {
		const {container} = render(
			<MemoryRouter
				initialEntries={[
					'/workspace/23/123123/contacts/segments/321321/membership',
				]}
			>
				<Route path={Routes.CONTACTS_SEGMENT_MEMBERSHIP}>
					<SegmentGrowthWithList
						channelId="123"
						data={[
							{
								added: 1,
								modifiedDate: data.getTimestamp(),
								removed: 3,
							},
						]}
						groupId="23"
						id="3"
						onPointSelect={jest.fn()}
					/>
				</Route>
			</MemoryRouter>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(screen.getByText('Known Members')).toBeInTheDocument();
	});
});

describe('SegmentGrowthChart', () => {
	it('renders', () => {
		render(<SegmentGrowthChart data={[]} onPointSelect={jest.fn()} />);

		expect(
			screen.getByText('There is no data for segment membership.')
		).toBeInTheDocument();
	});
});

describe('SelectedPointInfo', () => {
	it('renders', () => {
		render(
			<SelectedPointInfo
				data={[
					{
						added: 1,
						modifiedDate: data.getTimestamp(),
						removed: 3,
					},
				]}
				hasSelectedPoint
				onClearSelection={jest.fn()}
				selectedPoint={0}
			/>
		);

		expect(screen.getByText('Known Members')).toBeInTheDocument();
	});
});
