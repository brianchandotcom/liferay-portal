/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '~/utils/I18n';

export const paginationDeltas = [
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

export const paginationLabels = {
	paginationResults: i18n.translate('showing-x-to-x-of-x-entries'),
	perPageItems: i18n.translate('x-entries'),
	selectPerPageItems: i18n.translate('x-entries'),
};
