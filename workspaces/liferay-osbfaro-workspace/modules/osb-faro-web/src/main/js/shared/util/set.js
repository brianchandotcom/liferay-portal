import {Set} from 'immutable';

/**
 * Removes the item from the set if it exists, or adds it
 * if it does not.
 */
export function toggle(set, item) {
	return set.has(item) ? set.delete(item) : set.add(item);
}

/**
 * Returns an empty set if the item exists, or returns a
 * set of just that item if it does not.
 */
export function toggleSingleton(set, item) {
	return set.has(item) ? new Set() : Set.of(item);
}
