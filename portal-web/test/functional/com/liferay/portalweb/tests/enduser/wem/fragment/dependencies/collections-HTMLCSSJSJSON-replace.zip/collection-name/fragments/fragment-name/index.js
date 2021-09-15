{
	const form = document.querySelector('.fragment-configuration:not(.parsed)');

	form.classList.add('parsed');

	form.addEventListener(
		'submit',
		(event) => {
			event.preventDefault();

			alert('Here we go');
		});
}