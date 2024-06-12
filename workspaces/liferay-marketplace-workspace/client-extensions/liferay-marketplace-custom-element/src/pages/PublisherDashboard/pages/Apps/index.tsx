/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useState} from 'react';
import {useNavigate, useOutletContext} from 'react-router-dom';
import useSWR from 'swr';

import Page from '../../../../components/Page';
import SearchBuilder from '../../../../core/SearchBuilder';
import {useAccount} from '../../../../hooks/data/useAccounts';
import HeadlessCommerceAdminCatalogImpl from '../../../../services/rest/HeadlessCommerceAdminCatalog';
import PublisherAppsTable from '../../components/PublisherAppsTable';

const Apps = () => {
	const [page, setPage] = useState(1);
	const {catalogId} = useOutletContext<any>();
	const {data: supplierAccount} = useAccount();
	const navigate = useNavigate();

	const {
		data: publishedProductTable = {},
		error,
		isLoading,
	} = useSWR(
		catalogId
			? `/user-published-apps/${supplierAccount?.id}/${page}/${catalogId}`
			: null,
		() =>
			HeadlessCommerceAdminCatalogImpl.getProducts(
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
					'sort': 'createDate:desc',
				})
			)
	);

	return (
		<Page
			description="Manage and publish apps on the Marketplace"
			pageRendererProps={{error, isLoading}}
			rightButton={
				<ClayButton
					disabled={!(catalogId && catalogId > 0)}
					onClick={() => navigate('/app/create')}
				>
					New App
				</ClayButton>
			}
			title="Apps"
		>
			<PublisherAppsTable items={publishedProductTable?.items ?? []} />

			{!!publishedProductTable?.items?.length && (
				<ClayPaginationBarWithBasicItems
					activeDelta={publishedProductTable.pageSize}
					activePage={page}
					ellipsisBuffer={3}
					onPageChange={setPage}
					totalItems={publishedProductTable.totalCount}
				/>
			)}
		</Page>
	);
};

export default Apps;
