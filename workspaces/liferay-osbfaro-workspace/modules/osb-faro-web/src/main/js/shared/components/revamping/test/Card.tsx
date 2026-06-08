/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render, screen} from '@testing-library/react';
import React from 'react';

import {Card} from '../Card';

jest.unmock('react-dom');

describe('Card', () => {
	afterEach(cleanup);

	it('renders', () => {
		render(<Card title="Card Title">Card Content</Card>);

		expect(screen.getByText('Card Title')).toBeInTheDocument();
		expect(screen.getByText('Card Content')).toBeInTheDocument();
	});

	it('renders Card.SubHeader', () => {
		render(
			<Card title="Card Title">
				<Card.SubHeader title="Card SubHeader Title" />
				Card Content
			</Card>
		);

		expect(screen.getByText('Card Title')).toBeInTheDocument();
		expect(screen.getByText('CARD SUBHEADER TITLE')).toBeInTheDocument();
		expect(screen.getByText('Card Content')).toBeInTheDocument();
	});
});
