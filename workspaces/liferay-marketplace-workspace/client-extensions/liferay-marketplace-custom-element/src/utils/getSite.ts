/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../liferay/liferay';
import {isCloudEnvironment} from './util';

const getSiteName = () => {
	const isCloundEnvironment = isCloudEnvironment();
	const marketplaceUrl = (
		isCloundEnvironment
			? Liferay.ThemeDisplay.getURLHome()
			: Liferay.ThemeDisplay.getLayoutRelativeURL()
	).split('/');
	const index = isCloundEnvironment ? -1 : 2;

	return marketplaceUrl.at(index);
};

export {getSiteName};
