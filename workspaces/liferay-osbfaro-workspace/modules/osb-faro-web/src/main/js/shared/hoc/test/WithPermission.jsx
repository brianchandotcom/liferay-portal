import {render} from '@testing-library/react';
import React from 'react';
import {compose} from 'redux';
import FaroConstants from '~/shared/util/constants';
import {User} from '~/shared/util/records';
import * as data from '~/test/data';
import {withStaticRouter} from '~/test/mock-router';

import withPermission, {withAdminPermission} from '../WithPermission';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

jest.mock(
	'~/shared/hoc/WithAction',
	() => () => (wrappedComponent) => wrappedComponent
);

const {userRoleNames} = FaroConstants;

const mockUser = data.getImmutableMock(User, data.mockUser);

jest.unmock('react-dom');

jest.mock('~/shared/hooks/useCurrentUser', () => ({
	useCurrentUser: () => mockUser,
}));

describe('withPermission', () => {
	it('renders an error page', () => {
		const Component = compose(
			withStaticRouter,
			withPermission(['foo'])
		)(() => <h1>Foobar</h1>);

		const {container} = render(<Component />);

		expect(container).toMatchSnapshot();
	});

	it('renders the wrapped component', () => {
		const Component = withPermission([userRoleNames.owner])(() => (
			<h1>Foobar</h1>
		));

		const {container} = render(<Component />);

		expect(container).toMatchSnapshot();
	});
});

describe('withAdminPermission', () => {
	it('renders the wrapped component', () => {
		const Component = withAdminPermission(() => <h1>Foobar</h1>);

		const {container} = render(<Component />);

		expect(container).toMatchSnapshot();
	});
});
