/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import mockStore from '~/test/mock-store';

import WorkspacesSuccessDisplay from '../SuccessDisplay';

jest.unmock('react-dom');

describe('WorkspacesSuccessDisplay', () => {
	it('renders', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<WorkspacesSuccessDisplay friendlyURL="/fooFriendlyUrl" />
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
