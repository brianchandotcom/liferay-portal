// import 'datebook'

// const addToCalendar = fragmentElement.querySelector('.f-add-to-calendar');
// const addToCalendarButton = fragmentElement.querySelector('.add-to-calendar-button');
// const calendarLinksWrapper = fragmentElement.querySelector('.calendar-links');
// const calendarLinks = fragmentElement.querySelectorAll('.calendar-link');

// const eventStartDate = addToCalendar.getAttribute('data-event-start-date');
// const eventStartTime = addToCalendar.getAttribute('data-event-start-time');
// const eventEndDate = addToCalendar.getAttribute('data-event-end-date');
// const eventEndTime = addToCalendar.getAttribute('data-event-end-time');
// const eventDetails = addToCalendar.getAttribute('data-event-details');
// const eventLocation = addToCalendar.getAttribute('data-event-location');
// const eventName = addToCalendar.getAttribute('data-event-name');

// const endDateTime = new Date(eventEndDate + 'T' + eventEndTime + ':00Z');
// const startDateTime = new Date(eventStartDate + 'T' + eventStartTime + ':00Z');

// const config = {
// 	description: eventDetails,
// 	end: endDateTime,
// 	location: eventLocation,
// 	start: startDateTime,
// 	title: eventName,
// };

// calendarLinks.forEach(function (link) {
// 	link.addEventListener('click', function () {
// 		new Datebook.default(link.getAttribute('data-calendar-type'), config);
// 	});
// });


// addToCalendarButton.addEventListener('click', function (e) {
// 	e.stopPropagation();
// 	calendarLinksWrapper.classList.toggle('active');
// });

// window.onclick = function () {
// 	calendarLinksWrapper.classList.remove('active');
// };

// if (startDateTime > endDateTime) {
// 	fragmentElement.innerHTML =
// 		'ERROR: Start Date/Time is not before End Date/Time';
// }