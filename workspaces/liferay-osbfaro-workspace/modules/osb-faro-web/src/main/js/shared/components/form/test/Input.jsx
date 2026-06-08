import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import {Field, Form, Formik} from 'formik';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route, Switch} from 'react-router-dom';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import Input from '../Input';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

jest.unmock('react-dom');

const DefaultComponent = (props) => (
	<Formik initialValues={{foo: ''}} onSubmit={jest.fn()}>
		<Form>
			<Field
				component={Input}
				label="Input label"
				mask={[]}
				name="foo"
				{...props}
			/>
		</Form>
	</Formik>
);

describe('Input', () => {
	it('renders', async () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<MemoryRouter>
					<Switch>
						<Route path="*">
							<MockedProvider
								cache={
									new InMemoryCache({
										addTypename: false,
									})
								}
							>
								<Formik
									initialValues={{foo: ''}}
									onSubmit={jest.fn()}
								>
									<Form>
										<Field
											component={Input}
											inline
											name="foo"
										/>
									</Form>
								</Formik>
							</MockedProvider>
						</Route>
					</Switch>
				</MemoryRouter>
			</Provider>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('renders a labelled input', async () => {
		const {container, queryByText} = render(
			<Provider store={mockStore()}>
				<MemoryRouter>
					<Switch>
						<Route path="*">
							<MockedProvider
								cache={
									new InMemoryCache({
										addTypename: false,
									})
								}
							>
								<DefaultComponent />
							</MockedProvider>
						</Route>
					</Switch>
				</MemoryRouter>
			</Provider>
		);

		await waitForLoadingToBeRemoved(container);

		expect(queryByText('Input label')).toBeTruthy();
	});

	it('renders a required input', async () => {
		const {container, queryByText} = render(
			<Provider store={mockStore()}>
				<MemoryRouter>
					<Switch>
						<Route path="*">
							<MockedProvider
								cache={
									new InMemoryCache({
										addTypename: false,
									})
								}
							>
								<DefaultComponent required />
							</MockedProvider>
						</Route>
					</Switch>
				</MemoryRouter>
			</Provider>
		);

		await waitForLoadingToBeRemoved(container);

		expect(queryByText('Input label').closest('label')).toHaveClass(
			'required'
		);
	});
});
