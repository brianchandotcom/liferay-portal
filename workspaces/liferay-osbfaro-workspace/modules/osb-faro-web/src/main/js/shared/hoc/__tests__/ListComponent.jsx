import ListComponent from '../ListComponent';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

describe('ListComponent', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<StaticRouter>
				<ListComponent items={[]} total={0} />
			</StaticRouter>
		);

		expect(container).toMatchSnapshot();
	});
});
