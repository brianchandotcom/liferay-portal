/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isEqual} from 'lodash';
import {ComponentType} from 'react';

export const getDisplayName = function getDisplayName(
	WrappedComponent: ComponentType<any>
): string {
	return WrappedComponent.displayName || WrappedComponent.name || 'Component';
};

type HasChanges = <T extends object>(
	prev: T,
	next: T,
	...keys: Array<keyof T | string>
) => boolean;

/**
 * Compare previous state or props object by provided keys to detect changes.
 */
export const hasChanges: HasChanges = function hasChanges(
	prev = {} as any,
	next = {} as any,
	...keys
) {
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
