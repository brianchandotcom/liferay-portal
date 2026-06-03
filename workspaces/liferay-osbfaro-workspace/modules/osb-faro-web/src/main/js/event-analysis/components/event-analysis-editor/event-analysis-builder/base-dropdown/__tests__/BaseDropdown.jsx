import BaseDropdown from '../index';
import mockStore from 'test/mock-store';
import React from 'react';
import {act, fireEvent, render} from '@testing-library/react';
import {MemoryRouter} from 'react-router-dom';
import {MockedProvider} from '@apollo/client/testing';
import {Provider} from 'react-redux';

jest.unmock('react-dom');

const DefaultComponent = props => (
	<Provider store={mockStore()}>
		<MemoryRouter>
			<MockedProvider freezeResults={false}>
				<BaseDropdown
					onActiveChange={jest.fn()}
					trigger={<button data-testid='target'>{'click me'}</button>}
					{...props}
				>
					{() => <div>{'Child contents'}</div>}
				</BaseDropdown>
			</MockedProvider>
		</MemoryRouter>
	</Provider>
);

describe('BaseDropdown', () => {
	it('should render', () => {
		const {container, getByTestId} = render(<DefaultComponent />);

		fireEvent.click(getByTestId('target'));

		act(() => {
			jest.advanceTimersByTime(250);
		});

		expect(container).toMatchSnapshot();

		const dropdownMenu = document.body.getElementsByClassName(
			'event-analysis-dropdown-menu-root'
		)[0];

		expect(dropdownMenu).toMatchSnapshot();
	});
});
