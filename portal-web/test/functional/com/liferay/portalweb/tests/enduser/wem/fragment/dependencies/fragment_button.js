{
	const form = document.querySelector('.fragment-button:not(.parsed)');

	form.classList.add('parsed');

	form.addEventListener(
		'submit',
		(event) => {
			event.preventDefault();

			if (Liferay.__FF__.enableCustomDialogs) {
				Liferay.Util.openAlertModal({message: 'Form submitted'});
			} else {
				alert('Form submitted');
			}
		});
}