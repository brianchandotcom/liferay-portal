/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import getCN from 'classnames';
import React from 'react';

import RowActions from '../RowActions';

jest.unmock('react-dom');

const WrappedComponent = (props) => (
	<td className={getCN(props.className)}>
		<RowActions {...props} />
	</td>
);

describe('RowActions', () => {
	it('renders', () => {
		const {getByRole} = render(
			<WrappedComponent actions={[{label: 'foo'}]} />
		);

		expect(getByRole('button', {name: 'Menu'})).toBeInTheDocument();
	});

	it('renders with quick actions', () => {
		const {container} = render(
			<WrappedComponent
				actions={[{label: 'foo'}]}
				quickActions={[{iconSymbol: 'pencil', label: 'foo'}]}
			/>
		);

		expect(container.querySelector('.quick-action-menu')).toBeTruthy();
	});
});
