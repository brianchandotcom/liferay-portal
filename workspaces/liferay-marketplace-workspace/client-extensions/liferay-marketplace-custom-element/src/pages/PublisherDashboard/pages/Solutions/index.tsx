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
import {isCloudEnvironment} from '../../../../utils/util';
import PublishedSolutionsTable from './PublishedSolutionsTable';

const Solutions = () => {
	const [page, setPage] = useState(1);
	const {catalogId} = useOutletContext<any>();
	const {data: supplierAccount} = useAccount();
	const navigate = useNavigate();

	const {
		data: publishedSolutionsTable = {},
		error,
		isLoading,
		mutate,
	} = useSWR(
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
		<Page
			description="Manage and publish solutions on the Marketplace"
			pageRendererProps={{error, isLoading}}
			rightButton={
				!isCloudEnvironment() && (
					<ClayButton
						disabled={!(catalogId && catalogId > 0)}
						onClick={() => navigate('/solution/publisher')}
					>
						New Solution Template
					</ClayButton>
				)
			}
			title="Solutions"
		>
			<PublishedSolutionsTable
				items={publishedSolutionsTable?.items ?? []}
				mutate={mutate}
			/>

			{!!publishedSolutionsTable?.items?.length && (
				<ClayPaginationBarWithBasicItems
					activeDelta={publishedSolutionsTable.pageSize}
					activePage={page}
					ellipsisBuffer={3}
					onPageChange={setPage}
					totalItems={publishedSolutionsTable.totalCount}
				/>
			)}
		</Page>
	);
};

export default Solutions;
