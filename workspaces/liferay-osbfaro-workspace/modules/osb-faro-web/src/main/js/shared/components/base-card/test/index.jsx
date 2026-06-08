/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter} from 'react-router-dom';
import mockStore from '~/test/mock-store';

import BaseCard from '../index';

jest.unmock('react-dom');

const Wrapper = ({children}) => (
	<Provider store={mockStore()}>
		<MemoryRouter>
			<MockedProvider addTypename={false} freezeResults={false}>
				{children}
			</MockedProvider>
		</MemoryRouter>
	</Provider>
);

describe('BaseCard', () => {
	it('renders component', () => {
		const {container} = render(
			<Wrapper>
				<BaseCard className="my-component-classname" label="My title">
					{() => <div>My body component</div>}
				</BaseCard>
			</Wrapper>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders component with custom Header', () => {
		const Header = () => <div>My custom header component</div>;

		const {getByText} = render(
			<Wrapper>
				<BaseCard Header={Header} label="My title">
					{() => <div>My body component</div>}
				</BaseCard>
			</Wrapper>
		);

		expect(getByText('My body component')).toBeTruthy();
	});

	it('returns the props in Body component', () => {
		let customBodyProps = {};

		render(
			<Wrapper>
				<BaseCard label="My title">
					{(props) => {
						customBodyProps = props;

						return <div>My custom body component</div>;
					}}
				</BaseCard>
			</Wrapper>
		);

		expect(customBodyProps).toEqual(
			expect.objectContaining({
				experienceId: null,
				filters: expect.any(Object),
				interval: expect.any(String),
				onChangeInterval: expect.any(Function),
				rangeSelectors: expect.any(Object),
				router: expect.any(Object),
			})
		);
	});

	it('renders a Card Header with an interval selector', () => {
		const {container, getByText} = render(
			<Wrapper>
				<BaseCard label="My title" showInterval>
					{() => <div>My body component</div>}
				</BaseCard>
			</Wrapper>
		);

		expect(container.querySelector('.interval-selector-root')).toBeTruthy();
		expect(getByText('D')).toBeTruthy();
		expect(getByText('W')).toBeTruthy();
		expect(getByText('M')).toBeTruthy();
	});
});
