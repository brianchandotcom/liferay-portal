/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {List} from 'immutable';
import React from 'react';
import {User} from '~/shared/util/records';
import * as data from '~/test/data';

import {CONTEXT_OPTIONS, Distribution} from '../Distribution';

jest.unmock('react-dom');

const defaultProps = {
	currentUser: new User(data.mockUser()),
	distributionsKey: 'test',
	fieldDistributionIList: new List(),
	fieldMappingFieldName: 'test',
	groupId: '23',
	id: 'test',
	loading: true,
	numberOfBins: 10,
};

describe('SegmentDistribution', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {getByText} = render(<Distribution {...defaultProps} />);

		expect(
			getByText(Liferay.Language.get('distribution-by-attribute'))
		).toBeInTheDocument();
	});

	xit('renders a Chart component if loading is false', () => {
		const {container} = render(
			<Distribution
				{...defaultProps}
				fieldDistributionIList={
					new List([{count: 3, values: ['stuff']}])
				}
				loading={false}
			/>
		);

		expect(
			container.querySelector('.recharts-responsive-container')
		).toBeTruthy();
	});

	it('does not render a dropdown of context items if contextOptions is less than 2', () => {
		const {container, queryByText} = render(
			<Distribution
				{...defaultProps}
				contextOptions={[CONTEXT_OPTIONS[0]]}
			/>
		);

		expect(container.querySelector('.context-select')).toBeNull();
		expect(queryByText('by')).toBeNull();
	});
});
