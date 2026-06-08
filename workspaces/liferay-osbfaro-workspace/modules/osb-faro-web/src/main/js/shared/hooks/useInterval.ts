/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';

export const useInterval = function useInterval<T>(
	tickFn: () => T,
	delay: number = 0
): void {
	let intervalId: ReturnType<typeof setInterval>;

	useEffect(() => {

		// eslint-disable-next-line react-hooks/exhaustive-deps
		intervalId = setInterval(tickFn, delay);

		return () => {
			clearInterval(intervalId);
		};
	}, [delay]);
};
