/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {DashboardPage} from '../../../components/DashBoardPage/DashboardPage';
import SearchBuilder from '../../../core/SearchBuilder';
import i18n from '../../../i18n';
import HeadlessCommerceAdminCatalogImpl from '../../../services/rest/HeadlessCommerceAdminCatalog';
import AppAdministratorTable from './AppAdministratorTable';

const AppAdministrator = () => {
	const {data: apps} = useSWR<APIResponse<PublisherRequestInfo>>(
		'administrator-dashboard/apps',
		() =>
			HeadlessCommerceAdminCatalogImpl.getProducts(
				new URLSearchParams({
					filter: SearchBuilder.lambda('categoryNames', 'Project'),
					nestedFields: 'productSpecifications',
					sort: 'createDate:desc',
				})
			)
	);

	return (
		<DashboardPage
			messages={{
				description: i18n.translate('all-published-apps'),
				title: i18n.translate('published-apps'),
			}}
		>
			<AppAdministratorTable items={apps?.items || []} />
		</DashboardPage>
	);
};

export default AppAdministrator;
