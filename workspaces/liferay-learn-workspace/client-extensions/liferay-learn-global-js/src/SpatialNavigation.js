/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import SpatialNavigation from 'spatial-navigation-js';

class SpatialNavigationProvider {
	FOCUS_CSS_CLASS = 'focusable';

	constructor(focusableLinkSelector) {
		this.focusableLinkSelector = focusableLinkSelector;

		window.addEventListener('load', () => {
			SpatialNavigation.init();
			SpatialNavigation.add({
				selector: '.' + this.FOCUS_CSS_CLASS,
			});
		});
	}

	addFocusableClasses = (focusableLinkParent) =>
		this.toggleFocusableClasses(focusableLinkParent, true);

	removeFocusableClasses = (focusableLinkParent) =>
		this.toggleFocusableClasses(focusableLinkParent, false);

	toggleFocusableClasses = (focusableLinkParent, add) => {
		const focusableLinks = focusableLinkParent.querySelectorAll(
			this.focusableLinkSelector
		);

		for (let i = 0; i < focusableLinks.length; i++) {
			const focusableLink = focusableLinks[i];

			if (add) {
				focusableLink.classList.add(this.FOCUS_CSS_CLASS);
			}
			else {
				focusableLink.classList.remove(this.FOCUS_CSS_CLASS);
			}
		}
		SpatialNavigation.makeFocusable();
	};
}

export default SpatialNavigationProvider;
