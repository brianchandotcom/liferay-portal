jest.unmock('lodash/debounce');

import debounce from '../debounce-decorator';
import {times} from 'lodash';

describe('debounce-decorator', () => {
	it('should return a deounce to be used as a decorator', () => {
		expect(typeof debounce(250)).toBe('function');
	});

	it('should debounce the class method', () => {
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

		jest.advanceTimersByTime(300);

		expect(callback).not.toHaveBeenCalled();

		jest.advanceTimersByTime(700);

		expect(callback).toHaveBeenCalled();
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

	it('should continually debounce a function that has been autobind', () => {
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

		times(5, () => {
			testDebounce.foo();
		});

		jest.advanceTimersByTime(300);

		expect(callback).not.toHaveBeenCalled();

		jest.advanceTimersByTime(700);

		expect(callback).toHaveBeenCalledTimes(1);
	});
});
