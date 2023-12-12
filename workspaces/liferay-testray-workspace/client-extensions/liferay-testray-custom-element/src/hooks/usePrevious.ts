/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useRef} from 'react';

const usePrevious = <T = any>(value: T) => {
	const prevRef = useRef<T>();

	useEffect(() => {
		prevRef.current = value;
	}, [value]);

	return prevRef.current;
};

export default usePrevious;
