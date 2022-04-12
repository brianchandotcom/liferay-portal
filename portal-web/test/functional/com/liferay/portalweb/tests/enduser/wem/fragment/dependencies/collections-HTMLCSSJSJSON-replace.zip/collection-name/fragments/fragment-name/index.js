{
	const form = document.querySelector('.fragment-configuration:not(.parsed)');

	const MESSAGE = 'Here we go';

	form.classList.add('parsed');

	form.addEventListener(
		'submit',
		(event) => {
			event.preventDefault();

			if (Liferay.__FF__.enableCustomDialogs) {
				Liferay.Util.openAlertModal({message: MESSAGE});
			} else {
				alert(MESSAGE);
			}
		});
}