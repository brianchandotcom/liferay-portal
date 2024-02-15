/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

const useDebounce = <T>(value: T, debounceTimer = 500) => {
	const [deboucedValue, setDebouncedValue] = useState<T>(value);

	useEffect(() => {
		const timeout = setTimeout(() => {
			setDebouncedValue(value);
		}, debounceTimer);

		return () => clearTimeout(timeout);
	}, [debounceTimer, value]);

	return deboucedValue;
};

export default useDebounce;
