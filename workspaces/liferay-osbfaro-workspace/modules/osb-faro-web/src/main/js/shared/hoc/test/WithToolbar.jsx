/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {compose} from 'redux';
import {withStaticRouter} from '~/test/mock-router';

import withToolbar from '../WithToolbar';

jest.unmock('react-dom');

describe('withToolbar', () => {
	it('renders', () => {
		const WrappedComponent = compose(
			withStaticRouter,
			withToolbar({showRangeDropdownKey: true})
		)(() => <div>foobar</div>);

		const {container} = render(<WrappedComponent />);

		expect(container).toMatchSnapshot();
	});
});
