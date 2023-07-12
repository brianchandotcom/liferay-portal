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

import {HttpClientModule} from '@angular/common/http';
import {Injector, NgModule} from '@angular/core';
import {createCustomElement} from '@angular/elements';
import {FormsModule} from '@angular/forms';
import {MatBadgeModule} from '@angular/material/badge';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatDialogModule} from '@angular/material/dialog';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatListModule} from '@angular/material/list';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatTabsModule} from '@angular/material/tabs';
import {MatToolbarModule} from '@angular/material/toolbar';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
	FaIconLibrary,
	FontAwesomeModule,
} from '@fortawesome/angular-fontawesome';
import {fas} from '@fortawesome/free-solid-svg-icons';
import {NgAudioRecorderModule} from 'ng-audio-recorder';
import {NgxDocViewerModule} from 'ngx-doc-viewer';
import {NgxNotifierModule} from 'ngx-notifier';

import {AppComponent} from './app.component';
import {AttachmentViewComponent} from './components/chat/attachment-view/attachment-view.component';
import {DirectMessageWindowComponent} from './components/chat/direct-message-window/direct-message-window.component';
import {VoiceNoteComponent} from './components/chat/voice-note/voice-note.component';
import {WindowComponent} from './components/chat/window/window.component';
import {ScrollToBottomDirective} from './directives/scroll-to-bottom.directive';

@NgModule({
	bootstrap: [],
	declarations: [
		AppComponent,
		WindowComponent,
		ScrollToBottomDirective,
		DirectMessageWindowComponent,
		AttachmentViewComponent,
		VoiceNoteComponent,
	],
	imports: [
		BrowserModule,
		HttpClientModule,
		FormsModule,
		BrowserAnimationsModule,
		MatSlideToggleModule,
		MatListModule,
		MatTabsModule,
		MatButtonModule,
		MatToolbarModule,
		MatCardModule,
		MatProgressBarModule,
		MatIconModule,
		FontAwesomeModule,
		MatInputModule,
		MatBadgeModule,
		NgxDocViewerModule,
		NgxNotifierModule,
		MatProgressSpinnerModule,
		NgAudioRecorderModule,
		MatButtonModule,
		MatDialogModule,
	],
	providers: [],
})
export class AppModule {
	ngDoBootstrap() {}

	constructor(private injector: Injector, library: FaIconLibrary) {
		library.addIconPacks(fas);
		const ChatWindowComponent = createCustomElement(WindowComponent, {
			injector: this.injector,
		});
		customElements.define('liferay-chat-window', ChatWindowComponent);
	}
}
