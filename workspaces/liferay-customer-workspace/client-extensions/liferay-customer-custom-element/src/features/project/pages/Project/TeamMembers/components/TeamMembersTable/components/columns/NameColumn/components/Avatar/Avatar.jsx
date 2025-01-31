/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import md5 from 'md5';
import {memo} from 'react';
import {getInitials} from './utils/getInitials';

const AVATAR_SIZE_IN_PX = 40;

const Avatar = ({emailAddress, gravatarAPI, userName}) => {
	const emailAddressMD5 = md5(emailAddress);
	const uiAvatarURL = `https://ui-avatars.com/api/${getInitials(
		userName
	)}/128/0B5FFF/FFFFFF/2/0.33/true/true/true`;

	return (
		<img
			className="rounded-circle"
			height={AVATAR_SIZE_IN_PX}
			src={`${gravatarAPI}/${emailAddressMD5}?d=${encodeURIComponent(
				uiAvatarURL
			)}`}
			width={AVATAR_SIZE_IN_PX}
		/>
	);
};

export default memo(Avatar);
