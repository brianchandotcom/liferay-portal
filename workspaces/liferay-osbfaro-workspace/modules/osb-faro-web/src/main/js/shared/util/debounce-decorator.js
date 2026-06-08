/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {debounce} from 'lodash';

export default function debounceDecorate(...args) {
	return (target, key, descriptor) => {
		const {get} = descriptor;

		return {
			get() {
				const value = get ? get.apply(this) : descriptor.value;

				const debounceFn = debounce(value, ...args);

				Object.defineProperty(this, key, {
					get() {
						return debounceFn;
					},
				});

				return debounceFn;
			},
		};
	};
}
