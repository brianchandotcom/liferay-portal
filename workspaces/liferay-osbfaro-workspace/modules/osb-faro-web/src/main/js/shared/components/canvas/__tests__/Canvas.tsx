import Canvas from '../Canvas';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

const EMPTY_STATE = {
	description: 'Drop something here.',
	title: 'Nothing Here',
};

describe('Canvas', () => {
	afterEach(cleanup);

	it('renders the title as a heading and the actions in the header', () => {
		const {getByRole, getByText} = render(
			<Canvas>
				<Canvas.Header title="Canvas Title">
					<Canvas.Actions>
						<button type="button">{'Action'}</button>
					</Canvas.Actions>
				</Canvas.Header>
			</Canvas>
		);

		expect(
			getByRole('heading', {level: 2, name: 'Canvas Title'})
		).toBeInTheDocument();
		expect(getByText('Action').closest('.canvas-actions')).toBeTruthy();
		expect(getByText('Action').closest('.canvas-header')).toBeTruthy();
	});

	it('renders the body children', () => {
		const {getByText} = render(
			<Canvas>
				<Canvas.Body>
					<span>{'Content'}</span>
				</Canvas.Body>
			</Canvas>
		);

		expect(getByText('Content').closest('.canvas-body')).toBeTruthy();
	});

	it('renders the empty state as a body child', () => {
		const {getByRole, getByText} = render(
			<Canvas>
				<Canvas.Body>
					<Canvas.EmptyState {...EMPTY_STATE} />
				</Canvas.Body>
			</Canvas>
		);

		expect(
			getByRole('heading', {level: 3, name: EMPTY_STATE.title})
		).toBeInTheDocument();
		expect(getByText(EMPTY_STATE.description)).toBeInTheDocument();
	});

	it('renders the root and the slots as card parts', () => {
		const {container, getByTestId} = render(
			<Canvas className="custom-canvas" testId="canvas">
				<Canvas.Header className="custom-header" title="Canvas Title" />

				<Canvas.Body className="custom-body" />
			</Canvas>
		);

		expect(getByTestId('canvas')).toHaveClass(
			'canvas-root',
			'card',
			'custom-canvas'
		);
		expect(container.querySelector('.canvas-header')).toHaveClass(
			'card-header',
			'custom-header'
		);
		expect(container.querySelector('.canvas-body')).toHaveClass(
			'card-body',
			'custom-body'
		);
	});
});

describe('Canvas.EmptyState', () => {
	afterEach(cleanup);

	it('renders the illustration with its reduced motion variant', () => {
		const {container} = render(<Canvas.EmptyState {...EMPTY_STATE} />);

		const images = container.querySelectorAll('img');

		expect(images).toHaveLength(2);
		expect(images[0]).toHaveAttribute('alt', '');
		expect(images[0]).toHaveAttribute('src', 'empty_state.svg');
		expect(images[1]).toHaveAttribute(
			'src',
			'empty_state_reduced_motion.svg'
		);
	});

	it('shows the content while nothing is dragged', () => {
		const {container} = render(<Canvas.EmptyState {...EMPTY_STATE} />);

		expect(
			container.querySelector('.canvas-empty-state-idle')
		).toBeInTheDocument();
		expect(container.querySelector('.c-empty-state')).not.toHaveClass(
			'invisible'
		);
	});

	it.each(['dragging', 'over'] as const)(
		'hides the content in the %s drop state',
		(dropState) => {
			const {container} = render(
				<Canvas.EmptyState {...EMPTY_STATE} dropState={dropState} />
			);

			expect(
				container.querySelector(`.canvas-empty-state-${dropState}`)
			).toBeInTheDocument();
			expect(container.querySelector('.c-empty-state')).toHaveClass(
				'invisible'
			);
		}
	);
});
