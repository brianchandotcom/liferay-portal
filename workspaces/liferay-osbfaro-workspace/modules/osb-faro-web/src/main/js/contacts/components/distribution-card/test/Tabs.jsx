/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {List} from 'immutable';
import React from 'react';
import {DistributionTab} from '~/shared/util/records';

import Tabs from '../Tabs';

jest.unmock('react-dom');

describe('DistributionCard Tabs', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<Tabs
				itemsIList={
					new List([new DistributionTab({id: '123', title: 'Tab 1'})])
				}
				selectedTabIndex={0}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders w/ Add button as selected', () => {
		const {container} = render(
			<Tabs
				itemsIList={
					new List([new DistributionTab({id: '123', title: 'Tab 1'})])
				}
				selectedTabIndex={0}
				showAddProperty
			/>
		);

		expect(container.querySelector('.add-tab')).toHaveClass('active');
	});
});
