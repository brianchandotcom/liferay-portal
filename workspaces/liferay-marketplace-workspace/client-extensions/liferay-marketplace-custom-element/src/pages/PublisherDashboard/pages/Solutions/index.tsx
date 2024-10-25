/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useState} from 'react';
import {useNavigate, useOutletContext} from 'react-router-dom';
import useSWR from 'swr';

import {DashboardTable} from '../../../../components/DashboardTable/DashboardTable';
import Page from '../../../../components/Page';
import {useMarketplaceContext} from '../../../../context/MarketplaceContext';
import SearchBuilder from '../../../../core/SearchBuilder';
import {useAccount} from '../../../../hooks/data/useAccounts';
import {Liferay} from '../../../../liferay/liferay';
import HeadlessCommerceAdminCatalogImpl from '../../../../services/rest/HeadlessCommerceAdminCatalog';
import {getSiteURL} from '../../../../utils/site';
import PublishedSolutionsTable from './PublishedSolutionsTable';

const SOLUTION_PUBLISHER_ROLE = 'Solution Publisher';

const Solutions = () => {
	const [page, setPage] = useState(1);
	const {catalogId} = useOutletContext<any>();
	const {data: supplierAccount} = useAccount();
	const {myUserAccount} = useMarketplaceContext();
	const navigate = useNavigate();

	const supplierAccountRoleBriefs =
		(myUserAccount?.accountBriefs ?? []).find(
			({id}) => id === supplierAccount?.id
		)?.roleBriefs ?? [];

	const isSolutionPublisher = supplierAccountRoleBriefs.find(
		({name}) => name === SOLUTION_PUBLISHER_ROLE
	);

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

	const items = publishedSolutionsTable?.items ?? [];

	return (
		<Page
			description="Manage and publish solutions on the Marketplace"
			pageRendererProps={{error, isLoading}}
			rightButton={
				isSolutionPublisher && (
					<ClayButton
						disabled={!(catalogId && catalogId > 0)}
						onClick={() => navigate('/solutions/publisher')}
					>
						New Solution Template
					</ClayButton>
				)
			}
			title="Solutions"
		>
			{!isSolutionPublisher && (
				<ClayAlert displayType="warning">
					Dear <b>{Liferay.ThemeDisplay.getUserName()}</b>, Publishing
					solutions on the Liferay Solutions Marketplace is only
					available to existing Liferay partners currently. If you are
					a partner and wish to be able to publish your solutions here{' '}
					<a href={`${getSiteURL()}/publisher-gate`} target="_blank">
						please complete this form.
					</a>
				</ClayAlert>
			)}

			{items.length ? (
				<PublishedSolutionsTable items={items} mutate={mutate} />
			) : (
				<DashboardTable
					emptyStateMessage={{
						className: 'd-flex justify-content-center',
						...(isSolutionPublisher && {
							description1:
								'Create and submit new Solutions and they will show up here.',
							description2:
								'Click on “Add Solution Template” to start.',
						}),
						title: 'No Solutions Yet',
					}}
					icon="grid"
				/>
			)}

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
