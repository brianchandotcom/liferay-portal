/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import FormNavigation from '../FormNavigation';

jest.unmock('react-dom');

describe('FormNavigation', () => {
	it('renders', () => {
		const {container} = render(<FormNavigation cancelHref="" />);

		expect(container).toMatchSnapshot();
	});

	it('renders with a custom submit text', () => {
		const submitMessage = 'Submit me';

		const {getByText} = render(
			<FormNavigation submitMessage={submitMessage} />
		);

		expect(getByText(submitMessage)).toBeTruthy();
	});

	it('renders with a previous button', () => {
		const {getByText} = render(
			<FormNavigation onPreviousStep={() => jest.fn()} />
		);

		expect(getByText('Previous')).toBeTruthy();
	});
});
