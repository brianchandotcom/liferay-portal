/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
