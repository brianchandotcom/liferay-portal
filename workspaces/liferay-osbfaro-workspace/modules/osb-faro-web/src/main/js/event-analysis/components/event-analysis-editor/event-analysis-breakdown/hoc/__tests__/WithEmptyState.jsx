import React from 'react';
import withEmptyState from '../WithEmptyState';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('withEmptyState', () => {
	it('should render with an empty state', () => {
		const WrappedComponent = withEmptyState(() => 'test');

		const {container} = render(<WrappedComponent />);

		expect(container.querySelector('.breakdown-empty')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});

	it('should return the passed component', () => {
		const componentSpy = jest.fn(() => 'test');

		const WrappedComponent = withEmptyState(componentSpy);

		render(<WrappedComponent event={{name: 'Test Event'}} />);

		expect(componentSpy).toHaveBeenCalled();
	});
});
