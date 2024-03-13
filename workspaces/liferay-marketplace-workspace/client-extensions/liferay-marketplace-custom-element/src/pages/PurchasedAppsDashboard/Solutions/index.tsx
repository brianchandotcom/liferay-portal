/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useOutletContext} from 'react-router-dom';

import {DashboardPage} from '../../../components/DashBoardPage/DashboardPage';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import {usePurchasedOrders} from '../usePurchasedOrders';
import SolutionsTable from './components/SolutionsTable';

const Solutions = () => {
	const {selectedAccount} = useOutletContext<any>();
	const {channel} = useMarketplaceContext();

	const {data: placedOrders = {items: []}} = usePurchasedOrders({
		accountId: selectedAccount?.id,
		channelId: channel?.id,
		orderTypeExternalReferenceCodes: ['SOLUTION30', 'SOLUTIONS7'],
		page: 1,
		pageSize: 20,
	});

	return (
		<DashboardPage
			messages={{
				description:
					'Manage solution trial and purchases from the Marketplace',
				title: 'My Solutions',
			}}
		>
			<SolutionsTable items={placedOrders.items as PlacedOrder[]} />
		</DashboardPage>
	);
};

export default Solutions;
