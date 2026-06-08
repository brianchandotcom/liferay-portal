/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createEvent} from '@testing-library/dom';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import InputList from '../InputList';

jest.unmock('react-dom');

const createPasteEvent = (html) => {
	const text = html.replace('<[^>]*>', '');

	return {
		clipboardData: {
			getData: (type) => (type === 'text/plain' ? text : html),
			types: ['text/plain', 'text/html'],
		},
	};
};

describe('InputList', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<InputList />);
		expect(container).toMatchSnapshot();
	});

	it('renders with existing items', () => {
		const {container} = render(<InputList items={['one', 'two']} />);
		expect(container).toMatchSnapshot();
	});

	it('renders with an error message', () => {
		const {container} = render(<InputList errorMessage="error" />);

		jest.runAllTimers();
		expect(container).toMatchSnapshot();
	});

	it('renders without an error message if no content', () => {
		const {container} = render(<InputList errorMessage="error" />);

		fireEvent.keyDown(container.querySelector('.form-control-inset'), {
			keyCode: 13,
			preventDefault: () => {},
			target: {value: 'fail'},
		});

		jest.runAllTimers();
		expect(container).toMatchSnapshot();
	});

	it('onlies add item if validation passes', () => {
		const onItemsChange = jest.fn();

		const {container} = render(
			<InputList
				onItemsChange={onItemsChange}
				validationFn={(val) => val === 'pass'}
			/>
		);

		fireEvent.keyDown(container.querySelector('.form-control-inset'), {
			keyCode: 188,
			preventDefault: () => {},
			target: {value: 'fail'},
		});

		expect(onItemsChange).not.toHaveBeenCalled();

		fireEvent.keyDown(container.querySelector('.form-control-inset'), {
			keyCode: 188,
			preventDefault: () => {},
			target: {value: 'pass'},
		});

		expect(onItemsChange).toHaveBeenCalled();
	});

	it('removes item from list', () => {
		const onItemsChange = jest.fn();

		const {container} = render(
			<InputList items={['one']} onItemsChange={onItemsChange} />
		);

		fireEvent.click(container.querySelector('.close'));

		expect(onItemsChange).toHaveBeenCalledWith([]);
	});

	it('renders as disabled', () => {
		const onItemsChange = jest.fn();

		const {container} = render(
			<InputList
				disabled
				items={['one', 'two', 'three']}
				onItemsChange={onItemsChange}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('onlies blur item if validation passes', () => {
		const onItemsChange = jest.fn();

		const {container} = render(
			<InputList
				items={['one']}
				onItemsChange={onItemsChange}
				validateOnBlur={jest.fn()}
				validationFn={(val) => val === 'pass'}
			/>
		);

		fireEvent.blur(container.querySelector('.form-control-inset'), {
			target: {value: 'fail'},
		});

		expect(onItemsChange).not.toHaveBeenCalled();

		fireEvent.blur(container.querySelector('.form-control-inset'), {
			target: {value: 'pass'},
		});

		expect(onItemsChange).toHaveBeenCalled();
	});

	it('pastes a text in inputList', () => {
		const onItemsChange = jest.fn();

		const {container} = render(
			<InputList
				onItemsChange={onItemsChange}
				validationFn={(val) => val === 'pass'}
			/>
		);

		const containerNode = container.querySelector('.form-control-inset');
		const eventProperties = createPasteEvent('test');

		const pasteEvent = createEvent.paste(containerNode, eventProperties);

		fireEvent(containerNode, pasteEvent);

		expect(container).toMatchSnapshot();
	});
});
