/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import CollapsibleOverlay from '../CollapsibleOverlay';

jest.unmock('react-dom');

describe('CollapsibleOverlay', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<CollapsibleOverlay title="foo title" />);
		expect(container).toMatchSnapshot();
	});

	it('renders as not visible', () => {
		const {container} = render(<CollapsibleOverlay visible={false} />);

		expect(
			container.querySelector('.collapsible-overlay-root')
		).not.toBeVisible();
	});

	it('calls onClose when the close button is clicked', () => {
		const onCloseSpy = jest.fn();
		const {getByLabelText} = render(
			<CollapsibleOverlay onClose={onCloseSpy} visible />
		);

		fireEvent.click(getByLabelText('Close'));

		expect(onCloseSpy).toHaveBeenCalled();
	});
});
