/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const USE_CAPTURE = {
	blur: true,
	error: true,
	focus: true,
	invalid: true,
	load: true,
	scroll: true,
};

/**
 * Checks if element is disabled or whether it exists in a disabled element tree
 * @param {!Element} element The DOM element to check.
 * @return {boolean}
 */
function isDisabled(node) {
	return !!(node.disabled || node.closest('[disabled]'));
}

/**
 * Listens to the specified event on the given DOM element, but only calls the
 * given callback listener when it's triggered by elements that match the
 * given selector or target element.
 * @param {!Element} element The DOM element the event should be listened on.
 * @param {string} eventName The name of the event to listen to.
 * @param {string} selector Css selector that should match the event for the
 *     listener to be triggered.
 * @param {!function(!Object)} callback Function to be called when the event
 *     is triggered. It will receive the normalized event object.
 * @return {function} Can be used to remove the listener.
 */
function delegate(element, eventName, selector, callback) {
	const eventHandler = (event) => {
		const {defaultPrevented, target} = event;

		if (defaultPrevented || (eventName === 'click' && isDisabled(target))) {
			return;
		}

		const delegateTarget = target.closest(selector);

		if (delegateTarget) {
			event.delegateTarget = delegateTarget;

			callback(event);
		}
	};

	element.addEventListener(eventName, eventHandler, !!USE_CAPTURE[eventName]);

	return {
		dispose() {
			element.removeEventListener(eventName, eventHandler);
		},
	};
}

export default delegate;
