window.addEventListener('load', function () {
	if (document.querySelector('body.has-edit-mode-menu')) {
		return;
	}

	if (configuration.modalId) {
		if (configuration.contentInitEvent) {
			document.addEventListener(
				configuration.contentInitEvent,
				loadModalContent
			);
		}
		else {
			loadModalContent();
		}
	}
});

function loadModalContent() {
	var modalTriggers = document.querySelectorAll(
		'[href="#' + configuration.modalId + '"]'
	);
	var modal = document.getElementById(configuration.modalId);
	var modalContent = modal.firstElementChild;

	for (var i = 0; i < modalTriggers.length; i++) {
		modalTriggers[i].addEventListener('click', function () {
			Liferay.Util.openModal({
				bodyHTML: '<div></div>',
				containerProps: {
					className: '',
				},
				id: 'f-modal',
				onOpen: function () {
					var modalBody = document.querySelector(
						'#f-modal .liferay-modal-body'
					);
					modalBody.appendChild(modalContent);
				},
			});
		});
	}
}