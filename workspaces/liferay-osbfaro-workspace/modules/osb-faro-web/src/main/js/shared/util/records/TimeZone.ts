import {Record} from 'immutable';

export interface ITimeZone {
	country: string;
	displayTimeZone: string;
	timeZoneId: string;
}

export default class TimeZone
	extends Record({
		country: 'UTC',
		displayTimeZone: '(UTC) UTC',
		timeZoneId: 'UTC',
	})
	implements ITimeZone
{
	declare country: string;
	declare displayTimeZone: string;
	declare timeZoneId: string;

	constructor(props = {}) {
		super(props);
	}
}
