/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';
import React from 'react';
import {Provider} from 'react-redux';
import {addAlert} from '~/shared/actions/alerts';
import mockStore from '~/test/mock-store';

import ContactSalesModal from '../ContactSalesModal';

jest.unmock('react-dom');

describe('ContactSalesModal', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<ContactSalesModal addAlert={addAlert} onClose={noop} />
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
