/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import ProvisioningKoroneikiOAuth2 from '../../../services/oauth/ProvisioningKoroneikiOAuth2';

const useProvisioningKoroneikiOAuth2 = () => {
	const provisioningKoroneikiOAuth2 = useMemo(
		() => new ProvisioningKoroneikiOAuth2(),
		[]
	);

	return provisioningKoroneikiOAuth2;
};

export default useProvisioningKoroneikiOAuth2;
