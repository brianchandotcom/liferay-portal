/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable no-undef */
const currentPath = Liferay.ThemeDisplay.getLayoutRelativeURL().split('/');
const callbackId = currentPath.at(-1);

const updateMDFDetailsSummary = async () => {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`/o/c/callbackformentries/${callbackId}?fields=koroneikiAccountToCallbackFormEntry.slaCurrent&nestedFields=koroneikiAccountToCallbackFormEntry`,
		{
			headers: {
				'accept': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
		}
	);

	if (response.ok) {
		const {
			koroneikiAccountToCallbackFormEntry: {slaCurrent},
		} = await response.json();
		const callbackParagraph =
			fragmentElement.querySelector('#callback-message');

		if (slaCurrent === 'Platinum Subscription') {
			callbackParagraph.innerHTML =
				"A Liferay Support member will contact you within 1 hour at the indicated phone number. Consider creating a <a href='https://help.liferay.com/hc'>support ticket</a> if you haven't already to help expedite a resolution.";

			return;
		}

		callbackParagraph.innerHTML =
			"While immediate callbacks are reserved for those with platinum level, a support member will get in touch within 1 business day during our <a href='https://web.liferay.com/support/coverage-areas-and-hours'>standard business hours</a> of your designated Support Center. In the meantime, consider creating a <a href='https://help.liferay.com/hc'>support ticket</a> which can help expedite a resolution.";

		return;
	}

	Liferay.Util.openToast({
		message: 'An unexpected error occured.',
		type: 'danger',
	});
};

if (layoutMode !== 'edit') {
	updateMDFDetailsSummary();
}
