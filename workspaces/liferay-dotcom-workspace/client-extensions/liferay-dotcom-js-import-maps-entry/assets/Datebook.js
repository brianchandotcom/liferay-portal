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

		if (this.calendarType == 'apple' || this.calendarType == 'outlook') {
			var icalendar = new ICalendar(config);

			icalendar.download();
		}

		if (this.calendarType == 'outlook-online') {
			var outlookOnline = new OutlookCalendar(config);

			window.open(outlookOnline.render(), '_blank');
		}

		if (this.calendarType == 'yahoo') {
			var yahoo = new YahooCalendar(config);

			window.open(yahoo.render(), '_blank');
		}

		if (this.calendarType == 'google') {
			var google = new GoogleCalendar(config);

			window.open(google.render(), '_blank');
		}
	}
}

export default Datebook;
