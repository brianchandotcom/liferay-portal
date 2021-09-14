{
	const form = document.querySelector('.fragment-footer:not(.parsed)');

	form.classList.add('parsed');

	form.addEventListener(
		'submit',
		(event) => {
			event.preventDefault();

			alert('Form submitted');
		});
}