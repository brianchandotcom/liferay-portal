/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const url = new URL(window.location.href);

const redirectURL = url.searchParams.get('redirect_url');

if (redirectURL) {
	window.location.href = redirectURL;
}