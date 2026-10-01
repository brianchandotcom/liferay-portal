import ConditionChip from '../ConditionChip';
import React from 'react';
import {fireEvent, render, screen} from '@testing-library/react';

jest.unmock('react-dom');

const defaultProps = {
	icon: 'text',
	label: 'contains "manager"',
	name: 'Job Title',
	onRemove: jest.fn(),
	overline: 'Individual | Job Title'
};

describe('ConditionChip', () => {
	it('renders the overline and the label inside the edit button', () => {
		render(<ConditionChip {...defaultProps} />);

		const editButton = screen.getByRole('button', {
			name: /individual.*job title.*manager/i
		});

		expect(editButton).toHaveTextContent(/contains "manager"/i);
		expect(editButton.querySelector('.text-uppercase')).toHaveTextContent(
			/individual/i
		);
	});

	it('wraps long labels instead of truncating them', () => {
		const {container} = render(
			<ConditionChip {...defaultProps} label={'a'.repeat(200)} />
		);

		expect(container.querySelector('.text-break')).toBeInTheDocument();
		expect(container.querySelector('.text-truncate')).toBeNull();
	});

	it('removes the condition from a labeled remove button', () => {
		const onRemove = jest.fn();

		render(<ConditionChip {...defaultProps} onRemove={onRemove} />);

		fireEvent.click(screen.getByRole('button', {name: /remove.job title/i}));

		expect(onRemove).toHaveBeenCalledTimes(1);
	});

	it('passes the dropdown trigger props to the edit button', () => {
		const onClick = jest.fn();
		const onKeyDown = jest.fn();

		render(
			<ConditionChip
				{...defaultProps}
				aria-expanded={false}
				aria-haspopup="true"
				onClick={onClick}
				onKeyDown={onKeyDown}
			/>
		);

		const editButton = screen.getByRole('button', {name: /manager/i});

		fireEvent.click(editButton);
		fireEvent.keyDown(editButton, {key: 'ArrowDown'});

		expect(editButton).toHaveAttribute('aria-haspopup', 'true');
		expect(onClick).toHaveBeenCalledTimes(1);
		expect(onKeyDown).toHaveBeenCalledTimes(1);
	});

	it('renders the drag handle only when one is given', () => {
		const {container, rerender} = render(
			<ConditionChip {...defaultProps} />
		);

		expect(container.querySelector('.drag-handle')).toBeNull();

		rerender(
			<ConditionChip
				{...defaultProps}
				handle={<span className="drag-handle" />}
			/>
		);

		expect(container.querySelector('.drag-handle')).toBeInTheDocument();
	});
});
