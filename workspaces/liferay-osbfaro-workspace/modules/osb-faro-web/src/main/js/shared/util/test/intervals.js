/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map} from 'immutable';
import moment from 'moment';
import {RangeKeyTimeRanges} from '~/shared/util/constants';
import {getDate} from '~/shared/util/date';
import {INTERVAL_KEY_MAP} from '~/shared/util/time';
import * as data from '~/test/data';

import {
	createDateKeysIMap,
	getByCustomRangeKey,
	getByEvenOrOddIndexes,
	getByIndexesMultipleOfFour,
	getByIndexesMultipleOfSix,
	getDayIntervalsMap,
	getFirstAndFifteenthsDays,
	getFirstDays,
	getIntervalHandle,
	getIntervalsFromMap,
	getNextFirst,
	getNextFirstOrFifteenth,
	getNextSunday,
	getSundays,
	getWeekIntervalsMap,
	handleDayInterval,
} from '../intervals';

const currentDate = moment.utc(getDate()).valueOf();
const mockDate = moment.utc('2020-06-12').valueOf(); // Friday

const mockData = [
	{
		intervalInitDate: data.getTimestamp(0),
		scoreAvg: 2,
		totalElements: 5,
	},
	{
		intervalInitDate: data.getTimestamp(1),
		scoreAvg: 4,
		totalElements: 10,
	},
];

describe('createDateKeysIMap', () => {
	it('creates an dateKeysIMap', () => {
		const dateKeysIMap = createDateKeysIMap('D', mockData);

		expect(dateKeysIMap).toBeInstanceOf(Map);
	});

	it('creates an dateKeysIMap with two date when interval is week', () => {
		const dateKeysIMap = createDateKeysIMap(
			'W',
			mockData,
			'intervalInitDate'
		);

		const dates = dateKeysIMap.get(data.getTimestamp(0));

		expect(dates[0]).toBeNumber();
		expect(dates[1]).toBeNumber();
	});
});

describe('getNext Functions', () => {
	it('gets next sunday from a given date', () => {
		const nextSunday = getNextSunday(currentDate);

		expect(moment.utc(nextSunday).get('day')).toEqual(0);
	});

	it('gets next first day of a month from a given date', () => {
		const nextFirst = getNextFirst(currentDate);

		expect(moment.utc(nextFirst).get('date')).toEqual(1);
	});

	it('gets next first or fifteenth day of a month from a given date', () => {
		const mockToFifteenthDate = moment.utc('2020-06-12').valueOf();
		const mockToFirstDate = moment.utc('2020-06-16').valueOf();

		const nextFirst = getNextFirstOrFifteenth(mockToFirstDate);
		const nextFifteenth = getNextFirstOrFifteenth(mockToFifteenthDate);

		expect(moment.utc(nextFifteenth).get('date')).toEqual(15);
		expect(moment.utc(nextFirst).get('date')).toEqual(1);
	});
});

describe('getDates functions', () => {
	const dates = [];

	for (let i = 1; i < 30; i++) {
		const date = mockDate - i * 8.64e7;

		dates.push(date);
	}

	dates.reverse();

	it('get Sundays from a given date array', () => {
		const sundays = getSundays(dates);

		expect(moment.utc(sundays[0]).get('day')).toEqual(0);
		expect(moment.utc(sundays[1]).get('day')).toEqual(0);
		expect(moment.utc(sundays[2]).get('day')).toEqual(0);
	});

	it('get first days of each month from a given date array', () => {
		const firstDays = getFirstDays(dates);

		expect(moment.utc(firstDays[0]).get('date')).toEqual(1);
	});

	it('get first or fifteenth days of each month from a given date array', () => {
		const firstOrFifteenthDays = getFirstAndFifteenthsDays(dates);

		expect(moment.utc(firstOrFifteenthDays[0]).get('date')).toEqual(15);
		expect(moment.utc(firstOrFifteenthDays[1]).get('date')).toEqual(1);
	});

	it('get by even or odd indexes', () => {

		// even array

		const evenIndexes = getByEvenOrOddIndexes(dates.slice(0, 9));

		expect(evenIndexes).toEqual([
			dates[0],
			dates[2],
			dates[4],
			dates[6],
			dates[8],
		]);

		// odd array should include first item

		const oddIndexes = getByEvenOrOddIndexes(dates.slice(0, 8));

		expect(oddIndexes).toEqual([
			dates[0],
			dates[1],
			dates[3],
			dates[5],
			dates[7],
		]);
	});

	it('get by indexes multiple of four', () => {
		const multiplesOfFourIndexes = getByIndexesMultipleOfFour(
			dates.slice(0, 13)
		);

		expect(multiplesOfFourIndexes).toEqual([
			dates[0],
			dates[4],
			dates[8],
			dates[12],
		]);
	});

	it('get by indexes multiple of six', () => {
		const multiplesOfSixIndexes = getByIndexesMultipleOfSix(
			dates.slice(0, 24)
		);

		expect(multiplesOfSixIndexes).toEqual([
			dates[0],
			dates[6],
			dates[12],
			dates[18],
			dates[23],
		]);
	});
});

