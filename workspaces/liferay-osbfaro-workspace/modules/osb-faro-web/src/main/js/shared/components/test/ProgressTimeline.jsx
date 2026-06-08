/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';

import ProgressTimeline from '../ProgressTimeline';

jest.unmock('react-dom');

describe('ProgressTimeline', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<StaticRouter>
				<ProgressTimeline
					activeIndex={1}
					items={[
						{
							title: 'This is a really long title for this step',
						},
						{title: 'Step 2'},
						{title: 'Step 3'},
						{title: 'Step 4'},
					]}
				/>
			</StaticRouter>
		);

		expect(container).toMatchSnapshot();
	});
});
