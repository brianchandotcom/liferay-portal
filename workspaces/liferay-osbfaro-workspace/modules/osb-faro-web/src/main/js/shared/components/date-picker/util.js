import moment from 'moment';
import {conformsTo, isNil} from 'lodash';

/**
 * Represents a time between two dates.
 * @typedef Range
 * @property {Moment} start - The start of the range.
 * @property {Moment} end - The end of the range.
 */

export function isAboveMaxRange(date, maxRange) {
	return (
		isRange(date) &&
		date.start &&
		date.end &&
		maxRange &&
		moment
			.duration({
				from: date.start,
				to: date.end
			})
			.asDays() > maxRange
	);
}

/**
 * A validation function that determines if a value is a valid
 * moment instance, allowing for null.
 * @param {Object} value - The value to test.
 * @returns {boolean}
 */
function isMomentOrNil(value) {
	return isNil(value) || moment.isMoment(value);
}

/**
 * A validation function that determines if the value is a valid
 * moment or range.
 * @param {Object} value - The value to test.
 * @returns {boolean}
 */
export function isDateOrRange(value) {
	return isNil(value) || moment.isMoment(value) || isRange(value);
}

/**
 * Determines if a date is in a {@link Range}.
 * @param {Range|Moment} dateOrRange - The range to check in.
 * @param {Moment} date - The date to check for.
 * @returns {boolean}
 */
export function isInRange(dateOrRange, date) {
	if (moment.isMoment(dateOrRange)) {
		return false;
	}

	const {end, start} = dateOrRange;

	return start && date.isAfter(start) && end && date.isBefore(end);
}

/**
 * A validation function that determines if a value is a valid {@link Range}.
 * @param {Object} value - The value to test.
 * @returns {boolean}
 */
export function isRange(value) {
	return conformsTo(value, {
		end: isMomentOrNil,
		start: isMomentOrNil
	});
}

/**
 * Adds a date to an existing {@link Range}.
 *
 * This function will try to be smart and first update the
 * start or end if either are null. If both exist, then it will return
 * a new range with the start set to the new date, and the end reset
 * to null.
 * @param {Range} range - The range to update.
 * @param {Moment} date - The date to add to the range.
 * @returns {Range}
 */
export function updateRange(range, date) {
	if (!range.start) {
		return {
			...range,
			start: date
		};
	}

	if (range.start && !range.end) {
		if (date.isSameOrAfter(range.start)) {
			return {
				...range,
				end: date
			};
		} else {
			return {
				end: range.start,
				start: date
			};
		}
	}

	if (date.isSame(range.start) && date.isSame(range.end)) {
		return range;
	}

	return {
		end: null,
		start: date
	};
}
