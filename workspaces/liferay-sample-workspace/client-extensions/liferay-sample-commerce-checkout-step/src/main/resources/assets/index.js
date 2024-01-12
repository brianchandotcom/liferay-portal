/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function CommerceCheckoutStep() {
	const commerceCheckoutStepContainer = document.getElementById(
		'commerceCheckoutStepContainer'
	);
	const inputName =
		'_com_liferay_commerce_checkout_web_internal_portlet_CommerceCheckoutPortlet_pon';

	const newInput = document.createElement('input');
	newInput.setAttribute('type', 'text');
	newInput.setAttribute('name', inputName);
	newInput.setAttribute('id', inputName);
	newInput.setAttribute('placeholder', 'Purchase order number');

	commerceCheckoutStepContainer.appendChild(newInput);
}
