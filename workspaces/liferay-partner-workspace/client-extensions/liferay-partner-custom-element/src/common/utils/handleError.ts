/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LiferayError} from '../enums/liferayError';
import {Liferay} from '../services/liferay';

const DEFAULT_MESSAGE = 'An unexpected error occured.';

type ToastErrorMessage = {
	[key in LiferayError]?: string;
};

const toastMessages: ToastErrorMessage = {};

export default function handleError(status: LiferayError) {
	Liferay.Util.openToast({
		message: toastMessages[status] || DEFAULT_MESSAGE,
		type: 'danger',
	});
}
