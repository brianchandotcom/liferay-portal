/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router-dom';
import {Segment} from '~/shared/util/records';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import SegmentProfileCard from '../ProfileCard';

jest.unmock('react-dom');

const DefaultComponent = (props) => (
	<StaticRouter>
		<SegmentProfileCard
			channelId="123"
			groupId="23"
			id="3"
			segment={data.getImmutableMock(Segment, data.mockSegment, '3')}
			{...props}
		/>
	</StaticRouter>
);

describe('SegmentProfileCard', () => {
	it('renders', async () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
