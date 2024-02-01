/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	useSearchParams,
} from 'react-router-dom';

const useQueryParams = () => {
	const [searchParams] = useSearchParams();
	const currentPage = searchParams.get('page');

	const pageSize = searchParams.get('pageSize');


	return {
		currentPage,
		pageSize,
	};
};

export default useQueryParams;
