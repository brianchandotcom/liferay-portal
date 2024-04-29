/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useOutletContext} from 'react-router-dom';

import {MembersPage} from '../../../components/MembersPage/MembersPage';
import {
	publisherAppPermissionDescriptions,
	publisherDashboardPermissionDescriptions,
	publisherRoles,
} from '../PublisherDashboardPageUtil';

const Members = () => {
	const {selectedAccount} = useOutletContext<any>();

	return (
		<MembersPage
			icon="user"
			isCustomerDashboard={false}
			isPublisherDashboard={true}
			listOfRoles={publisherRoles}
			rolesPermissionDescription={{
				appPermissions: publisherAppPermissionDescriptions,
				dashboardPermissions: publisherDashboardPermissionDescriptions,
			}}
			selectedAccount={selectedAccount}
		/>
	);
};

export default Members;
