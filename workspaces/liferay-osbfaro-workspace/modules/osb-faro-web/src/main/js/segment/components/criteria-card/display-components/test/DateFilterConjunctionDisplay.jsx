/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {
	FunctionalOperators,
	RelationalOperators,
	TimeSpans,
} from '~/segment/segment-editor/dynamic/utils/constants';

import DateFilterConjunctionDisplay from '../DateFilterConjunctionDisplay';

jest.unmock('react-dom');

describe('DateFilterConjunctionDisplay', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<DateFilterConjunctionDisplay
				conjunctionCriterion={{
					operatorName: RelationalOperators.EQ,
					propertyName: 'date',
					touched: false,
					valid: false,
					value: '2020-12-12',
				}}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders w/ between', () => {
		const {getByText} = render(
			<DateFilterConjunctionDisplay
				conjunctionCriterion={{
					operatorName: FunctionalOperators.Between,
					propertyName: 'date',
					touched: false,
					valid: false,
					value: {end: '2020-12-12', start: '2020-12-01'},
				}}
			/>
		);

		expect(getByText('between')).toBeTruthy();
	});

	it('renders w/ ever', () => {
		const {getByText} = render(
			<DateFilterConjunctionDisplay
				conjunctionCriterion={{
					propertyName: 'date',
				}}
			/>
		);

		expect(getByText('ever')).toBeTruthy();
	});

	it('renders w/ since', () => {
		const {getByText} = render(
			<DateFilterConjunctionDisplay
				conjunctionCriterion={{
					operatorName: RelationalOperators.GT,
					propertyName: 'date',
					touched: false,
					valid: false,
					value: TimeSpans.Last90Days,
				}}
			/>
		);

		expect(getByText('since')).toBeTruthy();
	});
});
