/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getAttachmentFormattedDateTime(timeStamp) {
	const date = new Date(timeStamp);

	const formattedDate = `${date.getDate()}\u0020${date.toLocaleString(
		'default',
		{month: 'short'}
	)},\u0020${date.getFullYear()}\u0020-\u0020${date.toLocaleTimeString()}\u0020UTC`;

	return formattedDate;
}
