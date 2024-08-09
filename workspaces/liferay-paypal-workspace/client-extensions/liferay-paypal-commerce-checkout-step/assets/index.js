/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {onGooglePayLoaded} from './googlepay.js';
import {onPaypalLoaded} from './paypal.js';

export default function CommerceCheckoutStep() {
	const clientId = document.getElementById('payment-client-id').value;

	const payPalSDKScript = document.createElement('script');
	payPalSDKScript.src = `https://www.paypal.com/sdk/js?client-id=${clientId.substring(3)}&currency=${Liferay.CommerceContext.currency.currencyCode}&components=buttons,googlepay&enable-funding=venmo&disable-funding=blik,sepa`;
	payPalSDKScript.setAttribute(
		'data-partner-attribution-id',
		'Liferay_SP_PPCP_API'
	);

	const googleSDKScript = document.createElement('script');
	googleSDKScript.src = 'https://pay.google.com/gp/p/js/pay.js';

	const payPalSDKPromise = loadScript(payPalSDKScript);
	const googleSDKPromise = loadScript(googleSDKScript);

	const commerceCheckoutStepContainer = document.getElementById(
		'_com_liferay_commerce_checkout_web_internal_portlet_CommerceCheckoutPortlet_commerceCheckoutStepContainer'
	);

	const payPalButtonContainerDivElement = document.createElement('div');
	payPalButtonContainerDivElement.setAttribute(
		'id',
		'paypal-button-container'
	);

	const resultMessageElement = document.createElement('p');
	resultMessageElement.setAttribute('id', 'result-message');

	payPalButtonContainerDivElement.appendChild(resultMessageElement);
	commerceCheckoutStepContainer.appendChild(payPalButtonContainerDivElement);

	return Promise.all([payPalSDKPromise, googleSDKPromise])
		.then(() => {
			onPaypalLoaded();
			onGooglePayLoaded();
		})
		.catch((error) => {
			console.error('Error loading script:', error);
		});
}

function loadScript(script) {
	return new Promise((resolve, reject) => {
		script.onload = resolve;
		script.onerror = reject;
		document.head.appendChild(script);
	});
}
