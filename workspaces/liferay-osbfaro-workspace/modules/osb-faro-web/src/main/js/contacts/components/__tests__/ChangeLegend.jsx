import ChangeLegend from '../ChangeLegend';
import React from 'react';
import {CHART_ACTIVITY_ID} from 'shared/util/activities';
import {DEFAULT_ACTIVITY_MAX} from 'shared/api/activities';
import {render} from '@testing-library/react';
import {sub} from 'shared/util/lang';

jest.unmock('react-dom');

describe('ChangeLegend', () => {
	it('should render', () => {
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
						)
					}
				]}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render with a decrease if change is negative', () => {
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
						)
					}
				]}
			/>
		);

		expect(container.querySelector('.decrease')).toBeTruthy();
	});

	it('should render with no icon if change is 0', () => {
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
						)
					}
				]}
			/>
		);

		expect(container.querySelector('.icon-root')).toBeFalsy();
	});

	it('should render with hypens if change is Infinite', () => {
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
						)
					}
				]}
			/>
		);

		expect(getByText('--')).toBeTruthy();
	});
});
