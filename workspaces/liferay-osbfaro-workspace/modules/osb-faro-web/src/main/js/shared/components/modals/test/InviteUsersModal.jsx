/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import {noop} from 'lodash';
import React from 'react';

import InviteUsersModal from '../InviteUsersModal';

jest.unmock('react-dom');

describe('InviteUsersModal', () => {
	it('renders', () => {
		const {container} = render(<InviteUsersModal onClose={noop} />);

		expect(container).toMatchSnapshot();
	});

	it('renders with custom class', () => {
		const {container} = render(
			<InviteUsersModal className="custom-class" onClose={noop} />
		);

		expect(container.querySelector('.custom-class')).toBeTruthy();
	});
});
