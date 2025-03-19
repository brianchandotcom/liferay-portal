/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useRef, useState} from 'react';

const INTERSECTION_OPTIONS = {
	root: null,
	threshold: 1.0,
};

export default function useIntersectionObserver() {
	const trackedRefCurrent = useRef<HTMLDivElement | null>(null);
	const [isIntersecting, setIsIntersecting] = useState(false);

	const memoizedSetIntersecting = useCallback((entities: any[]) => {
		const target = entities[0];

		setIsIntersecting(target.isIntersecting);
	}, []);

	const setTrackedRefCurrent = useCallback(
		(node: HTMLDivElement) => {
			const observer = new IntersectionObserver(
				memoizedSetIntersecting,
				INTERSECTION_OPTIONS
			);

			if (trackedRefCurrent.current) {
				observer.unobserve(trackedRefCurrent.current);
			}

			trackedRefCurrent.current = node;

			if (trackedRefCurrent.current) {
				observer.observe(trackedRefCurrent.current);
			}
		},
		[memoizedSetIntersecting]
	);

	return [setTrackedRefCurrent, isIntersecting];
}
