/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '../../../../../../common/I18n';

export const ACTIVATION_STATUS = {
	activated: {
		color: 'success',
		id: 'activated',
		title: i18n.translate('activated'),
	},
	all: {
		color: 'none',
		id: 'all',
		title: i18n.translate('all'),
	},
	expired: {
		color: 'danger',
		id: 'expired',
		title: i18n.translate('expired'),
	},
	notActivated: {
		color: 'info',
		id: 'notActivated',
		title: i18n.translate('not-activated'),
	},
};
