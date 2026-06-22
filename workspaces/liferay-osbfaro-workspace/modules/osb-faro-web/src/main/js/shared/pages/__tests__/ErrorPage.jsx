import ErrorPage from '../ErrorPage';
import React from 'react';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<StaticRouter>
		<ErrorPage {...props} />
	</StaticRouter>
);

describe('ErrorPage', () => {
	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('should render a custom message', () => {
		const {getByText} = render(<DefaultComponent message='foo bar' />);

		expect(getByText('foo bar')).toBeTruthy();
	});
});
