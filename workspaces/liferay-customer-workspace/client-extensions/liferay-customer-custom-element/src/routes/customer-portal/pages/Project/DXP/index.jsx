/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import {useEffect, useState} from 'react';
import i18n from '../../../../../common/I18n';
import ActivationKeysTable from '../../../containers/ActivationKeysTable';
import {useCustomerPortal} from '../../../context';
import DeveloperKeysLayouts from '../../../layouts/DeveloperKeysLayout';
import {LIST_TYPES} from '../../../utils/constants';
import {getOrRequestToken} from '../../../../../common/services/liferay/security/auth/getOrRequestToken';

const DXP = ({hasComplimentaryKey}) => {
	const [oAuthToken, setOAuthToken] = useState();
	const [{project}] = useCustomerPortal();

	useEffect(() => {
		const fetchToken = async () => {
			const token = await getOrRequestToken();

			setOAuthToken(token);
		};

		fetchToken();
	}, []);

	return (
		<div className="mr-4">
			<ActivationKeysTable
				hasComplimentaryKey={hasComplimentaryKey}
				initialFilter="(startswith(productName,'DXP') or startswith(productName,'Digital'))"
				oAuthToken={oAuthToken}
				productName="DXP"
				project={project}
			/>

			<DeveloperKeysLayouts>
				<DeveloperKeysLayouts.Inputs
					accountKey={project.accountKey}
					downloadTextHelper={i18n.translate(
						'select-the-liferay-dxp-version-for-your-developer-key-to-download'
					)}
					dxpVersion={project.dxpVersion}
					listType={LIST_TYPES.dxpVersion}
					oAuthToken={oAuthToken}
					productName="DXP"
					projectName={project.name}
				></DeveloperKeysLayouts.Inputs>
			</DeveloperKeysLayouts>
		</div>
	);
};

export default DXP;
