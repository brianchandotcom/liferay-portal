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

import {default as delegate} from './delegate.es';

const KEYCODES = {
	ENTER: 13,
	SPACE: 32,
};
class DropdownProvider {
	EVENT_HIDDEN = 'liferay.dropdown.hidden';
	EVENT_HIDE = 'liferay.dropdown.hide';
	EVENT_SHOW = 'liferay.dropdown.show';
	EVENT_SHOWN = 'liferay.dropdown.shown';

	constructor(
		triggerSelector,
		menuSelector,
		cssClass,
		scopeToDocument,
		showCallback,
		hideCallback
	) {
		this.cssClass = cssClass;
		this.menuSelector = menuSelector;
		this.triggerSelector = triggerSelector;
		this.scopeToDocument = scopeToDocument;
		this.showCallback = showCallback;
		this.hideCallback = hideCallback;

		delegate(document.body, 'click', triggerSelector, this._onTriggerClick);

		delegate(document.body, 'keydown', triggerSelector, this._onKeyDown);
	}

	hide = ({menu, trigger}) => {
		if (menu && !trigger) {
			trigger = this._getTrigger(menu);
		}

		if (!menu) {
			menu = this._getMenu(trigger);
		}

		if (!menu.classList.contains(this.cssClass)) {
			return;
		}

		Liferay.fire(this.EVENT_HIDE, {menu, trigger});

		trigger.parentElement.classList.remove(this.cssClass);
		trigger.setAttribute('aria-expanded', false);

		menu.classList.remove(this.cssClass);

		Liferay.fire(this.EVENT_HIDDEN, {menu, trigger});

		if (this.hideCallback) {
			this.hideCallback(menu, trigger);
		}
	};

	show = ({menu, trigger}) => {
		if (menu && !trigger) {
			trigger = this._getTrigger(menu);
		}

		if (!menu) {
			menu = this._getMenu(trigger);
		}

		if (menu.classList.contains(this.cssClass)) {
			return;
		}

		Liferay.fire(this.EVENT_SHOW, {menu, trigger});

		trigger.parentElement.classList.add(this.cssClass);
		trigger.setAttribute('aria-expanded', true);

		const clickOutsideHandler = (event) => {
			if (
				!menu.contains(event.target) &&
				!trigger.contains(event.target)
			) {
				this.hide({menu, trigger});

				document.removeEventListener('mousedown', clickOutsideHandler);
				document.removeEventListener('keypress', clickOutsideHandler);
			}
		};

		document.addEventListener('mousedown', clickOutsideHandler);
		document.addEventListener('keypress', (event) => {
			if (event.key === 'Enter' || event.key === ' ') {
				clickOutsideHandler(event);
			}
		});

		menu.classList.add(this.cssClass);

		Liferay.fire(this.EVENT_SHOWN, {menu, trigger});

		if (this.showCallback) {
			this.showCallback(menu, trigger);
		}
	};

	_getMenu(trigger) {
		if (this.scopeToDocument) {
			return document.querySelector(this.menuSelector);
		}

		return trigger.parentElement.querySelector(this.menuSelector);
	}

	_getTrigger(menu) {
		return menu.parentElement.querySelector(this.triggerSelector);
	}

	_onKeyDown = (event) => {
		if (
			event.keyCode === KEYCODES.ENTER ||
			event.keyCode === KEYCODES.SPACE
		) {
			this._onTriggerClick(event);
		}
	};

	_onTriggerClick = (event) => {
		const trigger = event.delegateTarget;

		if (trigger.tagName === 'A') {
			event.preventDefault();
		}

		const menu = this._getMenu(trigger);

		if (menu) {
			if (menu.classList.contains(this.cssClass)) {
				this.hide({menu, trigger});
			}
			else {
				this.show({menu, trigger});
			}
		}
	};
}

export default DropdownProvider;
