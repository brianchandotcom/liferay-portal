import HelpWidgetModal from '../index';
import mockStore from 'test/mock-store';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/client/testing';
import {Provider} from 'react-redux';
import {Routes} from 'shared/util/router';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<Provider store={mockStore()}>
		<MemoryRouter initialEntries={['/workspace/23/settings']}>
			<Route path={Routes.SETTINGS}>
				<MockedProvider addTypename={false}>
					<HelpWidgetModal onClose={jest.fn()} {...props} />
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('HelpWidgetModal', () => {
	afterEach(cleanup);

	it('Should render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});
});
