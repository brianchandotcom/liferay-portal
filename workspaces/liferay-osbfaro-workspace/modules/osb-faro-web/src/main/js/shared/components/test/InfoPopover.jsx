/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import ReactDOM from 'react-dom';

import InfoPopover from '../InfoPopover';

jest.unmock('react-dom');

ReactDOM.createPortal = jest.fn();

describe('InfoPopover', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<InfoPopover content="foo content" title="foo title" />
		);
		expect(container).toMatchSnapshot();
	});
});
