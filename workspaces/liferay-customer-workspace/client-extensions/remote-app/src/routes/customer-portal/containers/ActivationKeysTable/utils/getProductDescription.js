/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '../../../../../common/I18n';

const COMPLIMENTARY = i18n.translate('complimentary');
const SUBSCRIPTION = i18n.translate('subscription');

export function getProductDescription(complimentary) {
	return complimentary ? COMPLIMENTARY : SUBSCRIPTION;
}
