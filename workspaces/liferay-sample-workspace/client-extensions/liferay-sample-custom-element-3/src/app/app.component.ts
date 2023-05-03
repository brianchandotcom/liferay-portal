import {Component, Input} from '@angular/core';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css'],
})
export class AppComponent {
	@Input('title') title = 'liferay-sample-custom-element-3';
}
