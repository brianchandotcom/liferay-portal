/**
 * Inserts an item into a list at the specified index.
 * @param {Array} items - The list where the item will be inserted into.
 * @param {number} index The position where the item will be inserted.
 * @param {*} item - The item that will be inserted.
 * @return {Array}
 */
export const insertAtIndex = (
	items: any[],
	index: number,
	item: any
): any[] => {
	const beg = items.slice(0, index);
	const end = items.slice(index);

	return beg.concat(item, end);
};

/**
 * Removes an item at the specified index.
 * @param {Array} items - The list the where an item will be removed.
 * @param {number} index - The position where the item will be removed.
 * @return {Array}
 */
export const removeAtIndex = (items: any[], index: number): any[] => {
	const beg = items.slice(0, index);
	const end = items.slice(index + 1);

	return beg.concat(end);
};

/**
 * Remove an item from the from index and insert it back at the to index.
 * @param {Array} items - The list where the item will be inserted into.
 * @param {number} from - The position where the item will be removed.
 * @param {number} to - The position where the item will be inserted.
 * @return {Array}
 */
export const moveItem = (items: any[], from: number, to: number): any[] => {
	const item = items[from];

	const itemsWithItemRemoved = removeAtIndex(items, from);

	return insertAtIndex(itemsWithItemRemoved, to, item);
};

/**
 * Replaces an item in a list at the specified index.
 * @param {Array} list The list where an item will be replaced.
 * @param {number} index The position where the item will be replaced.
 * @param {*} item The item that will be added.
 * @return {Array}
 */
export function replaceAtIndex(list: any[], index: number, item: any): any[] {
	return Object.assign(list, {
		[index]: item
	});
}

/**
 * Replaces an item at the specified index with multiple items. Starts by
 * replacing the item and then inserting the rest of the items after the index.
 */
export const replaceWithMultipleAtIndex = (
	items: any[],
	list: any[],
	index: number
): any[] => [
	...list.slice(0, index),
	...items,
	...list.slice(index + 1, list.length)
];

/**
 * Get an array containing all the elements of arr1 that are
 * not in arr2 and vice-versa.
 * @returns {Array}
 */
export function getDifferences<T>(arr1: T[], arr2: T[]): T[] {
	const x = arr1.filter(x => !arr2.includes(x));
	const y = arr2.filter(x => !arr1.includes(x));

	return x.concat(y);
}
