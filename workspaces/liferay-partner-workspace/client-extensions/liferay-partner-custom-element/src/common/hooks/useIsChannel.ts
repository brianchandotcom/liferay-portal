/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import UserAccount from '../interfaces/userAccount';
import {LiferayAPIs} from '../services/liferay/common/enums/apis';
import useGet from '../services/liferay/object/useGet';

export default function useIsChannel() {
	const {data: userAccount} = useGet<UserAccount>(
		`/o/${LiferayAPIs.HEADERLESS_ADMIN_USER}/my-user-account`
	);

	const isChannel =
		userAccount && Boolean(userAccount?.organizationBriefs.length);

	return {isChannel};
}
