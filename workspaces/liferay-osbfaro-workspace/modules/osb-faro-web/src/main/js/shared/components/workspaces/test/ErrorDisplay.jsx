/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {BrowserRouter} from 'react-router-dom';
import {User} from '~/shared/util/records';
import mockStore from '~/test/mock-store';

import WorkspacesErrorDisplay from '../ErrorDisplay';

jest.unmock('react-dom');

describe('WorkspacesErrorDisplay', () => {
	it('renders', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<WorkspacesErrorDisplay
						currentUser={
							new User({emailAddress: 'test@liferay.com'})
						}
					/>
				</BrowserRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
