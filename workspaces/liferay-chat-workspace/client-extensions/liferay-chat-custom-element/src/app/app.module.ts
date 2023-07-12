import {Injector, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {createCustomElement} from '@angular/elements';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {WindowComponent} from './components/chat/window/window.component';
import {ScrollToBottomDirective} from './directives/scroll-to-bottom.directive';
import {MatListModule} from '@angular/material/list';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatTabsModule} from '@angular/material/tabs';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatCardModule} from '@angular/material/card';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {
	FaIconLibrary,
	FontAwesomeModule,
} from '@fortawesome/angular-fontawesome';
import {fas} from '@fortawesome/free-solid-svg-icons';
import {DirectMessageWindowComponent} from './components/chat/direct-message-window/direct-message-window.component';
import {MatBadgeModule} from '@angular/material/badge';
import {NgxDocViewerModule} from 'ngx-doc-viewer';
import {AttachmentViewComponent} from './components/chat/attachment-view/attachment-view.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {NgAudioRecorderModule} from 'ng-audio-recorder';
import {NgxNotifierModule} from 'ngx-notifier';
import {VoiceNoteComponent} from './components/chat/voice-note/voice-note.component';
import {MatDialogModule} from '@angular/material/dialog';

@NgModule({
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
	bootstrap: [],
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
