/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {SegmentStates, SegmentTypes} from '~/shared/util/constants';

import SegmentSticker from '../SegmentSticker';

jest.unmock('react-dom');

describe('SegmentSticker', () => {
	it('renders', () => {
		const {container} = render(
			<SegmentSticker segmentType={SegmentTypes.Batch} />
		);
		expect(container).toMatchSnapshot();
	});

	it('renders with a dynamic segment icon', () => {
		const {container} = render(
			<SegmentSticker segmentType={SegmentTypes.Batch} />
		);

		expect(container.querySelector('use')).toHaveAttribute(
			'href',
			'#individual_dynamic_segment'
		);
	});

	it('renders with a disabled segment icon', () => {
		const {container} = render(
			<SegmentSticker state={SegmentStates.Disabled} />
		);

		expect(container.querySelector('use')).toHaveAttribute(
			'href',
			'#warning'
		);
	});
});
