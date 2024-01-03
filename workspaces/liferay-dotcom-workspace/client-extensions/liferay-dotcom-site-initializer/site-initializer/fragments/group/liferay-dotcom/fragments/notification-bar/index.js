var cookieName = 'OSB_WWW_NOTIFICATION_' + fragmentNamespace;
var body = document.body;

if (body && body.classList.contains('has-edit-mode-menu')) {
	body.classList.add('has-alert-container');
}

window.addEventListener('DOMContentLoaded', function() {
	var closeButton = fragmentElement.querySelector('#closeButton');

	if (!(document.cookie.indexOf(cookieName) >= 0)) {
		if (body) {
			body.classList.add('has-alert-container');
		}

		closeButton.addEventListener('click', function() {
			if (document.cookie.length) {
				body.classList.remove('has-alert-container');
				document.cookie = cookieName + '=true;path=/;max-age=604800;';
			} else {
				document.cookie = cookieName + '=true;path=/;';
			}
		});
	}
});