/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';
import React from 'react';

import OnboardingModal from '../index';

jest.unmock('react-dom');

describe('OnboardingModal', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<OnboardingModal onClose={noop} />);

		expect(container).toMatchSnapshot();
	});
});