describe('get functions to handle custom range key', () => {
	it('returns undefined if the duration is less than 14 days', () => {
		const handleFn = getByCustomRangeKey(13, INTERVAL_KEY_MAP.day);

		expect(handleFn).toEqual(undefined);
	});

	it('returns getSundays with day interval if the duration is more or equal 14 and less or equal 30 days', () => {
		const handleFn = getByCustomRangeKey(30, INTERVAL_KEY_MAP.day);

		expect(handleFn).toBe(getSundays);
	});

	it('returns getFirstAndFifteenthsDays with day interval if the duration is more than 30 and less or equal 180 days', () => {
		const handleFn = getByCustomRangeKey(180, INTERVAL_KEY_MAP.day);

		expect(handleFn).toBe(getFirstAndFifteenthsDays);
	});

	it('returns getFirstDays with day interval if the duration is more than 180', () => {
		const handleFn = getByCustomRangeKey(365, INTERVAL_KEY_MAP.day);

		expect(handleFn).toBe(getFirstDays);
	});

	it('returns getByEvenOrOddIndexes with week interval if the duration is more than 30 and less or equal 180 days', () => {
		const handleFn = getByCustomRangeKey(180, INTERVAL_KEY_MAP.week);

		expect(handleFn).toBe(getByEvenOrOddIndexes);
	});

	it('returns getByIndexesMultipleOfFour with day interval if the duration is more than 180', () => {
		const handleFn = getByCustomRangeKey(365, INTERVAL_KEY_MAP.week);

		expect(handleFn).toBe(getByIndexesMultipleOfFour);
	});
});

describe('getIntervalHandle functions', () => {
	it('returns undefined if a interval or rangeKey is not mapped', () => {
		let handleFn = getIntervalHandle(
			RangeKeyTimeRanges.Last30Days,
			[],
			INTERVAL_KEY_MAP.month
		);

		expect(handleFn).toEqual(undefined);

		handleFn = getIntervalHandle(
			RangeKeyTimeRanges.Last7Days,
			[],
			INTERVAL_KEY_MAP.day
		);

		expect(handleFn).toEqual(undefined);
	});

	it('returns a map object when a interval is mapped', () => {
		const intervalMaps = getIntervalsFromMap(30);

		expect(intervalMaps).toHaveProperty(INTERVAL_KEY_MAP.day);
		expect(intervalMaps).toHaveProperty(INTERVAL_KEY_MAP.week);

		expect(intervalMaps[INTERVAL_KEY_MAP.day]).toHaveProperty(
			RangeKeyTimeRanges.CustomRange
		);
		expect(intervalMaps[INTERVAL_KEY_MAP.week]).toHaveProperty(
			RangeKeyTimeRanges.CustomRange
		);
	});

	it('returns a map object with rangeKeys which have handle functions in day interval', () => {
		const dayIntervalMap = getDayIntervalsMap(30);

		expect(dayIntervalMap).toHaveProperty(RangeKeyTimeRanges.CustomRange);
		expect(dayIntervalMap).toHaveProperty(RangeKeyTimeRanges.Last180Days);
		expect(dayIntervalMap).toHaveProperty(RangeKeyTimeRanges.Last24Hours);
		expect(dayIntervalMap).toHaveProperty(RangeKeyTimeRanges.Last28Days);
		expect(dayIntervalMap).toHaveProperty(RangeKeyTimeRanges.Last30Days);
		expect(dayIntervalMap).toHaveProperty(RangeKeyTimeRanges.Last90Days);
		expect(dayIntervalMap).toHaveProperty(RangeKeyTimeRanges.LastYear);
		expect(dayIntervalMap).toHaveProperty(RangeKeyTimeRanges.Yesterday);
	});

	it('returns a map object with rangeKeys which have handle functions in week interval', () => {
		const weekIntervalMap = getWeekIntervalsMap(30);

		expect(weekIntervalMap).toHaveProperty(RangeKeyTimeRanges.CustomRange);
		expect(weekIntervalMap).toHaveProperty(RangeKeyTimeRanges.Last180Days);
		expect(weekIntervalMap).toHaveProperty(RangeKeyTimeRanges.Last90Days);
		expect(weekIntervalMap).toHaveProperty(RangeKeyTimeRanges.LastYear);
	});
});

describe('handleDayInterval', () => {
	it('extracts an array of dates from a start and end date using the handleFn argument as step', () => {

		// function to step two days

		const handleFn = (date) => moment.utc(date).add(2, 'days').valueOf();
		const lastDate = moment.utc('2020-06-18').valueOf();

		const intervals = handleDayInterval(handleFn, mockDate, lastDate);

		expect(intervals).toEqual([
			moment.utc('2020-06-12').valueOf(),
			moment.utc('2020-06-14').valueOf(),
			moment.utc('2020-06-16').valueOf(),
			moment.utc('2020-06-18').valueOf(),
		]);
	});
});
