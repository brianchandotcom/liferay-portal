/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	TOOLTIP_CLASSNAMES_TYPES,
	TOOLTIP_CONTENT_RENDERER_TYPES,
} from './constants';
import {getTooltipTitles} from './getTooltipTitles';

export function getTooltipContentRenderer(title) {
	const hasDropdownTooltip = title === TOOLTIP_CLASSNAMES_TYPES.dropDownItem;

	if (hasDropdownTooltip) {
		return TOOLTIP_CONTENT_RENDERER_TYPES[
			TOOLTIP_CLASSNAMES_TYPES.dropDownItem
		];
	}

	return getTooltipTitles(title);
}
