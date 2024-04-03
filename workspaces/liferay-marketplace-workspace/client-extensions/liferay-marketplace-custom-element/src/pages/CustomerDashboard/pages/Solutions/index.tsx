/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useOutletContext} from 'react-router-dom';

import Page from '../../../../components/Page';
import {useMarketplaceContext} from '../../../../context/MarketplaceContext';
import PurchasedSolutionsTable from '../../components/PurchasedSolutionsTable';
import {usePurchasedOrders} from '../../usePurchasedOrders';

const Solutions = () => {
	const {selectedAccount} = useOutletContext<any>();
	const {channel} = useMarketplaceContext();

	const {
		data: placedOrders = {items: []},
		error,
		isLoading,
	} = usePurchasedOrders({
		accountId: selectedAccount?.id,
		channelId: channel?.id,
		orderTypeExternalReferenceCodes: ['SOLUTION30', 'SOLUTIONS7'],
		page: 1,
		pageSize: 20,
	});

	return (
		<Page
			description="Manage solution trial and purchases from the Marketplace"
			pageRendererProps={{error, isLoading}}
			title="My Solutions"
		>
			<PurchasedSolutionsTable
				items={placedOrders.items as PlacedOrder[]}
			/>
		</Page>
	);
};

export default Solutions;
