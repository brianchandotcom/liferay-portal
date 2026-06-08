/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';

import ContextualInformation from '../ContextualInformation';

jest.unmock('react-dom');

describe('ContextualInformation', () => {
	const mockContext = {
		browserName: 'Chrome',
		city: 'Sidney',
		country: 'Australia',
		deviceType: 'Desktop',
		region: 'AEST',
		timezoneOffset: '-03:00',
	};

	it('renders the snapshot', () => {
		const {container} = render(
			<ContextualInformation
				contactId="contact-1"
				contextData={fromJS(mockContext)}
				email="test@example.com"
				userId="123456"
				uuid="12345"
			/>
		);
		expect(container).toMatchSnapshot();
	});

	it('renders the empty state when showEmptyState is true', () => {
		const {getByText, queryByText} = render(
			<ContextualInformation
				contextData={fromJS(mockContext)}
				showEmptyState
			>
				<div>empty state rendered</div>
			</ContextualInformation>
		);

		expect(getByText('empty state rendered')).toBeTruthy();
		expect(queryByText('browser')).toBeNull();
	});

	it('correctlies format the time zone offset string', () => {
		const {getByText} = render(
			<ContextualInformation contextData={fromJS(mockContext)} />
		);

		expect(getByText('UTC -03:00 (AEST)')).toBeTruthy();
	});

	it('shows email and uuid when passed as props', () => {
		const {getByText} = render(
			<ContextualInformation
				contextData={fromJS({})}
				email="test@example.com"
				uuid="1234-abcde-67890"
			/>
		);

		expect(getByText('test@example.com')).toBeTruthy();
		expect(getByText('1234-abcde-67890')).toBeTruthy();
	});

	it('displays the fallback dash for missing context values', () => {
		const {getAllByText} = render(
			<ContextualInformation contextData={fromJS({})} />
		);

		const dashes = getAllByText('-');
		expect(dashes.length).toBeGreaterThan(0);
	});
});
