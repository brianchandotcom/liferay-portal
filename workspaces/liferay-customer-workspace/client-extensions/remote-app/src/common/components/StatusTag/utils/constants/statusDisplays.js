/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {STATUS_TAG_TYPES} from '../../../../../routes/customer-portal/utils/constants/statusTag';
import i18n from '../../../../I18n';
import {SLA_STATUS_TYPES} from '../../../../utils/constants';

export const STATUS_DISPLAY = {
	[SLA_STATUS_TYPES.active]: {
		displayType: 'success',
		label: i18n.translate('active'),
	},
	[SLA_STATUS_TYPES.expired]: {
		displayType: 'danger',
		label: i18n.translate('expired'),
	},
	[SLA_STATUS_TYPES.future]: {
		displayType: 'info',
		label: i18n.translate('future'),
	},
	[STATUS_TAG_TYPES.active]: {
		displayType: 'success',
		label: i18n.translate('active'),
	},
	[STATUS_TAG_TYPES.inProgress]: {
		displayType: 'warning',
		label: i18n.translate('in-progress'),
	},
	[STATUS_TAG_TYPES.invited]: {
		displayType: 'info',
		label: i18n.translate('invited'),
	},
	[STATUS_TAG_TYPES.notActivated]: {
		displayType: 'dark',
		label: i18n.translate('not-activated'),
	},
};
