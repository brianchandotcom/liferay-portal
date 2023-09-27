/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import md5 from 'md5';

import {getInitials} from '../../utils/util';

const AVATAR_SIZE_IN_PX = 40;

export function Avatar({
	emailAddress,
	gravatarAPI,
	initialImage,
	userName,
}: {
	emailAddress: string;
	gravatarAPI: string;
	initialImage?: string;
	userName: string;
}) {
	let emailAddressMD5;
	let uiAvatarURL = '';

	if (!initialImage) {
		emailAddressMD5 = md5(emailAddress);
		uiAvatarURL = `https://ui-avatars.com/api/${getInitials(
			userName
		)}/128/0B5FFF/FFFFFF/2/0.33/true/true/true`;
	}

	return (
		<img
			height={AVATAR_SIZE_IN_PX}
			src={
				initialImage ??
				`${gravatarAPI}/${emailAddressMD5}?d=${encodeURIComponent(
					uiAvatarURL
				)}`
			}
			width={AVATAR_SIZE_IN_PX}
		/>
	);
}
