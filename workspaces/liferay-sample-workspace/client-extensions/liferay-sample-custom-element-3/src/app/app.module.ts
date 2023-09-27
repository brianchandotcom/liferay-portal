/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Injector, NgModule} from '@angular/core';
import {createCustomElement} from '@angular/elements';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';

@NgModule({
	bootstrap: [AppComponent],
	declarations: [AppComponent],
	imports: [BrowserModule],
	providers: [],
})
export class AppModule {
	constructor(private injector: Injector) {}

	ngDoBootstrap() {
		const AppComponentElement = createCustomElement(AppComponent, {
			injector: this.injector,
		});

		customElements.define(
			'liferay-sample-custom-element-3',
			AppComponentElement
		);
	}
}
