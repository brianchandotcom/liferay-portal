import {useEffect, useState} from 'react';

import accountLogo from '../../assets/icons/mainAppLogo.svg';
import {
	AppProps,
	DashboardTable,
} from '../../components/DashboardTable/DashboardTable';
import {DashboardTableRow} from '../../components/DashboardTable/DashboardTableRow';
import {getProducts} from '../../utils/api';
import {DashboardPage} from '../DashBoardPage/DashboardPage';
import {initialDashboardNavigationItems} from './PublishedDashboardPageUtil';

const tableHeaders = [
	{
		iconSymbol: 'order-arrow',
		title: 'Name',
	},
	{
		title: 'Version',
	},
	{
		title: 'Type',
	},
	{
		title: 'Last Updated',
	},
	{
		title: 'Rating',
	},
	{
		title: 'Status',
	},
];

export function PublishedAppsDashboardPage() {
	const [apps, setApps] = useState<AppProps[]>(Array<AppProps>());
	const [dashboardNavigationItems, setDashboardNavigationItems] = useState(
		initialDashboardNavigationItems
	);

	const messages = {
		description: 'Manage and publish apps on the Marketplace',
		emptyStateMessage: {
			description1: 'Publish apps and they will show up here.',
			description2: 'Click on “New App” to start.',
			title: 'No apps yet',
		},
		title: 'Apps',
	};

	useEffect(() => {
		(async () => {
			const products = await getProducts();

			const liferayApps = products.items.map((product: any) => ({
				name: product.name.en_US,
			}));

			setApps(liferayApps);
		})();
	}, []);

	return (
		<DashboardPage
			accountAppsNumber="4"
			accountLogo={accountLogo}
			accountTitle="Acme Co"
			buttonMessage="+ New App"
			dashboardNavigationItems={dashboardNavigationItems}
			items={apps}
			messages={messages}
			setDashboardNavigationItems={setDashboardNavigationItems}
		>
			<DashboardTable<AppProps>
				emptyStateMessage={messages.emptyStateMessage}
				items={apps}
				tableHeaders={tableHeaders}
			>
				{(item) => <DashboardTableRow item={item} key={item.name} />}
			</DashboardTable>
		</DashboardPage>
	);
}
