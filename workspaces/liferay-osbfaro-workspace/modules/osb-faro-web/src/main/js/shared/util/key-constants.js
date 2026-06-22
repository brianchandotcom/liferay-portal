export const ARROW_DOWN = 40;
export const ARROW_LEFT = 37;
export const ARROW_RIGHT = 39;
export const ARROW_UP = 38;
export const BACKSPACE = 8;
export const COMMA = 188;
export const ENTER = 13;
export const ESCAPE = 27;
export const SPACE = 32;
export const TAB = 9;

/**
 * Creates a decorator that only invokes the target function
 * if the key press event matches the passed keycode. Use
 * the key constants defined in this file.
 * @param {number} keyCode - The keyCode that invokes the function.
 * @returns {Function}
 */
export function onKey(keyCode) {
	return (target, key) => {
		const wrappedFn = target[key];

		return {
			value(event) {
				if (event && event.keyCode === keyCode) {
					wrappedFn.call(this, event);
				}
			}
		};
	};
}

/**
 * A decorator that only invokes a key press handler if the
 * enter key was pressed.
 */
export const onEnter = onKey(ENTER);
