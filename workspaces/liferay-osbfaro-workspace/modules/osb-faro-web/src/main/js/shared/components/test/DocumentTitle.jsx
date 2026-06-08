import {cleanup, render} from '@testing-library/react';
import React from 'react';

import DocumentTitle from '../DocumentTitle';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

jest.unmock('../DocumentTitle');

jest.unmock('react-dom');

describe('DocumentTitle', () => {
	afterEach(cleanup);

	it('changes the document title with analytics cloud appended', () => {
		render(<DocumentTitle title="test" />);

		expect(document.title).toEqual('test - Analytics Cloud');
	});
});
