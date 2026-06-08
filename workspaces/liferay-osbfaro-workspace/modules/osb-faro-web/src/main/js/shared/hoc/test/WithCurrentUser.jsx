/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import withCurrentUser from '../WithCurrentUser';

jest.unmock('react-dom');

jest.mock('~/shared/hooks/useCurrentUser', () => ({
	useCurrentUser: () => ({name: 'fooUser'}),
}));

const MockComponent = ({className, currentUser}) => (
	<div className={className}>{currentUser.name}</div>
);

describe('WithCurrentUser', () => {
	it('passes the currentUser to the WrappedComponent', () => {
		const WrappedComponent = withCurrentUser(MockComponent);

		const {getByText} = render(<WrappedComponent className="my-class" />);

		expect(getByText('fooUser')).toBeInTheDocument();
		expect(getByText('fooUser')).toHaveClass('my-class');
	});
});
