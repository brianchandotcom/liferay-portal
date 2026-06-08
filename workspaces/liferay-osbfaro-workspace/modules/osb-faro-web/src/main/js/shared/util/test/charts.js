/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map} from 'immutable';
import moment from 'moment';
import {RangeKeyTimeRanges} from '~/shared/util/constants';
import {getDate} from '~/shared/util/date';
import {INTERVAL_KEY_MAP} from '~/shared/util/time';

import {
	dateRangeFormatter,
	formatTooltipDate,
	formatXAxisDate,
	getAxisFormatter,
	getAxisMeasures,
	getAxisMeasuresFromData,
	getDataFormatter,
	getDateTitle,
	getIntervals,
	getLocationsData,
	getMetricFormatter,
} from '../charts';

const mockDate = moment.utc('2019-01-02').valueOf();
const mockDateKeysIMap = new Map([[mockDate, [mockDate]]]);

describe('dateRangeFormatter', () => {
	it('renders a range of dates with different start and ending months', () => {
		expect(
			dateRangeFormatter(mockDate, moment.utc('2019-02-02').valueOf())
		).toEqual('Jan 2 - Feb 2');
	});

	it('renders a range of dates with the same start and ending months', () => {
		expect(
			dateRangeFormatter(mockDate, moment.utc('2019-01-14').valueOf())
		).toEqual('Jan 2 - 14');
	});
});

describe('formatTooltipDate', () => {
	it('returns the hours for last 24 hours', () => {
		const date = getDate('2018-08-07');

		date.setHours(20);
		date.setMinutes(30);
		date.setSeconds(0);

		const formatedDate = formatTooltipDate(
			moment.utc(date).valueOf(),
			RangeKeyTimeRanges.Last24Hours
		);

		expect(formatedDate).toEqual('Aug 7, 8 PM');
	});

	it('returns the hours with UTC for yesterday', () => {
		const date = getDate('2018-08-07');

		date.setDate(date.getDate() - 8.64e7);
		date.setHours(20);
		date.setMinutes(30);
		date.setSeconds(0);

		const formatedDate = formatTooltipDate(
			date,
			RangeKeyTimeRanges.Yesterday
		);

		expect(formatedDate).toEqual('Jun 8, 8 PM');
	});

	it('returns the hours with UTC for last 90 days', () => {
		const date = moment('2018-08-07');

		date.subtract(90, 'days');

		const formatedDate = formatTooltipDate(
			date,
			RangeKeyTimeRanges.Last90Days
		);

		expect(formatedDate).toEqual('2018 May 9');
	});

	it('returns the formated date and month', () => {
		expect(formatTooltipDate(getDate('2018-08-07'))).toEqual('2018 Aug 7');
	});
});

describe('formatXAxisDate', () => {
	it('renders an x-axis label in a day month format by default', () => {
		expect(
			formatXAxisDate(
				mockDate,
				RangeKeyTimeRanges.Last30Days,
				INTERVAL_KEY_MAP.day,
				mockDateKeysIMap
			)
		).toEqual('Jan 2');
	});

	it('renders an x-axis label in an hourly format', () => {
		expect(
			formatXAxisDate(
				mockDate,
				RangeKeyTimeRanges.Last24Hours,
				INTERVAL_KEY_MAP.day,
				mockDateKeysIMap
			)
		).toEqual('12 AM');
	});

	it('renders an x-axis label in a daterange format if the rangekey is monthly and interval is weekly', () => {
		expect(
			formatXAxisDate(
				mockDate,
				RangeKeyTimeRanges.Last30Days,
				INTERVAL_KEY_MAP.week,
				new Map([[mockDate, [mockDate, getDate('2019-01-08')]]])
			)
		).toEqual('Jan 2 - 8');
	});
});

describe('getDateTitle', () => {
	it('returns a date display string', () => {
		expect(getDateTitle([getDate('2019-01-01')])).toEqual('2019 Jan 1');
	});

	it('returns a date display string as a date range if rangeKey is monthly and interval is weekly', () => {
		expect(
			getDateTitle(
				[getDate('2019-01-01'), getDate('2019-01-14')],
				RangeKeyTimeRanges.Last30Days,
				INTERVAL_KEY_MAP.week
			)
		).toEqual('2019 Jan 1 - 14');
	});

	it('returns year and month when interval its set to month', () => {
		expect(
			getDateTitle(
				[getDate('2019-01-01')],
				RangeKeyTimeRanges.Last30Days,
				INTERVAL_KEY_MAP.month
			)
		).toEqual('2019 Jan');
	});
});

