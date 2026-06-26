import ActivitiesChart from '../ActivitiesChart';
import React from 'react';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

jest.mock('recharts', () => {
	const OriginalModule = jest.requireActual('recharts');

	return {
		...OriginalModule,
		ResponsiveContainer: ({children}: {children: React.ReactNode}) => (
			<OriginalModule.ResponsiveContainer height={350} width={800}>
				{children}
			</OriginalModule.ResponsiveContainer>
		),
	};
});

const history = [
	{intervalInitDate: 1717200000000, totalEvents: 3, totalSessions: 2},
	{intervalInitDate: 1717286400000, totalEvents: 5, totalSessions: 4},
];

const renderChart = (selectedPoint?: number) =>
	render(
		<ActivitiesChart
			alwaysShowSelectedTooltip={false}
			history={history}
			interval="D"
			onPointSelect={() => {}}
			rangeSelectors={{
				rangeEnd: null,
				rangeKey: RangeKeyTimeRanges.Last30Days,
				rangeStart: null,
			}}
			selectedPoint={selectedPoint}
		/>
	);

describe('ActivitiesChart', () => {
	it('does not crash when the selected point index is out of bounds for the current history', () => {
		expect(() => renderChart(history.length + 5)).not.toThrow();
	});

	it('draws the reference line for an in-bounds selected point', () => {
		const {container} = renderChart(1);

		expect(
			container.querySelector('.recharts-reference-line')
		).toBeInTheDocument();
	});
});
