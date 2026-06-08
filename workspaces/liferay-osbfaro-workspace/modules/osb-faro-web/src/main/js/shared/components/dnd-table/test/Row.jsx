/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import Row from '../Row';

jest.unmock('react-dom');

describe('Row', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<DndProvider backend={HTML5Backend}>
				<Row
					columns={[
						{
							accessor: 'title',
						},
					]}
					data={{title: 'Test Test'}}
					index={0}
					name="row"
					onMove={noop}
					rowIdentifier="title"
				/>
			</DndProvider>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders w/o drag handle', () => {
		const {container} = render(
			<DndProvider backend={HTML5Backend}>
				<Row
					columns={[
						{
							accessor: 'title',
						},
					]}
					data={{draggable: false, title: 'Test Test'}}
					index={0}
					name="row"
					onMove={noop}
					rowIdentifier="title"
				/>
			</DndProvider>
		);

		expect(container.querySelector('.drag-handle')).not.toBeNull();
	});
});
