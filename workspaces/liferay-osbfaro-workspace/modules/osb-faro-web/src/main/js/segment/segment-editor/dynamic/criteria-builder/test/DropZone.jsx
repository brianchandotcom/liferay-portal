/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import DropZone from '../DropZone';

jest.unmock('react-dom');

describe('DropZone', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<DndProvider backend={HTML5Backend}>
				<DropZone />
			</DndProvider>
		);

		expect(container).toMatchSnapshot();
	});
});
