/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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
