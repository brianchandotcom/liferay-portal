/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function main() {
	const EMAIL_REGEX = /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/g;
	const EMAIL_INPUT_ID = '_com_liferay_login_web_portlet_LoginPortlet_login';
	const PASSWORD_INPUT_ID =
		'_com_liferay_login_web_portlet_LoginPortlet_password';

	const emailInput = document.getElementById(EMAIL_INPUT_ID);
	const passwordInput = document.getElementById(PASSWORD_INPUT_ID);
	const [emailLabel, passwordLabel] = document.querySelectorAll(
		'div.form-group label'
	);

	const TEXT_VARIANT = {
		danger: 'text-danger',
		success: 'text-success',
	};

	const isEmailValid = (email) => {
		const regex = new RegExp(EMAIL_REGEX);

		return regex.test(email);
	};

	function onInputChange(element, validation) {
		if (validation) {
			element.classList.remove(TEXT_VARIANT.danger);
			element.classList.add(TEXT_VARIANT.success);

			return;
		}

		element.classList.remove(TEXT_VARIANT.success);
		element.classList.add(TEXT_VARIANT.danger);
	}

	emailInput.addEventListener('change', () =>
		onInputChange(emailLabel, isEmailValid(emailInput.value))
	);

	passwordInput.addEventListener('change', () =>
		onInputChange(passwordLabel, passwordInput.value !== '')
	);
}

if (!themeDisplay.isSignedIn()) {
	main();
}
