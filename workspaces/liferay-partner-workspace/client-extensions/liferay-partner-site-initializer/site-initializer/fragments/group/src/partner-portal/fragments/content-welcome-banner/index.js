/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const predissmissBanner = fragmentElement.querySelector('.pre-dismiss');
const posDissmissBanner = fragmentElement.querySelector('.pos-dismiss');
const dissmissButtonBanner = fragmentElement.querySelector('.on-click-button');
const isBannerClosed = sessionStorage.getItem(fragmentEntryLinkNamespace);

const toggleBanner = () => {
	predissmissBanner.classList.toggle('d-lg-flex');
	predissmissBanner.classList.toggle('d-none');

	posDissmissBanner.classList.toggle('d-none');
	posDissmissBanner.classList.toggle('d-lg-flex');

	dissmissButtonBanner.classList.toggle('d-none');
	dissmissButtonBanner.classList.toggle('d-flex');
};

fragmentElement.querySelector('.dismiss-button').onclick = () => {
	toggleBanner();
	sessionStorage.setItem(fragmentEntryLinkNamespace, true);
};

if (!isBannerClosed) {
	toggleBanner();
}
