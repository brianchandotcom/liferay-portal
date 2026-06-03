import Body from '../Body';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('Modal Body', () => {
	it('should render', () => {
		const {container} = render(<Body />);

		expect(container).toMatchSnapshot();
	});

	it('should render with scroller', () => {
		const {container} = render(<Body inlineScroller />);

		expect(container.querySelector('.inline-scroller')).toBeTruthy();
	});

	it('should render with children', () => {
		const {queryByText} = render(
			<Body>
				<span>{'Body content'}</span>
			</Body>
		);

		expect(queryByText('Body content')).toBeTruthy();
	});
});
