/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map} from 'immutable';
import moment from 'moment';
import {Interval, RangeSelectors} from '~/shared/types';
import {RangeKeyTimeRanges} from '~/shared/util/constants';
import {INTERVAL_KEY_MAP} from '~/shared/util/time';

export const createDateKeysIMap = function createDateKeysIMap(
	interval: Interval,
	history: Array<any>,
	dateKey: string = 'intervalInitDate'
) {
	const parseHistory = (item: Record<string, number>) => {
		const dateStart = item[dateKey];
		const dateEnd =
			interval === 'W'
				? moment.utc(dateStart, 'x').add('6', 'days').valueOf()
				: null;

		return [dateStart, [dateStart, dateEnd]];
	};

	return Map<number, [number, number | null]>(history.map(parseHistory));
};

export const handleDayInterval = function handleDayInterval(
	handleFn: (date: number) => number,
	firstTick: number,
	lastDate: number
): number[] {
	const intervals = [firstTick];
	let lastTick = firstTick;

	while (lastTick < lastDate) {
		lastTick = handleFn(lastTick);

		if (lastTick <= lastDate) {
			intervals.push(lastTick);
		}
	}

	return intervals;
};

export const getByEvenOrOddIndexes = function getByEvenOrOddIndexes(
	array: number[]
): number[] {
	return array.length % 2 === 0
		? [array[0], ...array.filter((_, index) => index % 2 !== 0)]
		: array.filter((_, index) => index % 2 === 0);
};

export const getByIndexesMultipleOfFour = function getByIndexesMultipleOfFour(
	array: number[]
): number[] {
	return array.filter((_, index) => index % 4 === 0);
};

export const getByIndexesMultipleOfSix = function getByIndexesMultipleOfSix(
	array: number[]
): number[] {
	return [
		...array.filter((_, index) => index % 6 === 0),
		array[array.length - 1],
	];
};

export const getNextSunday = function getNextSunday(date: number): number {
	return (

		// TIMEZONE

		moment.utc(date).day(7).startOf('day').valueOf()
	);
};

export const getSundays = function getSundays(array: number[]): number[] {
	const firstDate = array[0];
	const lastDate = array[array.length - 1];

	const firstTick =
		moment.utc(firstDate).get('day') === 0
			? firstDate
			: getNextSunday(firstDate);

	return handleDayInterval(getNextSunday, firstTick, lastDate);
};

export const getNextFirst = function getNextFirst(date: number): number {
	return moment.utc(date).endOf('month').add(1).valueOf();
};

export const getFirstDays = function getFirstDays(array: number[]): number[] {
	const firstDate = array[0];
	const lastDate = array[array.length - 1];

	const firstTick =
		moment.utc(firstDate).get('day') === 1
			? firstDate
			: getNextFirst(firstDate);

	return handleDayInterval(getNextFirst, firstTick, lastDate);
};

export const getNextFirstOrFifteenth = function getNextFirstOrFifteenth(
	date: number
): number {
	if (moment.utc(date).get('date') >= 15) {
		return getNextFirst(date);
	}

	return (
		moment

			// TIMEZONE

			.utc(date)
			.date(15)
			.startOf('day')
			.valueOf()
	);
};

export const getFirstAndFifteenthsDays = function getFirstAndFifteenthsDays(
	array: number[]
): number[] {
	const firstDate = array[0];
	const lastDate = array[array.length - 1];

	const firstDayDate = moment.utc(firstDate).get('date');

	const firstTick =
		firstDayDate === 1 || firstDayDate === 15
			? firstDate
			: getNextFirstOrFifteenth(firstDate);

	return handleDayInterval(getNextFirstOrFifteenth, firstTick, lastDate);
};

export const getByCustomRangeKey = function getByCustomRangeKey(
	duration: number,
	timeInterval: Interval
) {
	if (timeInterval === INTERVAL_KEY_MAP.day) {
		if (duration >= 14 && duration <= 30) {
			return getSundays;
		}
		else if (duration > 30 && duration <= 180) {
			return getFirstAndFifteenthsDays;
		}
		else if (duration > 180) {
			return getFirstDays;
		}
	}
	else if (timeInterval === INTERVAL_KEY_MAP.week) {
		if (duration > 90 && duration <= 180) {
			return getByEvenOrOddIndexes;
		}
		else if (duration > 180) {
			return getByIndexesMultipleOfFour;
		}
	}
};

export const getDayIntervalsMap = function getDayIntervalsMap(
	duration: number
) {
	return {
		[RangeKeyTimeRanges.CustomRange]: getByCustomRangeKey(
			duration,
			INTERVAL_KEY_MAP.day
		),

		[RangeKeyTimeRanges.Last180Days]: getFirstAndFifteenthsDays,
		[RangeKeyTimeRanges.Last24Hours]: getByIndexesMultipleOfSix,
		[RangeKeyTimeRanges.Last28Days]: getSundays,
		[RangeKeyTimeRanges.Last30Days]: getSundays,
		[RangeKeyTimeRanges.Last90Days]: getFirstAndFifteenthsDays,
		[RangeKeyTimeRanges.LastYear]: getFirstDays,
		[RangeKeyTimeRanges.Yesterday]: getByIndexesMultipleOfSix,
	};
};

export const getWeekIntervalsMap = function getWeekIntervalsMap(
	duration: number
) {
	return {
		[RangeKeyTimeRanges.CustomRange]: getByCustomRangeKey(
			duration,
			INTERVAL_KEY_MAP.week
		),

		[RangeKeyTimeRanges.Last180Days]: getByEvenOrOddIndexes,
		[RangeKeyTimeRanges.Last90Days]: getByEvenOrOddIndexes,
		[RangeKeyTimeRanges.LastYear]: getByIndexesMultipleOfFour,
	};
};

export const getIntervalsFromMap = function getIntervalsFromMap(
	duration: number
) {
	return {
		[INTERVAL_KEY_MAP.day]: getDayIntervalsMap(duration),
		[INTERVAL_KEY_MAP.week]: getWeekIntervalsMap(duration),
	};
};

export const getIntervalHandle = function getIntervalHandle(
	rangeKey: RangeSelectors['rangeKey'],
	duration: number,
	timeInterval: Interval
) {
	const intervalMapsByRangeKey = getIntervalsFromMap(duration)[timeInterval];

	return (
		intervalMapsByRangeKey &&
		(intervalMapsByRangeKey as Record<string, (arr: number[]) => number[]>)[
			rangeKey
		]
	);
};