describe('getIntervals', () => {
	const currentDate = getDate('2020-06-12'); // Friday
	const dates = [];

	for (let i = 1; i < 370; i++) {
		const date = currentDate - i * 8.64e7;

		dates.push(date);
	}

	it('is return the intervals from a array of dates from the last 24 hours', () => {
		const mockDates = dates.filter((item, index) => index <= 20).reverse();
		const dateKeysIMap = new Map(mockDates.map((date) => [date, [date]]));

		expect(
			getIntervals(
				RangeKeyTimeRanges.Last24Hours,
				mockDates,
				INTERVAL_KEY_MAP.day,
				dateKeysIMap
			)
		).toEqual([
			mockDates[0],
			mockDates[6],
			mockDates[12],
			mockDates[18],
			mockDates[20],
		]);
	});

	it('is return the intervals from a array of dates from yesterday', () => {
		const mockDates = dates.filter((item, index) => index <= 22).reverse();
		const dateKeysIMap = new Map(mockDates.map((date) => [date, [date]]));

		expect(
			getIntervals(
				RangeKeyTimeRanges.Yesterday,
				mockDates,
				INTERVAL_KEY_MAP.day,
				dateKeysIMap
			)
		).toEqual([
			mockDates[0],
			mockDates[6],
			mockDates[12],
			mockDates[18],
			mockDates[22],
		]);
	});

	it('is return only sundays as intervals from an array of dates from the last 28 days', () => {
		const mockDates = dates.filter((item, index) => index <= 27).reverse();
		const dateKeysIMap = new Map(mockDates.map((date) => [date, [date]]));

		const intervals = getIntervals(
			RangeKeyTimeRanges.Last28Days,
			mockDates,
			INTERVAL_KEY_MAP.day,
			dateKeysIMap
		);

		expect(moment.utc(intervals[0]).get('day')).toEqual(0);
		expect(moment.utc(intervals[1]).get('day')).toEqual(0);
		expect(moment.utc(intervals[2]).get('day')).toEqual(0);
		expect(moment.utc(intervals[3]).get('day')).toEqual(0);

		expect(intervals).toEqual([
			mockDates[2],
			mockDates[9],
			mockDates[16],
			mockDates[23],
		]);
	});

	it('returns the unfiltered interval dates from an array of dates from the last 28 days and with an interval of longer than a day', () => {
		const mockDates = dates.filter((item, index) => index <= 27).reverse();
		const dateKeysIMap = new Map(mockDates.map((date) => [date, [date]]));

		expect(
			getIntervals(
				RangeKeyTimeRanges.Last28Days,
				mockDates,
				INTERVAL_KEY_MAP.week,
				dateKeysIMap
			)
		).toEqual(expect.arrayContaining(mockDates));
	});

	it('is return only sundays as intervals from a array of dates from the last 30 days', () => {
		const mockDates = dates.filter((item, index) => index <= 29).reverse();
		const dateKeysIMap = new Map(mockDates.map((date) => [date, [date]]));

		const intervals = getIntervals(
			RangeKeyTimeRanges.Last30Days,
			mockDates,
			INTERVAL_KEY_MAP.day,
			dateKeysIMap
		);

		expect(moment.utc(intervals[0]).get('day')).toEqual(0);
		expect(moment.utc(intervals[1]).get('day')).toEqual(0);
		expect(moment.utc(intervals[2]).get('day')).toEqual(0);
		expect(moment.utc(intervals[3]).get('day')).toEqual(0);

		expect(intervals).toEqual([
			mockDates[4],
			mockDates[11],
			mockDates[18],
			mockDates[25],
		]);
	});

	it('returns the unfiltered interval dates from a array of dates from the last 30 days with a week interval', () => {
		const mockDates = dates.filter((item, index) => index <= 29);
		const dateKeysIMap = new Map(mockDates.map((date) => [date, [date]]));

		expect(
			getIntervals(
				RangeKeyTimeRanges.Last30Days,
				mockDates,
				INTERVAL_KEY_MAP.week,
				dateKeysIMap
			)
		).toEqual(expect.arrayContaining(mockDates));
	});

	it('is return only the 1st or 15th of each month from a array of dates from the last 90 days with an interval of day', () => {
		const mockDates = dates.filter((item, index) => index <= 89).reverse();
		const dateKeysIMap = new Map(mockDates.map((date) => [date, [date]]));

		const result = getIntervals(
			RangeKeyTimeRanges.Last90Days,
			mockDates,
			INTERVAL_KEY_MAP.day,
			dateKeysIMap
		);

		expect(moment.utc(result[0]).get('date')).toEqual(15);
		expect(moment.utc(result[1]).get('date')).toEqual(1);
		expect(moment.utc(result[2]).get('date')).toEqual(15);
	});

	it('is return the intervals with multiple of two indexes from a array of dates from the last 90 days with a week interval', () => {
		const mockDates = dates.filter((item, index) => index <= 89).reverse();
		const dateKeysIMap = new Map(mockDates.map((date) => [date, [date]]));

		expect(
			getIntervals(
				RangeKeyTimeRanges.Last90Days,
				mockDates,
				INTERVAL_KEY_MAP.week,
				dateKeysIMap
			)
		).toEqual(mockDates.filter((_, i) => i === 0 || i % 2 !== 0));
	});

	it('is return only the 1st or 15th of each month from a array of dates from the last 180 days with an interval of day', () => {
		const mockDates = dates.filter((item, index) => index <= 179).reverse();
		const dateKeysIMap = new Map(mockDates.map((date) => [date, [date]]));

		const result = getIntervals(
			RangeKeyTimeRanges.Last180Days,
			mockDates,
			INTERVAL_KEY_MAP.day,
			dateKeysIMap
		);

		expect(moment.utc(result[0]).get('date')).toEqual(15);
		expect(moment.utc(result[1]).get('date')).toEqual(1);
		expect(moment.utc(result[2]).get('date')).toEqual(15);
	});

	it('is return the intervals with multiple of two indexes from a array of dates from the last 180 days with a week interval', () => {
		const mockDates = dates.filter((item, index) => index <= 179).reverse();
		const dateKeysIMap = new Map(mockDates.map((date) => [date, [date]]));

		expect(
			getIntervals(
				RangeKeyTimeRanges.Last180Days,
				mockDates,
				INTERVAL_KEY_MAP.week,
				dateKeysIMap
			)
		).toEqual(mockDates.filter((_, i) => i === 0 || i % 2 !== 0));
	});

	it('is return only the 1st of each month from a array of dates from the last year with an interval of day', () => {
		const mockDates = dates.filter((item, index) => index <= 364).reverse();
		const dateKeysIMap = new Map(mockDates.map((date) => [date, [date]]));

		const result = getIntervals(
			RangeKeyTimeRanges.LastYear,
			mockDates,
			INTERVAL_KEY_MAP.day,
			dateKeysIMap
		);

		expect(moment.utc(result[0]).get('date')).toEqual(1);
		expect(moment.utc(result[1]).get('date')).toEqual(1);
		expect(moment.utc(result[2]).get('date')).toEqual(1);
		expect(moment.utc(result[3]).get('date')).toEqual(1);
		expect(moment.utc(result[4]).get('date')).toEqual(1);
	});

	it('is return the intervals with multiple of four indexes from a array of dates from the last year with a week interval', () => {
		const mockDates = dates.filter((item, index) => index <= 364).reverse();
		const dateKeysIMap = new Map(mockDates.map((date) => [date, [date]]));

		expect(
			getIntervals(
				RangeKeyTimeRanges.LastYear,
				mockDates,
				INTERVAL_KEY_MAP.week,
				dateKeysIMap
			)
		).toEqual(mockDates.filter((_, i) => i === 0 || i % 4 === 0));
	});

	it('is return only sundays as intervals from an array of dates of a custom rangeKey greater or equal 14 and smaller or equal 30', () => {
		let mockDates = dates.filter((item, index) => index <= 13).reverse();
		let dateKeysIMap = new Map(mockDates.map((date) => [date, [date]]));

		let intervals = getIntervals(
			RangeKeyTimeRanges.CustomRange,
			mockDates,
			INTERVAL_KEY_MAP.day,
			dateKeysIMap
		);

		expect(moment.utc(intervals[0]).get('day')).toEqual(0);
		expect(moment.utc(intervals[1]).get('day')).toEqual(0);

		mockDates = dates.filter((item, index) => index <= 29).reverse();

		dateKeysIMap = new Map(mockDates.map((date) => [date, [date]]));
		intervals = getIntervals(
			RangeKeyTimeRanges.CustomRange,
			mockDates,
			INTERVAL_KEY_MAP.day,
			dateKeysIMap
		);

		expect(moment.utc(intervals[0]).get('day')).toEqual(0);
		expect(moment.utc(intervals[1]).get('day')).toEqual(0);
	});

	it('is return only the 1st or 15th of each month from a array of dates of a custom rangeKey greater than 30 and smaller or equal 180', () => {
		let mockDates = dates.filter((item, index) => index <= 39).reverse();
		let dateKeysIMap = new Map(mockDates.map((date) => [date, [date]]));

		let intervals = getIntervals(
			RangeKeyTimeRanges.CustomRange,
			mockDates,
			INTERVAL_KEY_MAP.day,
			dateKeysIMap
		);

		expect(moment.utc(intervals[0]).get('date')).toEqual(15);
		expect(moment.utc(intervals[1]).get('date')).toEqual(1);

		mockDates = dates.filter((item, index) => index <= 179).reverse();
		dateKeysIMap = new Map(mockDates.map((date) => [date, [date]]));

		intervals = getIntervals(
			RangeKeyTimeRanges.CustomRange,
			mockDates,
			INTERVAL_KEY_MAP.day,
			dateKeysIMap
		);

		expect(moment.utc(intervals[0]).get('date')).toEqual(15);
		expect(moment.utc(intervals[1]).get('date')).toEqual(1);
	});

	it('is return only the 1st of each month from a array of dates of a custom rangeKey greater than 180', () => {
		const mockDates = dates.filter((item, index) => index <= 200).reverse();
		const dateKeysIMap = new Map(mockDates.map((date) => [date, [date]]));

		const result = getIntervals(
			RangeKeyTimeRanges.CustomRange,
			mockDates,
			INTERVAL_KEY_MAP.day,
			dateKeysIMap
		);

		expect(moment.utc(result[0]).get('date')).toEqual(1);
		expect(moment.utc(result[1]).get('date')).toEqual(1);
		expect(moment.utc(result[2]).get('date')).toEqual(1);
	});

	it('returns an empty intervals array if the original data array was empty', () => {
		expect(getIntervals(RangeKeyTimeRanges.Last24Hours, [])).toEqual(
			expect.arrayContaining([])
		);
	});
});

