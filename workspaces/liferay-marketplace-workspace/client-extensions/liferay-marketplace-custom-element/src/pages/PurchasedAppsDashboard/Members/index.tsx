/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useOutletContext} from 'react-router-dom';

import membersIcon from '../../../assets/icons/person_fill_icon.svg';
import {MembersPage} from '../../../components/MembersPage/MembersPage';
import {customerRoles} from '../../PublishedAppsDashboard/PublishedDashboardPageUtil';
import {
	customerAppPermissionDescriptions,
	customerDashboardPermissionDescriptions,
} from '../PurchasedDashboardPageUtil';

const Members = () => {
	const {selectedAccount} = useOutletContext<any>();

	return (
		<MembersPage
			icon={membersIcon}
			isCustomerDashboard={true}
			isPublisherDashboard={false}
			listOfRoles={customerRoles}
			rolesPermissionDescription={{
				appPermissions: customerAppPermissionDescriptions,
				dashboardPermissions: customerDashboardPermissionDescriptions,
			}}
			selectedAccount={selectedAccount}
		/>
	);
};

export default Members;
