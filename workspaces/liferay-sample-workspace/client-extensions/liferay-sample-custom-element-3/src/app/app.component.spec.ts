import {TestBed} from '@angular/core/testing';

import {AppComponent} from './app.component';

describe('AppComponent', () => {
	beforeEach(async () => {
		await TestBed.configureTestingModule({
			declarations: [AppComponent],
		}).compileComponents();
	});

	it('creates the app', () => {
		const fixture = TestBed.createComponent(AppComponent);
		const app = fixture.componentInstance;
		expect(app).toBeTruthy();
	});

	it(`has as title 'liferay-sample-custom-element-3'`, () => {
		const fixture = TestBed.createComponent(AppComponent);
		const app = fixture.componentInstance;
		expect(app.title).toEqual('liferay-sample-custom-element-3');
	});

	it('renders title', () => {
		const fixture = TestBed.createComponent(AppComponent);
		fixture.detectChanges();
		const compiled = fixture.nativeElement as HTMLElement;
		expect(compiled.querySelector('.content span')?.textContent).toContain(
			'liferay-sample-custom-element-3 app is running!'
		);
	});
});
