/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import mockStore from '~/test/mock-store';

import BasePage from '../base-page/BasePage';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		groupId: '23',
	}),
}));

const mockBreadcrumbItems = [
	{
		active: true,
		href: 'test123',
		label: 'testLabelBreadcrumbItems',
	},
];

const mockPageActions = [
	{
		actions: [{label: 'Test Action'}],
		label: 'testLabelPageActions ',
	},
];

describe('BasePage', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<BasePage />
				</StaticRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders with a description', () => {
		const {getByText} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<BasePage pageDescription="testPageDescription" />
				</StaticRouter>
			</Provider>
		);

		expect(getByText('testPageDescription')).toBeTruthy();
	});

	it('renders with a breadcrumb', () => {
		const {getByText} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<BasePage breadcrumbItems={mockBreadcrumbItems} />
				</StaticRouter>
			</Provider>
		);

		expect(getByText('testLabelBreadcrumbItems')).toBeTruthy();
	});

	it('renders with a page action', () => {
		const {getByText} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<BasePage groupId="23" pageActions={mockPageActions} />
				</StaticRouter>
			</Provider>
		);

		expect(getByText('testLabelPageActions')).toBeTruthy();
	});

	it('renders with a title', () => {
		const {getByText} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<BasePage groupId="23" pageTitle="testPageTitle" />
				</StaticRouter>
			</Provider>
		);

		expect(getByText('testPageTitle')).toBeTruthy();
	});

	it('renders with an inline title with subtitle', () => {
		const {getByText} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<BasePage
						groupId="23"
						pageTitle="testPageTitle"
						subTitle="mysubtitle"
					/>
				</StaticRouter>
			</Provider>
		);

		expect(getByText('testPageTitle').parentElement).toHaveClass(
			'title-text'
		);
		expect(getByText('mysubtitle').parentElement).toBeTruthy();
	});
});
