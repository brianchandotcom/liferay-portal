import {Component, Input} from '@angular/core';

@Component({
	selector: 'app-root',
	styleUrls: ['./app.component.css'],
	templateUrl: './app.component.html',
})
export class AppComponent {
	@Input('title') title = 'liferay-sample-custom-element-3';
}
