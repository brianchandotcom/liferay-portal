const containerClass = '.navigation-container';

document.addEventListener('DOMContentLoaded', function () {
	const container = document.querySelector(containerClass);

	const checkboxes = container.querySelectorAll('input[type="checkbox"]:not(.nav-items-menu-button-input)');

	document.addEventListener('click', function (event) {
		if (!container.contains(event.target)) {
			for (const checkbox of checkboxes) {
				checkbox.checked = false;
			}
		}
	});

	document.addEventListener('keydown', function (event) {
		if (event.keyCode === 27) {
			for (const checkbox of checkboxes) {
				checkbox.checked = false;
			}
		}
	});

	for (const checkbox of checkboxes) {
		checkbox.addEventListener('click', function () {
			const otherCheckboxes = container.querySelectorAll('input[type="checkbox"]:not(.nav-items-menu-button-input)');

			for (const otherCheckbox of otherCheckboxes) {
				if (otherCheckbox !== this) {
					otherCheckbox.checked = false;
				}
			}
		});
	}
});
