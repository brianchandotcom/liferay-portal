/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MutableRefObject, useEffect, useState} from 'react';

export const useResize = function useResize(
	elementRef: MutableRefObject<any> | null
): [number, number] {
	const [size, setSize] = useState<[number, number]>([0, 0]);

	useEffect(() => {
		const handleResize = () =>
			setSize([
				elementRef?.current?.clientWidth ?? window.innerWidth,
				elementRef?.current?.clientHeight ?? window.innerHeight,
			]);

		handleResize();

		window.addEventListener('resize', handleResize);

		return () => window.removeEventListener('resize', handleResize);
	}, [elementRef]);

	return size;
};
