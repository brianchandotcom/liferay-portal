import {ComponentType} from 'react';
import {isEqual} from 'lodash';

export const getDisplayName = (WrappedComponent: ComponentType<any>): string =>
	WrappedComponent.displayName || WrappedComponent.name || 'Component';

type HasChanges = <T extends object>(
	prev: T,
	next: T,
	...keys: Array<keyof T | string>
) => boolean;

/**
 * Compare previous state or props object by provided keys to detect changes.
 */
export const hasChanges: HasChanges = (
	prev = {} as any,
	next = {} as any,
	...keys
) => {
	for (const key of keys) {
		if ((key as string) in next) {
			const newVal = (next as Record<string, unknown>)[key as string];

			const prevVal = (prev as Record<string, unknown>)[key as string];

			if (!isEqual(newVal, prevVal)) {
				return true;
			}
		}
	}

	return false;
};
