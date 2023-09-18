/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import iconSVG from '@clayui/css/lib/images/icons/icons.svg';

import {Liferay} from '../services/liferay';

export default function getIconSpriteMap() {
	const pathThemeImages = Liferay.ThemeDisplay.getPathThemeImages();

	if (pathThemeImages) {
		return `${pathThemeImages}/clay/icons.svg`;
	}

	return iconSVG;
}
