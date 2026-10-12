/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import 'clarity-solution-custom-element-distributors-map-osm-with-slots';

// The page editor owns the drop zone's DOM in edit mode, so the items are only
// moved when the page is viewed. Items added later, for example by a "Load
// More" pagination, are not moved.

if (layoutMode !== 'edit') {
	const items = fragmentElement.querySelector(
		'.distributors-map-osm-with-slots-collection-items'
	);

	fragmentElement
		.querySelector(
			'clarity-solution-custom-element-distributors-map-osm-with-slots'
		)
		.append(...items.querySelectorAll('map-item'));

	items.hidden = true;
}
