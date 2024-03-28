/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useState} from 'react';
import {useNavigate, useOutletContext} from 'react-router-dom';
import useSWR from 'swr';

import {DashboardPage} from '../../../components/DashBoardPage/DashboardPage';
import SearchBuilder from '../../../core/SearchBuilder';
import {useAccount} from '../../../hooks/data/useAccounts';
import HeadlessCommerceAdminCatalogImpl from '../../../services/rest/HeadlessCommerceAdminCatalog';
import PublishedAppsTable from './components/PublishedAppsTable';

const Apps = () => {
	const {catalogId} = useOutletContext<any>();
	const navigate = useNavigate();

	const [page, setPage] = useState(1);

	const {data: supplierAccount} = useAccount();

	const {data: publishedProductTable = {}} = useSWR(
		`/user-published-apps/${supplierAccount?.id}/${page}/${catalogId}`,
		() => {
			if (!catalogId) {
				return {items: [], totalCount: 0};
			}

			return HeadlessCommerceAdminCatalogImpl.getProducts(
				new URLSearchParams({
					'accountId': '-1',
					'attachments.accountId': '-1',
					'filter': new SearchBuilder()
						.eq('catalogId', catalogId as number, {unquote: true})
						.and()
						.lambda('categoryNames', 'App')
						.build(),
					'images.accountId': '-1',
					'nestedFields':
						'attachments,images,productChannels,productSpecifications,skus',
					'page': page.toString(),
					'skus.accountId': '-1',
				})
			);
		}
	);

	return (
		<DashboardPage
			buttonDisabled={!(catalogId && catalogId > 0)}
			buttonMessage="New App"
			messages={{
				description: 'Manage and publish apps on the Marketplace',
				title: 'Apps',
			}}
			onButtonClick={() => navigate('/app/create')}
		>
			<PublishedAppsTable items={publishedProductTable?.items ?? []} />

			{!!publishedProductTable?.items?.length && (
				<ClayPaginationBarWithBasicItems
					activeDelta={publishedProductTable.pageSize}
					activePage={page}
					ellipsisBuffer={3}
					onPageChange={setPage}
					totalItems={publishedProductTable.totalCount}
				/>
			)}
		</DashboardPage>
	);
};

export default Apps;
