/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Directive, ElementRef} from '@angular/core';

@Directive({
	selector: '[scroll-to-bottom]',
})
export class ScrollToBottomDirective {
	constructor(private _el: ElementRef) {}

	public scrollToBottom() {
		const element: HTMLDivElement = this._el.nativeElement;

		element.scrollTop = Math.max(
			0,
			element.scrollHeight - element.offsetHeight
		);
	}
}
