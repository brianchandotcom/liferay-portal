/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import i18n from '../../../../i18n';
import HeadlessCommerceAdminCatalogImpl from '../../../../services/rest/HeadlessCommerceAdminCatalog';
import AppAdministratorTable from './AppAdministratorTable';
import Page from '../../../../components/Page';

const AppAdministrator = () => {
	const {
		data: apps,
		isLoading,
		error,
	} = useSWR<APIResponse<PublisherRequestInfo>>(
		'administrator-dashboard/apps',
		() =>
			HeadlessCommerceAdminCatalogImpl.getProducts(
				new URLSearchParams({
					nestedFields: 'productSpecifications',
					sort: 'createDate:desc',
				})
			)
	);

	return (
		<Page
			description={i18n.translate('list-with-latest-published-apps')}
			title={i18n.translate('recent-published-apps')}
			pageRendererProps={{isLoading, error}}
		>
			<AppAdministratorTable items={apps?.items || []} />
		</Page>
	);
};

export default AppAdministrator;
