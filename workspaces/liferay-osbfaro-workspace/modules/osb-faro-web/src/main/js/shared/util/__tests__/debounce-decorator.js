jest.unmock('lodash/debounce');

import autobind from 'autobind-decorator';
import debounce from '../debounce-decorator';
import {times} from 'lodash';

describe('debounce-decorator', () => {
	beforeAll(() => {
		jest.useRealTimers();
	});

	afterAll(() => {
		jest.useFakeTimers();
	});

	it('should return a deounce to be used as a decorator', () => {
		expect(typeof debounce(250)).toBe('function');
	});

	it('should debounce the class method', done => {
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

	it('should have a new debounced function for each instance', () => {
		class TestDebounce {
			@debounce()
			foo() {}
		}

		const a = new TestDebounce();
		const b = new TestDebounce();

		expect(a.foo).not.toEqual(b.foo);
	});

	it('should debounce a function that has been autobind', done => {
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

	it('should continually debounce a function that has been autobind', done => {
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
