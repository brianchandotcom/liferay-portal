import autobind from 'autobind-decorator';
import {times} from 'lodash';

import debounce from '../debounce-decorator';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

jest.unmock('lodash/debounce');

describe('debounce-decorator', () => {
	beforeAll(() => {
		jest.useRealTimers();
	});

	afterAll(() => {
		jest.useFakeTimers();
	});

	it('returns a deounce to be used as a decorator', () => {
		expect(typeof debounce(250)).toBe('function');
	});

	it('debounces the class method', (done) => {
		expect.assertions(3);

		class TestDebounce {
			constructor(callback) {
				this._callback = callback;
			}

			@debounce(750)
			foo() {
				this._callback();
			}
		}

		const callback = jest.fn();

		const testDebounce = new TestDebounce(callback);

		testDebounce.foo();

		expect(callback).not.toHaveBeenCalled();

		window.setTimeout(() => expect(callback).not.toHaveBeenCalled(), 300);

		window.setTimeout(() => {
			expect(callback).toHaveBeenCalled();

			done();
		}, 1000);
	});

	it('has a new debounced function for each instance', () => {
		class TestDebounce {
			@debounce()
			foo() {}
		}

		const a = new TestDebounce();
		const b = new TestDebounce();

		expect(a.foo).not.toEqual(b.foo);
	});

	it('debounces a function that has been autobind', (done) => {
		expect.assertions(3);

		class TestDebounce {
			constructor(callback) {
				this._callback = callback;
			}

			@debounce(750)
			@autobind
			foo() {
				this._callback();
			}
		}

		const callback = jest.fn();

		const testDebounce = new TestDebounce(callback);

		testDebounce.foo();

		expect(callback).not.toHaveBeenCalled();

		window.setTimeout(() => expect(callback).not.toHaveBeenCalled(), 300);

		window.setTimeout(() => {
			expect(callback).toHaveBeenCalled();

			done();
		}, 1000);
	});

	it('continuallies debounce a function that has been autobind', (done) => {
		expect.assertions(3);

		class TestDebounce {
			constructor(callback) {
				this._callback = callback;
			}

			@debounce(750)
			@autobind
			foo() {
				this._callback();
			}
		}

		const callback = jest.fn();

		const testDebounce = new TestDebounce(callback);

		times(5, () => {
			testDebounce.foo();
		});

		expect(callback).not.toHaveBeenCalled();

		window.setTimeout(() => expect(callback).not.toHaveBeenCalled(), 300);

		window.setTimeout(() => {
			expect(callback).toHaveBeenCalled();

			done();
		}, 1000);
	});
});
