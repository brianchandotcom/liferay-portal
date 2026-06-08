/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import * as data from '~/test/data';

import CreatedByCell from '../CreatedBy';

jest.unmock('react-dom');

const tableRow = document.createElement('tr');

describe('CreatedByCell', () => {
	it('renders', () => {
		const {container} = render(
			<CreatedByCell
				data={{
					dateModified: data.getTimestamp(),
					userName: 'Test Test',
				}}
			/>,
			{container: document.body.appendChild(tableRow)}
		);

		expect(container).toMatchSnapshot();
	});
});
