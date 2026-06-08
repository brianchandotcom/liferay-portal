import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import {Field, Form, Formik} from 'formik';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route, Switch} from 'react-router-dom';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import Select from '../Select';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

jest.unmock('react-dom');

describe('Select', () => {
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
									initialValues={{test: 'red'}}
									onSubmit={jest.fn()}
								>
									<Form>
										<Field component={Select} name="test">
											<Select.Item value="red">
												red
											</Select.Item>
										</Field>
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

	it('renders w/ label', async () => {
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
									initialValues={{test: ''}}
									onSubmit={jest.fn()}
								>
									<Form>
										<Field
											component={Select}
											label="foo"
											name="test"
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

		expect(container.querySelector('label')).not.toBeNull();
	});

	it('renders w/ error', async () => {
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
									initialErrors={{
										test: 'can not be test',
									}}
									initialTouched={{test: true}}
									initialValues={{test: 'red'}}
									onSubmit={jest.fn()}
								>
									<Form>
										<Field component={Select} name="test">
											<Select.Item value="red">
												red
											</Select.Item>
										</Field>
									</Form>
								</Formik>
							</MockedProvider>
						</Route>
					</Switch>
				</MemoryRouter>
			</Provider>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container.querySelector('.has-error')).not.toBeNull();
	});
});
