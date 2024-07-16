/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';

export default function useSearchTerm(onSearch: (searchTerm: string) => void) {
	const [lastSearchedTerm, setLastSearchedTerm] = useState('');

	return [
		lastSearchedTerm,
		(searchTerm: string) => {
			if (searchTerm !== lastSearchedTerm) {
				onSearch(searchTerm);
				setLastSearchedTerm(searchTerm);
			}
		},
	];
}
