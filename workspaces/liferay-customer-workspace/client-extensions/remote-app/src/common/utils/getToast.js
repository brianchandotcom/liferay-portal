/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import i18n from '../../common/I18n';

const openToast = (title, message, {type = 'success'} = {}) =>
	Liferay.Util.openToast({
		message: i18n.translate(message),
		title: i18n.translate(title),
		type,
	});

export default openToast;
