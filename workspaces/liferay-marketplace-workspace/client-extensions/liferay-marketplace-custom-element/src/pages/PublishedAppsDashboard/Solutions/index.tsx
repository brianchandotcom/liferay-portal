/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useState} from 'react';
import {useOutletContext} from 'react-router-dom';
import useSWR from 'swr';

import {DashboardPage} from '../../../components/DashBoardPage/DashboardPage';
import SearchBuilder from '../../../core/SearchBuilder';
import {useAccount} from '../../../hooks/data/useAccounts';
import HeadlessCommerceAdminCatalogImpl from '../../../services/rest/HeadlessCommerceAdminCatalog';
import PublishedSolutionsTable from './PublishedSolutionsTable';

const Solutions = () => {
	const {catalogId} = useOutletContext<any>();
	const {data: supplierAccount} = useAccount();

	const [page, setPage] = useState(1);

	const {data: publishedSolutionsTable = {}, mutate} = useSWR(
		`/user-published-solutions/${supplierAccount?.id}/${page}/${catalogId}`,
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
						.lambda('categoryNames', 'Solution')
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
			buttonMessage="New Solution Template"
			messages={{
				description: 'Manage and publish solutions on the Marketplace',
				title: 'Solutions',
			}}
		>
			<PublishedSolutionsTable
				items={publishedSolutionsTable?.items ?? []}
				mutate={mutate}
			/>

			{!!publishedSolutionsTable?.items?.length && (
				<ClayPaginationBarWithBasicItems
					active={page}
					activeDelta={publishedSolutionsTable.pageSize}
					ellipsisBuffer={3}
					ellipsisProps={{
						'aria-label': 'More',
						'title': 'More',
					}}
					onActiveChange={setPage}
					totalItems={publishedSolutionsTable.totalCount}
				/>
			)}
		</DashboardPage>
	);
};

export default Solutions;
