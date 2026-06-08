/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

export const useDebounce = function useDebounce(value: any, delay: number) {
	const [debouncedValue, setDebouncedValue] = useState(value);

	useEffect(
		() => {
			const handler = setTimeout(() => {
				setDebouncedValue(value);
			}, delay);

			return () => {
				clearTimeout(handler);
			};
		},

		// This is required when the `object` has lost the
		// reference plus the values are the same, `useEffect`
		// uses `Object.is` or equivalent under the covers.
		// For some reason the reference is being lost.

		// eslint-disable-next-line react-hooks/exhaustive-deps
		typeof value === 'object' && value !== null
			? [...Object.keys(value), ...Object.values(value)]
			: [value]
	);

	return debouncedValue;
};
