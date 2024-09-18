/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../liferay/liferay';
import {isCloudEnvironment} from './util';

const CLOUD_SITE_NAME_INDEX = -1;
const LOCAL_SITE_NAME_INDEX = 2;

export function getSiteName() {
	const _isCloudEnvironment = isCloudEnvironment();

	const marketplaceURL = (
		_isCloudEnvironment
			? Liferay.ThemeDisplay.getURLHome()
			: Liferay.ThemeDisplay.getLayoutRelativeURL()
	).split('/');

	return marketplaceURL.at(
		_isCloudEnvironment ? CLOUD_SITE_NAME_INDEX : LOCAL_SITE_NAME_INDEX
	);
}

export function getSiteURL() {
	const layoutRelativeURL = Liferay.ThemeDisplay.getLayoutRelativeURL();

	return layoutRelativeURL.includes('web')
		? layoutRelativeURL.split('/').slice(0, 3).join('/')
		: '';
}
