import mockStore from 'test/mock-store';
import React from 'react';
import {BasePage} from '../index';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

describe('BasePage', () => {
	it('renders BasePage', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<BasePage documentTitle='Test title'>
						{'Test test'}
					</BasePage>
				</StaticRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
