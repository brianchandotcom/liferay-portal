/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import {useCustomerPortal} from '~/routes/customer-portal/context';
import {useOnboarding} from '~/routes/onboarding/context';

import {useAppPropertiesContext} from '../contexts/AppPropertiesContext';
import ProvisioningLicenseKeys from '../services/liferay/rest/raysource/ProvisioningLicenseKeys';

const useProvisioningLicenseKeys = () => {
	const customerPortalContext = useCustomerPortal();

	const onboardingContext = useOnboarding();

	const sessionId =
		customerPortalContext?.[0].sessionId ||
		onboardingContext?.[0].sessionId;

	const {provisioningServerAPI} = useAppPropertiesContext();

	const provisioningLicenseKeysService = useMemo(
		() =>
			new ProvisioningLicenseKeys({
				provisioningServerAPI,
				sessionId,
			}),
		[provisioningServerAPI, sessionId]
	);

	return provisioningLicenseKeysService;
};

export default useProvisioningLicenseKeys;
