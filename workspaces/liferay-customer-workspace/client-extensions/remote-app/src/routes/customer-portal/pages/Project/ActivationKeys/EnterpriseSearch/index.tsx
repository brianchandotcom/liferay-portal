/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCustomerPortal} from '../../../../context';
import ActivationKeysLayout from '../../../../layouts/ActivationKeysLayout';

const EnterpriseSearch = () => {
	const [{project, sessionId}] = useCustomerPortal();

	return (
		<ActivationKeysLayout>
			<ActivationKeysLayout.Inputs
				accountKey={project?.accountKey}
				productKey="enterprise-search"
				productTitle="Enterprise Search"
				projectName={project?.name}
				sessionId={sessionId}
			/>
		</ActivationKeysLayout>
	);
};

export default EnterpriseSearch;
