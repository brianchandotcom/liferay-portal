/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useRef, useState} from 'react';

const INTERSECTION_OPTIONS = {
	root: null,
	threshold: 1.0,
};

export default function useIntersectionObserver() {
	const trackedRefCurrent = useRef(null);
	const [isIntersecting, setIsIntersecting] = useState(false);

	const memoizedSetIntersecting = useCallback((entities: any[]) => {
		const target = entities[0];

		setIsIntersecting(target.isIntersecting);
	}, []);

	useEffect(() => {
		const observer = new IntersectionObserver(
			memoizedSetIntersecting,
			INTERSECTION_OPTIONS
		);

		if (trackedRefCurrent.current) {
			observer.observe(trackedRefCurrent.current);
		}

		return () => {
			if (trackedRefCurrent.current) {

				// eslint-disable-next-line react-hooks/exhaustive-deps
				observer.unobserve(trackedRefCurrent.current);
			}
		};
	}, [memoizedSetIntersecting]);

	return [trackedRefCurrent, isIntersecting];
}
