/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';
import {Routes, toRoute} from '~/shared/util/router';

import redirectIf from '../RedirectIf';

jest.unmock('react-dom');

const WrapperComponent = ({route = null}) => {
	const Component = redirectIf(() => route)(() => (
		<div className="my-component">component body</div>
	));

	return (
		<StaticRouter>
			<Component />
		</StaticRouter>
	);
};

describe('redirectIf', () => {
	it('renders the component', () => {
		const {container} = render(<WrapperComponent />);

		expect(container).toMatchSnapshot();
	});

	it('redirects if pass a route', () => {
		const expectedRoute = toRoute(Routes.WORKSPACES);
		const {container} = render(<WrapperComponent route={expectedRoute} />);

		expect(
			container.querySelector('.my-component')
		).not.toBeInTheDocument();
	});

	it('renders the passed component if dont pass a route', () => {
		const {container} = render(<WrapperComponent />);

		expect(container.querySelector('.my-component')).toBeInTheDocument();
	});
});
