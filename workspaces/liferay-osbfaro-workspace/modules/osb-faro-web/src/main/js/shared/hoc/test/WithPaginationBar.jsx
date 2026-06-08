/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {compose} from 'redux';
import {withStaticRouter} from '~/test/mock-router';

import withPaginationBar from '../WithPaginationBar';

jest.unmock('react-dom');

describe('withPaginationBar', () => {
	it('renders', () => {
		const WrappedComponent = compose(
			withStaticRouter,
			withPaginationBar({defaultDelta: 10})
		)(() => <div>foobar</div>);

		const {container} = render(
			<WrappedComponent delta={5} page={1} total={15} />
		);

		expect(container).toMatchSnapshot();
	});

	it('renders w/o the pagination bar', () => {
		const WrappedComponent = compose(
			withStaticRouter,
			withPaginationBar({defaultDelta: 10})
		)(() => <div>foobar</div>);

		const {container} = render(<WrappedComponent total={0} />);

		expect(container).toMatchSnapshot();
	});
});
