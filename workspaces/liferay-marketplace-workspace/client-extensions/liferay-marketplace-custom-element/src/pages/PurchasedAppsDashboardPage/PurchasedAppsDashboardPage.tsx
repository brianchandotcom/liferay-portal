import {useEffect, useState} from 'react';

import accountLogo from '../../assets/icons/mainAppLogo.svg';
import {AppProps} from '../../components/DashboardTable/DashboardTable';
import {getOrders} from '../../utils/api';
import {DashboardPage} from '../DashBoardPage/DashboardPage';
import {initialDashboardNavigationItems} from './PurchasedDashboardPageUtil';

export function PurchasedAppsDashboardPage() {
	const [orders, setOrders] = useState<AppProps[]>(Array<AppProps>());
	const [dashboardNavigationItems, setDashboardNavigationItems] = useState(
		initialDashboardNavigationItems
	);

	const messages = {
		description: 'Manage apps purchase from the Marketplace',
		emptyStateMessage: {
			description1:
				'Purchase and install new apps and they will show up here.',
			description2: 'Click on “Add Apps” to start.',
			title: 'No apps yet',
		},
		title: 'My Apps',
	};

	useEffect(() => {
		(async () => {
			const orders = await getOrders();

			setOrders(orders);
		})();
	}, []);

	return (
		<DashboardPage
			accountAppsNumber="0"
			accountLogo={accountLogo}
			accountTitle="Hourglass"
			buttonMessage="Add Apps"
			dashboardNavigationItems={dashboardNavigationItems}
			items={orders}
			messages={messages}
			setDashboardNavigationItems={setDashboardNavigationItems}
		></DashboardPage>
	);
}
