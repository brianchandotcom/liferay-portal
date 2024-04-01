/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useOutletContext} from 'react-router-dom';

import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import {usePurchasedOrders} from '../usePurchasedOrders';
import PurchasedSolutionsTable from './components/PurchasedSolutionsTable';
import Page from '../../../components/Page';

const Solutions = () => {
	const {selectedAccount} = useOutletContext<any>();
	const {channel} = useMarketplaceContext();

	const {
		data: placedOrders = {items: []},
		isLoading,
		error,
	} = usePurchasedOrders({
		accountId: selectedAccount?.id,
		channelId: channel?.id,
		orderTypeExternalReferenceCodes: ['SOLUTION30', 'SOLUTIONS7'],
		page: 1,
		pageSize: 20,
	});

	return (
		<Page
			pageRendererProps={{isLoading, error}}
			description="Manage solution trial and purchases from the Marketplace"
			title="My Solutions"
		>
			<PurchasedSolutionsTable
				items={placedOrders.items as PlacedOrder[]}
			/>
		</Page>
	);
};

export default Solutions;
