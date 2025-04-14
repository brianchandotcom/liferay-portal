/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IOption} from '~/utils/types';

function containsOption(
	options: IOption[],
	searchOption?: {key: string; name: string}
): boolean {
	if (!options.length || !searchOption) {
		return false;
	}

	return Boolean(
		options.filter((option) => option.value === searchOption.key).length
	);
}

export {containsOption};
