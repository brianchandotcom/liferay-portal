/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useOutletContext} from 'react-router-dom';

import solutionsIcon from '../../../assets/icons/analytics_icon.svg';
import {DashboardTable} from '../../../components/DashboardTable/DashboardTable';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import {DashboardPage} from '../../DashBoardPage/DashboardPage';
import {usePurchasedOrders} from '../usePurchasedOrders';

const Solutions = () => {
	const {selectedAccount} = useOutletContext<any>();
	const {channel} = useMarketplaceContext();

	const {data: placedOrders = {items: []}} = usePurchasedOrders({
		accountId: selectedAccount.id,
		channelId: channel.id,
		orderTypeExternalReferenceCodes: ['SOLUTION30'],
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
			<DashboardTable
				emptyStateMessage={{
					description1:
						'Purchase and install new solutions and they will show up here.',
					description2: 'Click on “New Solutions” to start.',
					title: 'No Solutions Yet',
				}}
				icon={solutionsIcon}
				items={placedOrders.items}
				tableHeaders={[]}
			/>
		</DashboardPage>
	);
};

export default Solutions;
