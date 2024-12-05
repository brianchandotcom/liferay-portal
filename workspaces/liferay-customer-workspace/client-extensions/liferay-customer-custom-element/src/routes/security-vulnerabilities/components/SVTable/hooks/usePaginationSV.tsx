/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';
import i18n from '~/common/I18n';

export default function usePagination() {
    const labels = {
        paginationResults: i18n.translate('showing-x-to-x-of-x-entries'),
        perPageItems: i18n.translate('x-entries'),
        selectPerPageItems: i18n.translate('x-entries')
    }

	const [pageSize, setPageSize] = useState<number>(15);

	const [page, setPage] = useState<number>(1);

	const deltas = [
		{
			label: 15,
		},
		{
			label: 30,
		},
		{
			label: 45,
		},
		{
			label: 60,
		},
	];

	return {
		activeDelta: pageSize,
		activePage: page,
		deltas,
        labels,
		onDeltaChange: setPageSize,
		onPageChange: setPage,
	};
}
