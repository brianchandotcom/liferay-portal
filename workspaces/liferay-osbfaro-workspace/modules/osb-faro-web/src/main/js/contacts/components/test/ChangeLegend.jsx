/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {DEFAULT_ACTIVITY_MAX} from '~/shared/api/activities';
import {CHART_ACTIVITY_ID} from '~/shared/util/activities';
import {sub} from '~/shared/util/lang';

import ChangeLegend from '../ChangeLegend';

jest.unmock('react-dom');

describe('ChangeLegend', () => {
	it('renders', () => {
		const mockActivityCount = 50;

		const {container} = render(
			<ChangeLegend
				items={[
					{
						change: 2,
						id: CHART_ACTIVITY_ID,
						secondaryInfo: sub(
							Liferay.Language.get('x-day-total'),
							[DEFAULT_ACTIVITY_MAX]
						),
						title: sub(
							Liferay.Language.get('total-activity-count-x'),
							[mockActivityCount.toLocaleString()]
						),
					},
				]}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders with a decrease if change is negative', () => {
		const mockActivityCount = 50;

		const {container} = render(
			<ChangeLegend
				items={[
					{
						change: -2,
						id: CHART_ACTIVITY_ID,
						secondaryInfo: sub(
							Liferay.Language.get('x-day-total'),
							[DEFAULT_ACTIVITY_MAX]
						),
						title: sub(
							Liferay.Language.get('total-activity-count-x'),
							[mockActivityCount.toLocaleString()]
						),
					},
				]}
			/>
		);

		expect(container.querySelector('.decrease')).toBeTruthy();
	});

	it('renders with no icon if change is 0', () => {
		const mockActivityCount = 50;

		const {container} = render(
			<ChangeLegend
				items={[
					{
						change: 0,
						id: CHART_ACTIVITY_ID,
						secondaryInfo: sub(
							Liferay.Language.get('x-day-total'),
							[DEFAULT_ACTIVITY_MAX]
						),
						title: sub(
							Liferay.Language.get('total-activity-count-x'),
							[mockActivityCount.toLocaleString()]
						),
					},
				]}
			/>
		);

		expect(container.querySelector('.icon-root')).toBeFalsy();
	});

	it('renders with hypens if change is Infinite', () => {
		const mockActivityCount = 50;

		const {getByText} = render(
			<ChangeLegend
				items={[
					{
						change: Infinity,
						id: CHART_ACTIVITY_ID,
						secondaryInfo: sub(
							Liferay.Language.get('x-day-total'),
							[DEFAULT_ACTIVITY_MAX]
						),
						title: sub(
							Liferay.Language.get('total-activity-count-x'),
							[mockActivityCount.toLocaleString()]
						),
					},
				]}
			/>
		);

		expect(getByText('--')).toBeTruthy();
	});
});
