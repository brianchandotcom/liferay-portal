/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Cell from '../Cell';

jest.unmock('react-dom');

describe('Cell', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<Cell accessor="name" data={{name: 'Test'}} />
		);
		expect(container).toMatchSnapshot();
	});

	it('renders w/ Custom CellRenderer', () => {
		const email = 'test@test.com';
		const title = 'Test Test';

		const {getByText} = render(
			<Cell
				cellRenderer={({data: {email, title}}) => (
					<td className="custom-cell-renderer">
						<div>{title}</div>
						<div>{email}</div>
					</td>
				)}
				data={{email, title}}
			/>
		);

		expect(getByText(email)).not.toBeNull();
		expect(getByText(title)).not.toBeNull();
	});

	it('renders w/ dataFormatter', () => {
		const {getByText} = render(
			<Cell
				accessor="score"
				data={{score: 10.56}}
				dataFormatter={(val) => Math.round(val)}
			/>
		);

		expect(getByText('11')).not.toBeNull();
	});
});
