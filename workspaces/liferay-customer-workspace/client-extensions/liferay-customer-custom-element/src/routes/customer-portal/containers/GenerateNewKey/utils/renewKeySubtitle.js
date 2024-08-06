/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '~/common/I18n';

const getRenewKeySubtitle = (state) => {
	const keyCount = state?.activationKeys?.length;
	const keyType = state?.activationKeys[0]?.complimentary
		? 'complimentary'
		: 'subscription';
	const translationKey = keyCount === 1 ? 'renew-x-x-key' : 'renew-x-x-keys';

	return i18n.sub(translationKey, [keyCount, keyType]);
};

export {getRenewKeySubtitle};
