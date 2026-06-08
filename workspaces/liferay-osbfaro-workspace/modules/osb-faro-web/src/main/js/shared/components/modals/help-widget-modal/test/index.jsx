/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {Routes} from '~/shared/util/router';
import mockStore from '~/test/mock-store';

import HelpWidgetModal from '../index';

jest.unmock('react-dom');

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<MemoryRouter initialEntries={['/workspace/23/settings']}>
			<Route path={Routes.SETTINGS}>
				<MockedProvider addTypename={false}>
					<HelpWidgetModal onClose={jest.fn()} {...props} />
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('HelpWidgetModal', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});
});
