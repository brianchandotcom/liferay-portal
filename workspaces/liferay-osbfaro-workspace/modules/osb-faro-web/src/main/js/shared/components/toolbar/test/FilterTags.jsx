/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import {range} from 'lodash';
import React from 'react';

import FilterTags from '../FilterTags';

jest.unmock('react-dom');

describe('FilterTags', () => {
	it('renders a list of tags', () => {
		const {container} = render(
			<FilterTags
				tags={range(3).map((i) => ({
					key: `foo${i}`,
					label: `Foo Label${i}`,
					value: `foo-value${i}`,
				}))}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
