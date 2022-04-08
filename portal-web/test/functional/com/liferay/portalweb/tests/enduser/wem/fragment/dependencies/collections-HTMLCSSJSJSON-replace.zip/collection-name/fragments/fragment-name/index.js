{
	const form = document.querySelector('.fragment-configuration:not(.parsed)');

	form.classList.add('parsed');

	form.addEventListener(
		'submit',
		(event) => {
			event.preventDefault();

			if (Liferay.__FF__.enableCustomDialogs) {
				Liferay.Util.openAlertModal({message: 'Here we go'});
			} else {
				alert('Here we go');
			}
		});
}