/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import {noop} from 'lodash';
import React from 'react';

import IssueSubmitted from '../IssueSubmitted';

jest.unmock('react-dom');

describe('IssueSubmitted', () => {
	it('renders', () => {
		const {container} = render(
			<IssueSubmitted onClose={noop} onNext={noop} />
		);

		expect(container).toMatchSnapshot();
	});
});
