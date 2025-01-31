/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getPascalCase} from '~/features/project/utils/getPascalCase';
import i18n from '~/utils/I18n';
import getKebabCase from '~/utils/getKebabCase';

export function getProductName(activationKey) {
	const formatProductName = getPascalCase(activationKey.productName).replace(
		'Production',
		'Prod'
	);

	const translateProductName = i18n.translate(
		getKebabCase(formatProductName)
	);

	return translateProductName;
}
