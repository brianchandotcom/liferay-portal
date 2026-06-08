/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import mockStore from '~/test/mock-store';

import TimeZoneAlert from '../TimeZoneAlert';

jest.unmock('react-dom');

jest.mock('~/shared/hooks/useTimeZone', () => ({
	useTimeZone: () => ({
		displayTimeZone: 'UTC -03:00 Brasilia Time (America/Recife)',
		timeZoneId: 'UTC',
	}),
}));

describe('TimeZoneAlert', () => {
	it('renders', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<TimeZoneAlert groupId="23" onClose={jest.fn()} />
				</StaticRouter>
			</Provider>
		);
		expect(container).toMatchSnapshot();
	});
});
