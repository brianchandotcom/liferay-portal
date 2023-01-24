/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
