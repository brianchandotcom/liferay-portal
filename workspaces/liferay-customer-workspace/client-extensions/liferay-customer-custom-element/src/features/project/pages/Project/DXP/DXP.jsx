/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import {useEffect, useState} from 'react';
import i18n from '~/utils/I18n';
import ActivationKeysTable from '~/features/project/containers/ActivationKeysTable';
import {useAppContext} from '~/features/project/context';
import DeveloperKeysLayouts from '~/features/project/layouts/DeveloperKeysLayout';
import {LIST_TYPES} from '~/features/project/utils/constants';
import {getOrRequestToken} from '~/services/liferay/security/auth/getOrRequestToken';

const DXP = ({hasComplimentaryKey}) => {
	const [oAuthToken, setOAuthToken] = useState();
	const [{project}] = useAppContext();

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
					listType={LIST_TYPES.dxpMajorVersion}
					oAuthToken={oAuthToken}
					productName="DXP"
					projectName={project.name}
				></DeveloperKeysLayouts.Inputs>
			</DeveloperKeysLayouts>
		</div>
	);
};

export default DXP;
