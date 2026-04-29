/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '~/utils/I18n';
import getKebabCase from '~/utils/getKebabCase';

export function getEnvironmentType(productName) {
	const formatProductName = productName?.startsWith('Liferay Self-Hosted ')
		? productName.replace('Liferay Self-Hosted ', '')
		: productName?.substr(productName.indexOf(' ') + 1);

	return i18n.translate(getKebabCase(formatProductName));
}
