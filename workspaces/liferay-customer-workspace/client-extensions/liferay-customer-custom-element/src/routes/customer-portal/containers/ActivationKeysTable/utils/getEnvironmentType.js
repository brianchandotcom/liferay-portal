/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '../../../../../common/I18n';
import getKebabCase from '../../../../../common/utils/getKebabCase';

export function getEnvironmentType(productName) {
	const formatProductName = productName?.substr(
		productName?.indexOf(' ') + 1
	);

	const translateProductName = i18n.translate(
		getKebabCase(formatProductName)
	);

	return translateProductName;
}
