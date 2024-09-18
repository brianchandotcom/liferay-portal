/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useState} from 'react';
import {useOutletContext} from 'react-router-dom';

import {DashboardPage} from '../../../../components/DashBoardPage/DashboardPage';
import {DashboardEmptyTable} from '../../../../components/DashboardTable/DashboardEmptyTable';
import Page from '../../../../components/Page';
import {Liferay} from '../../../../liferay/liferay';
import {getSiteURL} from '../../../../utils/site';
import PurchasedAppsTable from '../../components/PurchasedAppsTable';
import {usePurchasedOrders} from '../../usePurchasedOrders';

const Apps = () => {
	const [page, setPage] = useState(1);
	const {selectedAccount} = useOutletContext<any>();

	const {
		data: placedOrders = {items: [], pageSize: 1, totalCount: 0},
		error,
		isLoading,
	} = usePurchasedOrders({
		accountId: selectedAccount?.id as number,
		channelId: Number(Liferay.CommerceContext.commerceChannelId),
		orderTypeExternalReferenceCodes: ['CLOUDAPP', 'DXPAPP'],
		page,
		pageSize: 10,
	});

	const purchasedAppTable = {
		...placedOrders,
		items: placedOrders.items.map((order) => {
			const [placeOrderItem] = order.placedOrderItems;

			return {
				...order,
				name: placeOrderItem?.name,
				productId: order.placedOrderItems[0]?.productId,
				thumbnail: placeOrderItem?.thumbnail,
				type: placeOrderItem?.subscription
					? 'Subscription'
					: 'Perpetual',
				virtualURL: placeOrderItem?.virtualItemURLs,
			};
		}),
	};

	if (error) {
		return (
			<DashboardPage
				messages={{
					description: 'Manage apps purchase from the Marketplace',
					title: 'My Apps',
				}}
			>
				<DashboardEmptyTable
					description1="An error has occurred in retrieving you App."
					description2="Please try again. If the issue persists please contact marketplace-admin@liferay.com"
					icon="grid"
					title="An error occurred while fetching the data."
				/>
			</DashboardPage>
		);
	}

	return (
		<Page
			description="Manage apps purchase from the Marketplace"
			pageRendererProps={{isLoading}}
			rightButton={
				<ClayButton
					onClick={() => Liferay.Util.navigate(getSiteURL() || '/')}
				>
					Add Apps
				</ClayButton>
			}
			title="My Apps"
		>
			<PurchasedAppsTable
				items={(purchasedAppTable.items ?? []) as any}
			/>

			{!!purchasedAppTable.items.length && (
				<ClayPaginationBarWithBasicItems
					activeDelta={purchasedAppTable.pageSize}
					activePage={page}
					onPageChange={setPage}
					showDeltasDropDown={false}
					totalItems={purchasedAppTable?.totalCount}
				/>
			)}
		</Page>
	);
};

export default Apps;
