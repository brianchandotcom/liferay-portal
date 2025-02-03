/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {useCustomerPortal} from '~/features/project/context';
import ActivationKeysLayout from '~/features/project/layouts/ActivationKeysLayout';
import {getOrRequestToken} from '~/services/liferay/security/auth/getOrRequestToken';

const EnterpriseSearch = () => {
	const [oAuthToken, setOAuthToken] = useState<string | null>(null);
	const [{project}] = useCustomerPortal();

	useEffect(() => {
		const fetchToken = async () => {
			const token = await getOrRequestToken();

			setOAuthToken(token);
		};

		fetchToken();
	}, []);

	return (
		<ActivationKeysLayout>
			<ActivationKeysLayout.Inputs
				accountKey={project?.accountKey}
				accountSubscriptionGroupName="enterprise-search"
				oAuthToken={oAuthToken}
				productTitle="Enterprise Search"
				projectName={project?.name}
			/>
		</ActivationKeysLayout>
	);
};

export default EnterpriseSearch;
