/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const setGuestOrderEnabled = ({guestOrderEnabled}) => {
	const signInButton = document.querySelector('button.sign-in');

	if (signInButton && guestOrderEnabled) {
		const clonedSignInButton = signInButton.cloneNode(true);

		signInButton.parentElement.replaceChild(
			clonedSignInButton,
			signInButton
		);
		const performGuestSignIn = (event) => {
			event.preventDefault();

			window.Liferay.fire('perform-sign-in');

			return false;
		};

		clonedSignInButton.addEventListener('click', performGuestSignIn);
	}

	window.Liferay.detach('guest-order-enabled', setGuestOrderEnabled);
};

window.Liferay.on('guest-order-enabled', setGuestOrderEnabled);
