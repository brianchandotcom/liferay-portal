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

	it('renders the body children when it is not empty', () => {
		const {getByText, queryByText} = render(
			<Canvas>
				<Canvas.Body emptyState={EMPTY_STATE}>
					<span>{'Content'}</span>
				</Canvas.Body>
			</Canvas>
		);

		expect(getByText('Content')).toBeInTheDocument();
		expect(queryByText(EMPTY_STATE.title)).not.toBeInTheDocument();
	});

	it('renders the empty state instead of the children when it is empty', () => {
		const {getByRole, getByText, queryByText} = render(
			<Canvas>
				<Canvas.Body empty emptyState={EMPTY_STATE}>
					<span>{'Content'}</span>
				</Canvas.Body>
			</Canvas>
		);

		expect(queryByText('Content')).not.toBeInTheDocument();
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
