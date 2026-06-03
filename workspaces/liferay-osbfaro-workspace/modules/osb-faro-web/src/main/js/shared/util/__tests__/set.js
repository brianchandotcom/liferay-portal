import {Set} from 'immutable';
import {toggle, toggleSingleton} from '../set';

describe('set', () => {
	describe('toggle', () => {
		it('should remove the item from the set', () => {
			expect(toggle(new Set([1, 2, 3]), 2)).toEqual(new Set([1, 3]));

			expect(toggle(new Set([1]), 1)).toEqual(new Set());
		});

		it('should add the item to the set', () => {
			expect(toggle(new Set([1, 2, 3]), 5)).toEqual(
				new Set([1, 2, 3, 5])
			);

			expect(toggle(new Set([]), 1)).toEqual(Set.of(1));
		});
	});

	describe('toggleSingleton', () => {
		it('should remove the item from the set', () => {
			expect(toggleSingleton(new Set([1]), 1)).toEqual(new Set());
		});

		it('should add the item to the set', () => {
			expect(toggleSingleton(new Set(), 5)).toEqual(Set.of(5));
		});

		it('should return a set of a single item', () => {
			expect(toggleSingleton(new Set([1, 2, 3]), 5)).toEqual(Set.of(5));
		});
	});
});
