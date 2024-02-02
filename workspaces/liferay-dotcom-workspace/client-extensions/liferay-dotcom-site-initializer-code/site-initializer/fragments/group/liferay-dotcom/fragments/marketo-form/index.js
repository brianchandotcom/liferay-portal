const mktoCallback = function (form) {
	var formEl = form.getFormElem()[0];

	var arrayify = getSelection.call.bind([].slice);

	var styledEls = arrayify(formEl.querySelectorAll('[style]')).concat(formEl);

	styledEls.forEach(function (el) {
		el.removeAttribute('style');
	});

	formEl.querySelectorAll('style').forEach(function (el) {
		el.remove();
	});

	var styleSheets = arrayify(document.styleSheets);

	styleSheets.forEach(function (stylesheet) {
		if (
			[mktoForms2BaseStyle, mktoForms2ThemeStyle].indexOf(
				stylesheet.ownerNode
			) != -1 ||
			formEl.contains(stylesheet.ownerNode)
		) {
			stylesheet.disabled = true;
		}
	});

	var buttonElem = form.getFormElem().find('button.mktoButton');

	if (buttonElem) {
		buttonElem.html(Liferay.Language.get(configuration.submitButtonText));
	}
};

MktoForms2.loadForm(configuration.podId, configuration.munchkinId, configuration.formId, mktoCallback);