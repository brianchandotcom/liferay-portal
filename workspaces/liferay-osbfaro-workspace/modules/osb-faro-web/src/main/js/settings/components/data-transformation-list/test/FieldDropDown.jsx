/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {act, cleanup, render} from '@testing-library/react';
import {Map} from 'immutable';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter} from 'react-router-dom';
import mockStore from '~/test/mock-store';

import FieldDropDown from '../FieldDropDown';

jest.unmock('react-dom');

const DefaultWrapper = ({children}) => (
	<Provider store={mockStore()}>
		<MemoryRouter>
			<MockedProvider>{children}</MockedProvider>
		</MemoryRouter>
	</Provider>
);

const DefaultComponent = (props) => (
	<FieldDropDown
		buttonPlaceholder="Search"
		dataIMap={new Map()}
		inputPlaceholder="Search here"
		searchItems={[]}
		{...props}
	/>
);

describe('FieldDropDown', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<DefaultWrapper>
				<DefaultComponent />
			</DefaultWrapper>
		);

		act(() => {
			jest.advanceTimersByTime(250);
		});

		expect(container).toMatchSnapshot();
	});

	it('renders with a title', () => {
		const {getByText} = render(
			<DefaultWrapper>
				<DefaultComponent title="FOO BAR" />
			</DefaultWrapper>
		);

		act(() => {
			jest.advanceTimersByTime(250);
		});

		expect(getByText('FOO BAR')).toBeTruthy();
	});

	it('renders with data', () => {
		const {getByText} = render(
			<DefaultWrapper>
				<DefaultComponent
					dataIMap={new Map({name: 'foo', value: 450})}
				/>
			</DefaultWrapper>
		);

		act(() => {
			jest.advanceTimersByTime(250);
		});

		expect(getByText('foo')).toBeTruthy();
		expect(getByText('450')).toBeTruthy();
	});
});
