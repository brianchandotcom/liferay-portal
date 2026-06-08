/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {times} from 'lodash';
import React from 'react';
import {mockIndividual} from '~/test/data';

import ListView from '../ListView';

jest.unmock('react-dom');

const items = times(3, (i) => mockIndividual(i, {total: 123}));

describe('ListView', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<ListView
				itemRenderer={() => 'foo item'}
				items={items}
				onClick={jest.fn()}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders quick actions', () => {
		const {container} = render(
			<ListView
				itemRenderer={() => 'foo item'}
				items={items}
				quickActions={[
					{
						symbol: 'foo',
					},
					{
						symbol: 'bar',
					},
				]}
			/>
		);

		expect(container.querySelectorAll('.quick-action-item').length).toBe(6);
	});
});
