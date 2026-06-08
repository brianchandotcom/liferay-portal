/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getFinitePercent,
	toDuration,
	toFixedPoint,
	toInt,
	toLocale,
	toRounded,
	toThousands,
	undoThousands,
} from '../numbers';

describe('toLocale', () => {
	it('is return the number to locale', () => {
		expect(toLocale(0.3)).toEqual('0.3');
		expect(toLocale(0.123456)).toEqual('0.123456');
		expect(toLocale(123456.123456789)).toEqual('123,456.123457');
	});
});

describe('toThousands', () => {
	it('returns the number truncate', () => {
		expect(toThousands(0.1)).toEqual('0.1');
		expect(toThousands(1.4)).toEqual('1.4');
		expect(toThousands(1.5)).toEqual('1.5');
		expect(toThousands(2.45)).toEqual('2.45');
		expect(toThousands(2.453)).toEqual('2.45');
		expect(toThousands(2.456)).toEqual('2.46');
		expect(toThousands(10.456)).toEqual('10.46');
		expect(toThousands(150.5)).toEqual('150.5');
		expect(toThousands(150.566)).toEqual('150.57');
		expect(toThousands(1100)).toEqual('1.1K');
		expect(toThousands(1520)).toEqual('1.52K');
		expect(toThousands(2432)).toEqual('2.43K');
		expect(toThousands(51444)).toEqual('51.44K');
		expect(toThousands(255000.0)).toEqual('255K');
		expect(toThousands(4500000)).toEqual('4.5M');
		expect(toThousands(4500000000)).toEqual('4.5B');
		expect(toThousands(4560000000)).toEqual('4.56B');
		expect(toThousands(4567000000)).toEqual('4.57B');
		expect(toThousands(1500000000000)).toEqual('1.5T');
	});

	it('returns the number formatted', () => {
		expect(toThousands(0)).toEqual('0');
		expect(toThousands(1)).toEqual('1');
		expect(toThousands(10)).toEqual('10');
		expect(toThousands(100)).toEqual('100');
		expect(toThousands(1000)).toEqual('1K');
		expect(toThousands(10000)).toEqual('10K');
		expect(toThousands(100000)).toEqual('100K');
		expect(toThousands(1000000)).toEqual('1M');
		expect(toThousands(10000000)).toEqual('10M');
		expect(toThousands(100000000)).toEqual('100M');
		expect(toThousands(1000000000)).toEqual('1B');
		expect(toThousands(10000000000)).toEqual('10B');
		expect(toThousands(100000000000)).toEqual('100B');
		expect(toThousands(1000000000000)).toEqual('1T');
	});

	it('returns an empty string if parameter it is not a number', () => {
		expect(toThousands('test')).toEqual('');
		expect(toThousands([])).toEqual('');
		expect(toThousands({})).toEqual('');
	});
});

describe('toFixedPoint', () => {
	it('returns the same number if the number is less than 999', () => {
		expect(toFixedPoint(10)).toEqual('10');
		expect(toFixedPoint(100)).toEqual('100');
		expect(toFixedPoint(555)).toEqual('555');
		expect(toFixedPoint(999)).toEqual('999');
	});

	it('returns the number formatted', () => {
		expect(toFixedPoint(1000)).toEqual('1,000');
		expect(toFixedPoint(10000)).toEqual('10,000');
		expect(toFixedPoint(100000)).toEqual('100,000');
		expect(toFixedPoint(1000000)).toEqual('1,000,000');
		expect(toFixedPoint(10000000)).toEqual('10,000,000');
		expect(toFixedPoint(100000000)).toEqual('100,000,000');
		expect(toFixedPoint(1000000000)).toEqual('1,000,000,000');
		expect(toFixedPoint(10000000000)).toEqual('10,000,000,000');
		expect(toFixedPoint(100000000000)).toEqual('100,000,000,000');
	});
});

describe('toRounded', () => {
	it('returns the rounded without decimal', () => {
		expect(toRounded(20.567, 0)).toEqual('21');
		expect(toRounded(1.123, 0)).toEqual('1');
		expect(toRounded(1.543, 0)).toEqual('2');
	});

	it('is return the rounded single precision', () => {
		expect(toRounded(20.5678)).toEqual('20.6');
		expect(toRounded(1.123)).toEqual('1.1');
		expect(toRounded(1.543)).toEqual('1.5');
	});

	it('returns the rounded with two decimals', () => {
		expect(toRounded(20.5678, 2)).toEqual('20.57');
		expect(toRounded(1.123, 2)).toEqual('1.12');
		expect(toRounded(1.543, 2)).toEqual('1.54');
		expect(toRounded(0.300001, 2)).toEqual('0.30');
		expect(toRounded(30.0, 2)).toEqual('30');
	});
});

describe('toInt', () => {
	it('is return the int', () => {
		const number = toInt('1000');

		expect(number).toEqual(1000);
	});
});

describe('toDuration', () => {
	it('returns the time formatted', () => {
		const number = toDuration(1000, undefined, 'seconds');

		expect(number).toEqual('16m 40s');
	});

	it('returns the time formatted displaying milliseconds', () => {
		const number = toDuration(12101989, 'DD[days] hh[h] mm[m] ss[s] S[ms]');

		expect(number).toEqual('03h 21m 41s 989ms');
	});

	it('returns the time in the default format', () => {
		const number = toDuration(99999999);

		expect(number).toEqual('01d 03h 46m 40s');
	});
});

describe('getFinitePercent', () => {
	it.each`
		curVal | totalVal | expected
		${0}   | ${0}     | ${null}
		${0}   | ${null}  | ${null}
		${0}   | ${NaN}   | ${null}
		${10}  | ${100}   | ${'10.0'}
		${50}  | ${100}   | ${'50.0'}
		${100} | ${100}   | ${'100.0'}
	`(
		'should calculate the percent of $curVal to $totalVal return $expected',
		({curVal, expected, totalVal}) => {
			expect(getFinitePercent(curVal, totalVal)).toBe(expected);
		}
	);
});

describe('undoThousands', () => {
	it('returns a number aproximation for a formatted number', () => {
		expect(undoThousands('145')).toEqual(145);
		expect(undoThousands('23.5B')).toEqual(23500000000);
		expect(undoThousands('23.5K')).toEqual(23500);
		expect(undoThousands('23.5M')).toEqual(23500000);
	});

	it('returns the sum two numbers using method toThousand', () => {
		const formattedA = '1.5K';
		const formattedB = '2.5K';
		const formattedC = '140';

		const numberA = undoThousands(formattedA);
		const numberB = undoThousands(formattedB);
		const numberC = undoThousands(formattedC);

		expect(toThousands(numberA + numberB)).toEqual('4K');
		expect(toThousands(numberA + numberC)).toEqual('1.64K');
	});

	it('returns zero when no formatted value is specified', () => {
		expect(undoThousands()).toEqual(0);
	});

	it('returns zero when formatted value no matches', () => {
		expect(undoThousands('E$')).toEqual(0);
	});

	it('returns the number without multiplier when formatted doesnt have multipliter declared', () => {
		expect(undoThousands('50X')).toEqual(50);
		expect(undoThousands('100Y')).toEqual(100);
		expect(undoThousands('150Z')).toEqual(150);
	});
});
