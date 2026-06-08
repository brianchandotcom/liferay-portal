/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import withSelectedPoint from '../WithSelectedPoint';

jest.unmock('react-dom');

describe('withSelectedPoint', () => {
	it('renders the wrapped component', () => {
		const WrappedComponent = withSelectedPoint(() => <div>foo</div>);

		const {container} = render(<WrappedComponent />);

		expect(container).toMatchSnapshot();
	});

	it('passes the selected point to the wrapped component', () => {
		const WrappedComponent = withSelectedPoint(
			({hasSelectedPoint, onPointSelect, selectedPoint}) => (
				<div>
					<span>
						{hasSelectedPoint
							? 'There is a selected point'
							: 'There is no selected point'}
					</span>
					<button onClick={() => onPointSelect({index: 1})}>
						{'bar'}
						{selectedPoint}
					</button>
				</div>
			)
		);

		const {getByText} = render(<WrappedComponent hasSelectedPoint />);

		fireEvent.click(getByText('bar'));

		expect(getByText('bar1')).toBeTruthy();
		expect(getByText('There is a selected point'));
	});
});
