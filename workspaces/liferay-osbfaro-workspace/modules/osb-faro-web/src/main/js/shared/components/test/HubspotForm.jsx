/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import HubspotForm from '../HubspotForm';

jest.unmock('react-dom');

describe('HubspotForm', () => {
	afterEach(cleanup);

	it('renders a hubspot div with id', () => {
		const {container} = render(<HubspotForm />);

		expect(container).toMatchSnapshot();
	});

	it('appends the hubspot script in the head of the document', () => {
		render(<HubspotForm />);

		expect(document.head.innerHTML).toContain(
			'<script src="//js.hsforms.net/forms/v2.js"></script>'
		);
	});
});
