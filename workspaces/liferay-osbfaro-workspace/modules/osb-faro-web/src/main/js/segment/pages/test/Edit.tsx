/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';
import {SegmentTypes} from '~/shared/util/constants';

import Edit from '../Edit';

jest.unmock('react-dom');

jest.mock('../edit/Dynamic', () => ({
	__esModule: true,
	default: ({type}: {type: string}) => (
		<div data-testid="dynamic-segment" data-type={type} />
	),
}));

describe('Edit', () => {
	it('renders', () => {
		render(<Edit groupId="23" />);

		expect(screen.getByTestId('dynamic-segment')).toBeInTheDocument();
	});

	it('renders a dynamic segment', () => {
		render(<Edit groupId="23" type={SegmentTypes.Batch} />);

		const dynamicSegment = screen.getByTestId('dynamic-segment');

		expect(dynamicSegment).toBeInTheDocument();
		expect(dynamicSegment).toHaveAttribute('data-type', SegmentTypes.Batch);
	});
});
