/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useMemo, useState} from 'react';

import {useAppPropertiesContext} from '../contexts/AppPropertiesContext';
import ProvisioningLicenseKeys from '../services/liferay/rest/raysource/ProvisioningLicenseKeys';
import {getOrRequestToken} from '../services/liferay/security/auth/getOrRequestToken';

const useProvisioningLicenseKeys = () => {
	const [oAuthToken, setOAuthToken] = useState<string | null>(null);
	const {provisioningServerAPI} = useAppPropertiesContext();

	useEffect(() => {
		const fetchToken = async () => {
			const token = await getOrRequestToken();

			setOAuthToken(token);
		};

		fetchToken();
	}, []);

	const provisioningLicenseKeysService = useMemo(() => {
		if (!oAuthToken) {
			return null;
		}

		return new ProvisioningLicenseKeys({
			oAuthToken,
			provisioningServerAPI,
		});
	}, [oAuthToken, provisioningServerAPI]);

	return provisioningLicenseKeysService;
};

export default useProvisioningLicenseKeys;
