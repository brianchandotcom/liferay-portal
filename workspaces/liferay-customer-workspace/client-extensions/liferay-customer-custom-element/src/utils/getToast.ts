/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '~/services/liferay';
import i18n from '~/utils/I18n';

interface IProps {
	message: string;
	title: string;
	type?: 'success' | 'danger' | 'info' | 'warning';
}

const openToast = ({message, title, type = 'success'}: IProps) =>
	Liferay.Util.openToast({
		message: i18n.translate(message),
		title: i18n.translate(title),
		type,
	});

export default openToast;
