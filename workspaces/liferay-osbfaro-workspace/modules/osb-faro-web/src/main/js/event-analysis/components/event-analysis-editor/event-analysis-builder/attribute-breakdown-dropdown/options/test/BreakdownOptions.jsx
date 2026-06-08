/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {withAttributesProvider} from '~/event-analysis/components/event-analysis-editor/context/attributes';

import FilterOptions from '../index';

jest.unmock('react-dom');

describe('FilterOptions', () => {
	it('renders', () => {
		const WrappedFilterOptions = withAttributesProvider(FilterOptions);

		const {container} = render(
			<WrappedFilterOptions
				attribute={{
					dataType: 'DATE',
					displayName: 'Filed Ticket',
					id: '4',
					name: 'filedTicket',
				}}
				onActiveChange={jest.fn()}
				onAttributeChange={jest.fn()}
				onEditClick={jest.fn()}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
