/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function CommerceCheckoutStep() {
	const commerceCheckoutStepContainer = document.getElementById(
		'_com_liferay_commerce_checkout_web_internal_portlet_CommerceCheckoutPortlet_commerceCheckoutStepContainer'
	);

	const newInput = document.createElement('input');

	const inputName =
		'_com_liferay_commerce_checkout_web_internal_portlet_CommerceCheckoutPortlet_pon';

	newInput.setAttribute('id', inputName);
	newInput.setAttribute('name', inputName);

	newInput.setAttribute('placeholder', 'Purchase order number');
	newInput.setAttribute('type', 'text');

	commerceCheckoutStepContainer.appendChild(newInput);
}