describe('getLocationsData', () => {
	const locationsData = getLocationsData([
		{value: 200},
		{value: 400},
		{value: 400},
		{value: 400},
		{value: 600},
		{value: 400},
		{value: 100},
	]);

	it('is return the locations data', () => {
		expect([locationsData[0]]).toEqual([
			{
				group: undefined,
				id: undefined,
				name: undefined,
				total: 200,
				value: '8',
			},
		]);
	});

	it('is return the others countries object in the last position when have more then five rows', () => {
		expect(locationsData[locationsData.length - 1]).toEqual({
			color: '#CCCCCC',
			group: 'Other Countries',
			id: 'others',
			name: 'Other Countries',
			total: 500,
			value: '20',
		});
	});

	it('is return the others regions object in the last position when have more then five rows', () => {
		const locationsDataRegions = getLocationsData(
			[
				{value: 200},
				{value: 400},
				{value: 400},
				{value: 400},
				{value: 600},
				{value: 400},
				{value: 100},
			],
			'Brazil'
		);

		expect(locationsDataRegions[locationsDataRegions.length - 1]).toEqual({
			color: '#CCCCCC',
			group: 'Other Regions',
			id: 'others',
			name: 'Other Regions',
			total: 500,
			value: '20',
		});
	});
});

describe('getAxisMeasures', () => {
	it('is return the axis intervals, maxValue and interval value', () => {
		expect(getAxisMeasures(0.7)).toEqual({
			intervalCount: 4,
			intervalValue: 0.2,
			intervals: [0, 0.2, 0.4, 0.6000000000000001, 0.8],
			maxValue: 0.8,
		});
		expect(getAxisMeasures(1)).toEqual({
			intervalCount: 3,
			intervalValue: 0.5,
			intervals: [0, 0.5, 1, 1.5],
			maxValue: 1.5,
		});
		expect(getAxisMeasures(1.6)).toEqual({
			intervalCount: 4,
			intervalValue: 0.5,
			intervals: [0, 0.5, 1, 1.5, 2],
			maxValue: 2,
		});
		expect(getAxisMeasures(1.7)).toEqual({
			intervalCount: 4,
			intervalValue: 0.5,
			intervals: [0, 0.5, 1, 1.5, 2],
			maxValue: 2,
		});
		expect(getAxisMeasures(11)).toEqual({
			intervalCount: 3,
			intervalValue: 5,
			intervals: [0, 5, 10, 15],
			maxValue: 15,
		});
		expect(getAxisMeasures(16)).toEqual({
			intervalCount: 4,
			intervalValue: 5,
			intervals: [0, 5, 10, 15, 20],
			maxValue: 20,
		});
		expect(getAxisMeasures(201)).toEqual({
			intervalCount: 3,
			intervalValue: 100,
			intervals: [0, 100, 200, 300],
			maxValue: 300,
		});
		expect(getAxisMeasures(745)).toEqual({
			intervalCount: 4,
			intervalValue: 200,
			intervals: [0, 200, 400, 600, 800],
			maxValue: 800,
		});
		expect(getAxisMeasures(1001)).toEqual({
			intervalCount: 3,
			intervalValue: 500,
			intervals: [0, 500, 1000, 1500],
			maxValue: 1500,
		});
		expect(getAxisMeasures(100450)).toEqual({
			intervalCount: 3,
			intervalValue: 50000,
			intervals: [0, 50000, 100000, 150000],
			maxValue: 150000,
		});
	});
});

