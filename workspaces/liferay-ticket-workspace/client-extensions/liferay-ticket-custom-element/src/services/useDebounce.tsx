/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

// see https://github.com/tannerlinsley/react-query/issues/293
// see https://usehooks.com/useDebounce/

export default function useDebounce(value: string, delay: number) {

	// State and setters for debounced value

	const [debouncedValue, setDebouncedValue] = useState(value);

	useEffect(
		() => {

			// Update debounced value after delay

			const handler = setTimeout(() => {
				setDebouncedValue(value);
			}, delay);

			// Cancel the timeout if value changes (also on delay change or unmount)
			// This is how we prevent debounced value from updating if value is changed ...
			// .. within the delay period. Timeout gets cleared and restarted.

			return () => {
				clearTimeout(handler);
			};
		},
		[value, delay] // Only re-call effect if value or delay changes
	);

	return debouncedValue;
}
