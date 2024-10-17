/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import {useEffect, useState} from 'react';
import {useOutletContext} from 'react-router-dom';
import i18n from '../../../../../common/I18n';
import ActivationKeysTable from '../../../containers/ActivationKeysTable';
import {useCustomerPortal} from '../../../context';
import DeveloperKeysLayouts from '../../../layouts/DeveloperKeysLayout';
import {LIST_TYPES} from '../../../utils/constants';
import {getOrRequestToken} from '../../../../../common/services/liferay/security/auth/getOrRequestToken';

const Portal = ({hasComplimentaryKey}) => {
	const [oAuthToken, setOAuthToken] = useState();
	const [{project}] = useCustomerPortal();
	const {setHasSideMenu} = useOutletContext();

	useEffect(() => {
		const fetchToken = async () => {
			const token = await getOrRequestToken();

			setOAuthToken(token);
		};

		fetchToken();
	}, []);

	useEffect(() => {
		setHasSideMenu(true);
	}, [setHasSideMenu]);

	return (
		<div className="mr-4">
			<ActivationKeysTable
				hasComplimentaryKey={hasComplimentaryKey}
				initialFilter="startswith(productName,'Portal')"
				oAuthToken={oAuthToken}
				productName="Portal"
				project={project}
			/>

			<DeveloperKeysLayouts>
				<DeveloperKeysLayouts.Inputs
					accountKey={project.accountKey}
					downloadTextHelper={i18n.translate(
						'select-the-liferay-portal-version-for-which-you-want-to-download-a-developer-key'
					)}
					dxpVersion={project.dxpVersion}
					listType={LIST_TYPES.portalVersion}
					oAuthToken={oAuthToken}
					productName="Portal"
					projectName={project.name}
				></DeveloperKeysLayouts.Inputs>
			</DeveloperKeysLayouts>
		</div>
	);
};

export default Portal;