describe('getAxisMeasuresFromData', () => {
	it('is return the max value from a data', () => {
		expect(
			getAxisMeasuresFromData([
				['data1', 0, 1001, 145],
				['data2', 100, 400, 3450],
			])
		).toEqual({
			intervalCount: 4,
			intervalValue: 1000,
			intervals: [0, 1000, 2000, 3000, 4000],
			maxValue: 4000,
		});
	});
});

describe('getAxisFormatter', () => {
	it('is return the value to percentage', () => {
		expect(getAxisFormatter('percentage')(1)).toEqual('100%');
	});

	it('is return the value to ratings', () => {
		expect(getAxisFormatter('ratings')(1)).toEqual('10.00');
	});

	it('is return the value to any', () => {
		expect(getAxisFormatter()(10)).toEqual('10');
	});
});

describe('getDataFormatter', () => {
	it('is return the value to any', () => {
		expect(getDataFormatter()([1, 100, 1000, 10000])).toEqual([
			1, 100, 1000, 10000,
		]);
	});

	it('is return the data formatted to percentage', () => {
		expect(getDataFormatter('percentage')([1, 100, 1000, 10000])).toEqual([
			1, 100, 1000, 10000,
		]);
	});

	it('is return the data formatted to time', () => {
		expect(getDataFormatter('time')([1, 100, 500, 1000, 10000])).toEqual([
			0, 0, 1000, 1000, 10000,
		]);
	});

	it('is return the data formatted to numbers', () => {
		expect(getDataFormatter('numbers')([1, 100, 1000, 10000])).toEqual([
			1, 100, 1000, 10000,
		]);
	});

	it('is return the data formatted to ratings', () => {
		expect(getDataFormatter('ratings')([1, 100, 1000, 10000])).toEqual([
			1, 100, 1000, 10000,
		]);
	});
});

describe('getMetricFormatter', () => {
	it('shold be return the metric formatter', () => {
		expect(getMetricFormatter('number')(1)).toEqual('1');
		expect(getMetricFormatter('percentage')(1)).toEqual('100%');
		expect(getMetricFormatter('time')(1)).toEqual('00s');
		expect(getMetricFormatter('any')(1)).toEqual('1');
	});
});
