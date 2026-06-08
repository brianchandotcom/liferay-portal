/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {List} from 'immutable';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import {Segment} from '~/shared/util/records';
import * as data from '~/test/data';
import mockStore from '~/test/mock-store';

import DynamicSegmentEdit from '../Dynamic';

jest.unmock('react-dom');

jest.mock(
	'~/contacts/hoc/segment/WithBaseEdit',
	() => (Component) => Component
);
jest.mock(
	'~/segment/segment-editor/dynamic/hoc/WithPropertyGroups',
	() => (Component) => Component
);
jest.mock('uuid', () => ({
	v4: () => '00000000-0000-0000-0000-000000000000',
}));

describe('DynamicSegmentEdit', () => {
	afterEach(cleanup);

	it('renders in BATCH mode', () => {
		const SEGMENT_ID = '123';
		const GROUP_ID = '23';

		const batchSegmentMock = data.getImmutableMock(
			Segment,
			data.mockSegment,
			SEGMENT_ID,
			{segmentType: 'BATCH'}
		);

		const {container} = render(
			<StaticRouter>
				<Provider store={mockStore()}>
					<DndProvider backend={HTML5Backend}>
						<DynamicSegmentEdit
							groupId={GROUP_ID}
							id={SEGMENT_ID}
							propertyGroupsIList={new List()}
							segment={batchSegmentMock}
						/>
					</DndProvider>
				</Provider>
			</StaticRouter>
		);

		expect(container).toMatchSnapshot();
	});
});
