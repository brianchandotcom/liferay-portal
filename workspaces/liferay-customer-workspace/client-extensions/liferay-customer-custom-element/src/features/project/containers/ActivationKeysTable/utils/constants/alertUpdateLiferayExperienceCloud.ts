/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ALERT_DOWNLOAD_TYPE} from '~/features/project/utils/constants/alertDownloadType';
import i18n from '~/utils/I18n';

export const ALERT_UPDATE_LIFERAY_EXPERIENCE_CLOUD_STATUS = {
	[ALERT_DOWNLOAD_TYPE.success]: i18n.translate(
		'your-liferay-saas-environments-are-ready'
	),
};
