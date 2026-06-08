/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import RadioGroup from '../RadioGroup';

jest.unmock('react-dom');

describe('RadioGroup.Option', () => {
	afterEach(cleanup);

	it('renders Option', () => {
		const {container} = render(<RadioGroup.Option />);
		expect(container).toMatchSnapshot();
	});

	it('renders Option with value', () => {
		const {container} = render(<RadioGroup.Option value={4} />);
		expect(container).toMatchSnapshot();
	});

	it('renders Option with label', () => {
		const {queryByText} = render(<RadioGroup.Option label="foo bar" />);
		expect(queryByText('foo bar')).toBeTruthy();
	});

	it('calls `onClick` prop with value', () => {
		const onClick = jest.fn();
		const value = 'foo';
		const {getByLabelText} = render(
			<RadioGroup.Option label={value} onClick={onClick} value={value} />
		);
		fireEvent.click(getByLabelText('foo'));
		expect(onClick).toBeCalled();
	});
});

class RadioGroupExample extends React.Component {
	render() {
		return (
			<RadioGroup {...this.props} name="foo">
				<RadioGroup.Option label="1" value={1} />
				<RadioGroup.Option label="2" value={2} />
				<RadioGroup.Option label="3" value={3} />
			</RadioGroup>
		);
	}
}

describe('RadioGroup', () => {
	it('renders RadioGroup', () => {
		const {container} = render(<RadioGroup />);
		expect(container).toMatchSnapshot();
	});

	it('renders RadioGroup with children', () => {
		const {container} = render(<RadioGroupExample />);
		expect(container).toMatchSnapshot();
	});

	it('renders RadioGroup with a selected value', () => {
		const {container} = render(<RadioGroupExample checked={1} />);
		expect(container).toMatchSnapshot();
	});

	it('renders RadioGroup as disabled', () => {
		const {container} = render(<RadioGroupExample disabled />);
		expect(container.querySelector('.disabled')).toBeTruthy();
	});
});
