/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	GoogleCalendar,
	ICalendar,
	OutlookCalendar,
	YahooCalendar,
} from 'datebook';

class Datebook {
	constructor(calendarType, config) {
		this.calendarType = calendarType;
		this.config = config;

		if (this.calendarType === 'apple' || this.calendarType === 'outlook') {
			const icalendar = new ICalendar(config);

			icalendar.download();
		}

		if (this.calendarType === 'outlook-online') {
			const outlookOnline = new OutlookCalendar(config);

			window.open(outlookOnline.render(), '_blank');
		}

		if (this.calendarType === 'yahoo') {
			const yahoo = new YahooCalendar(config);

			window.open(yahoo.render(), '_blank');
		}

		if (this.calendarType === 'google') {
			const google = new GoogleCalendar(config);

			window.open(google.render(), '_blank');
		}
	}
}

export default Datebook;
