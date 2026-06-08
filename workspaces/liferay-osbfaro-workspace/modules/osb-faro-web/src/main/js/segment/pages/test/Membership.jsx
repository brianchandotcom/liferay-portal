/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import {SegmentTypes} from '~/shared/util/constants';
import {Segment} from '~/shared/util/records';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import Membership, {MembershipChart} from '../Membership';

jest.unmock('react-dom');

const defaultProps = {
	channelId: '123',
	groupId: '23',
	growthHistory: {data: []},
	id: '321',
	segment: data.getImmutableMock(Segment, data.mockSegment),
	segmentType: SegmentTypes.Batch,
	timeZoneId: 'UTC',
};

describe('Membership', () => {
	const WrappedComponent = (props) => (
		<Provider store={mockStore()}>
			<StaticRouter>
				<Membership {...defaultProps} {...props} />
			</StaticRouter>
		</Provider>
	);

	it('renders', async () => {
		const {container} = render(<WrappedComponent />);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(
			screen.getByText('Segment Membership Trend')
		).toBeInTheDocument();
	});
});

describe('MembershipChart', () => {
	const WrappedComponent = (props) => (
		<StaticRouter>
			<MembershipChart {...defaultProps} {...props} />
		</StaticRouter>
	);

	it('renders', async () => {
		const {container} = render(<WrappedComponent />);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(screen.getByText('Known Members')).toBeInTheDocument();
	});
});
