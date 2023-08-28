/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
