import {fireEvent, screen} from '@testing-library/react';

const LIST_CLIENT_HEIGHT = 100;

const LIST_SCROLL_HEIGHT = 1000;

/**
 * jsdom lays nothing out, so every list reports a zero height and Clay's
 * infinite scroll sees it as already scrolled to the bottom. Give lists a
 * scrollable height so that only `scrollListToBottom` reaches the end.
 * Returns a function that restores the original geometry.
 */
export function mockListGeometry() {
	const spies = [
		jest
			.spyOn(HTMLElement.prototype, 'clientHeight', 'get')
			.mockReturnValue(LIST_CLIENT_HEIGHT),
		jest
			.spyOn(HTMLElement.prototype, 'scrollHeight', 'get')
			.mockReturnValue(LIST_SCROLL_HEIGHT),
	];

	return () => spies.forEach((spy) => spy.mockRestore());
}

/**
 * Opens the autocomplete list. Pickers share the combobox role, but only
 * the autocomplete renders it on a text input.
 */
export function focusAutocompleteInput() {
	const input = screen
		.getAllByRole('combobox')
		.find((element) => element.tagName === 'INPUT');

	fireEvent.focus(input);
}

export function mockPaginatedFieldValues(total = 25) {
	const values = Array.from({length: total}, (_, index) => `Value ${index}`);

	return ({delta, page}) =>
		Promise.resolve({
			items: values.slice((page - 1) * delta, page * delta),
			total,
		});
}

export function scrollListToBottom() {
	const list = screen.getByRole('listbox');

	list.scrollTop = LIST_SCROLL_HEIGHT - LIST_CLIENT_HEIGHT;

	fireEvent.scroll(list);
}

/**
 * Waits for real options; Clay renders its "Loading" placeholder with the
 * option role too.
 */
export function waitForListOptions() {
	return screen.findAllByRole('option', {name: /^Value/});
}
