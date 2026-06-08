/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import MetadataTag from '../MetadataTag';

jest.unmock('react-dom');

describe('MetadataTag', () => {
	it('renders', () => {
		const {container} = render(<MetadataTag value="og:url" />);

		expect(container).toMatchSnapshot();
	});
});
