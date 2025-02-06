/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useState} from 'react';

export default function useSearchTerm(
	onSearch: (onSearchTerm: string) => void
): [string, (onSearchTerm: string) => void] {
	const [searchTerm, setSearchTerm] = useState('');

	const handleSearch = useCallback(
		(newSearchTerm: string) => {
			setSearchTerm(newSearchTerm);
			onSearch(newSearchTerm);
		},
		[onSearch]
	);

	return [searchTerm, handleSearch];
}
