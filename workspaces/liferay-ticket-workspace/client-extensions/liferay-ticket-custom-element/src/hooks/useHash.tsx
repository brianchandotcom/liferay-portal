/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';

export function useHash(defaultHash: string) {
	const [hash, setHash] = useState(() => window.location.hash || defaultHash);

	const hashChangeHandler = useCallback(() => {
		setHash(window.location.hash);
	}, []);

	useEffect(() => {
		window.addEventListener('hashchange', hashChangeHandler);

		return () => {
			window.removeEventListener('hashchange', hashChangeHandler);
		};
	}, [hashChangeHandler]);

	const updateHash = useCallback(
		(newHash: string) => {
			if (newHash !== hash) {
				window.location.hash = newHash;
			}
		},
		[hash]
	);

	return {hash, updateHash};
}
