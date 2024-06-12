/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* global MktoForms2, mktoForms2BaseStyle, mktoForms2ThemeStyle*/

/* eslint no-undef: "error"*/

const mktoCallback = function (form) {
	const formElement = form.getFormElem()[0];

	const arrayify = getSelection.call.bind([].slice);

	const styledEls = arrayify(formElement.querySelectorAll('[style]')).concat(
		formElement
	);

	styledEls.forEach((element) => {
		element.removeAttribute('style');
	});

	formElement.querySelectorAll('style').forEach((element) => {
		element.remove();
	});

	const styleSheets = arrayify(document.styleSheets);

	styleSheets.forEach((stylesheet) => {
		if (
			[mktoForms2BaseStyle, mktoForms2ThemeStyle].indexOf(
				stylesheet.ownerNode
			) !== -1 ||
			formElement.contains(stylesheet.ownerNode)
		) {
			stylesheet.disabled = true;
		}
	});

	const buttonElem = form.getFormElem().find('button.mktoButton');

	if (buttonElem) {
		buttonElem.html(Liferay.Language.get(configuration.submitButtonText));
	}
};

if (MktoForms2) {
	MktoForms2.loadForm(
		configuration.podId,
		configuration.munchkinId,
		configuration.formId,
		mktoCallback
	);
}
