import React, {useContext} from 'react';
import {act, cleanup, fireEvent, render, screen} from '@testing-library/react';
import {Conjunctions, PropertyTypes} from '../../utils/constants';
import {
	KeyboardMovementContext,
	KeyboardMovementProvider
} from '../keyboardMovement';

jest.unmock('react-dom');

const source = {
	criterion: {
		defaultValue: 'default',
		propertyName: 'download',
		type: PropertyTypes.Event
	},
	property: {label: 'Download'}
};

const criteria = {
	conjunctionName: Conjunctions.And,
	criteriaGroupId: 'root',
	items: [{propertyName: 'click', rowId: 'row_a', type: PropertyTypes.Event}]
};

let movement;

const Probe = () => {
	movement = useContext(KeyboardMovementContext);

	return null;
};

const renderProvider = (props = {}) => {
	const onChange = jest.fn();

	render(
		<KeyboardMovementProvider
			criteria={criteria}
			onChange={onChange}
			sequential={false}
			{...props}
		>
			<Probe />
		</KeyboardMovementProvider>
	);

	return onChange;
};

const getAnnouncement = () =>
	document.querySelector('[aria-live="assertive"]').textContent;

describe('KeyboardMovementProvider', () => {
	afterEach(cleanup);

	it('adds the criterion right away when the canvas is empty', () => {
		const onChange = renderProvider({criteria: null});

		act(() => movement.startMovement(source));

		expect(onChange).toHaveBeenCalledTimes(1);
		expect(onChange.mock.calls[0][0].items[0].propertyName).toBe(
			'download'
		);
		expect(movement.source).toBeNull();
		expect(getAnnouncement()).toBe(
			'Download was added to the segment criteria.'
		);
	});

	it('targets the last position and explains how to move', () => {
		renderProvider();

		act(() => movement.startMovement(source));

		expect(movement.source).toBe(source);
		expect(movement.target).toMatchObject({
			groupId: 'root',
			index: 1,
			position: 'bottom'
		});
		expect(getAnnouncement()).toBe(
			'Use the arrow keys to choose where to add Download, and press Enter to confirm or Esc to cancel.'
		);
	});

	it('moves through the targets and drops the criterion with Enter', () => {
		const onChange = renderProvider();

		act(() => movement.startMovement(source));

		fireEvent.keyDown(window, {key: 'Home'});

		expect(movement.target).toMatchObject({index: 0, position: 'top'});
		expect(getAnnouncement()).toBe('Targeting Top of click');

		fireEvent.keyDown(window, {key: 'ArrowDown'});

		expect(movement.target).toMatchObject({index: 0, position: 'middle'});

		fireEvent.keyDown(window, {key: 'ArrowUp'});
		fireEvent.keyDown(window, {key: 'Enter'});

		expect(
			onChange.mock.calls[0][0].items.map(({propertyName}) => propertyName)
		).toEqual(['download', 'click']);
		expect(movement.source).toBeNull();
		expect(movement.target).toBeNull();
	});

	it('cancels the movement with Escape', () => {
		const onChange = renderProvider();

		act(() => movement.startMovement(source));

		fireEvent.keyDown(window, {key: 'Escape'});

		expect(onChange).not.toHaveBeenCalled();
		expect(movement.source).toBeNull();
		expect(screen.queryByText(/Targeting/)).toBeNull();
	});
});
