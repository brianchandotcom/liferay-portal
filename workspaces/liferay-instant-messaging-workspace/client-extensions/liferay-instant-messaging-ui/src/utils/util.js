/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function showError(title, message) {
	Liferay.Util.openToast({message, title, type: 'danger'});
}

export function showSuccess(
	title,
	message = 'The request has been successfully completed.'
) {
	Liferay.Util.openToast({message, title, type: 'success'});
}

export function showWarning(
	title,
	message = 'The request has been successfully completed.'
) {
	Liferay.Util.openToast({message, title, type: 'warning'});
}

export function getSelectedLanguage() {
	return Liferay.ThemeDisplay.getLanguageId();
}

export function getUserId() {
	return Liferay.ThemeDisplay.getUserId();
}

export function getCurrentSite() {
	return Liferay.ThemeDisplay.getSiteGroupId();
}
