/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import {TOOLTIP_CLASSNAMES_TYPES} from './tooltipClassnamesTypes';

export const TOOLTIP_CONTENT_RENDERER_TYPES = {
	[TOOLTIP_CLASSNAMES_TYPES.dropDownItem]: (
		<p className="m-0">
			To download an aggregate key, select keys with identical
			<b>{' Type, Start Date, End Date, '}</b>
			and
			<b>Instance Size</b>
		</p>
	),
};
