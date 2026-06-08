/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

declare module 'redux-toolbox';

declare module 'clipboard' {
	interface ClipboardEvent {
		action: string;
		clearSelection: () => void;
		text: string;
		trigger: Element;
	}

	type ClipboardEventName = 'success' | 'error';

	class Clipboard {
		constructor(

			// eslint-disable-next-line no-undef
			selector: string | Element | NodeListOf<Element>,
			options?: {[key: string]: any}
		);
		on(
			event: ClipboardEventName,
			callback: (event: ClipboardEvent) => void
		): this;
		destroy(): void;
	}

	export default Clipboard;
}
