import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useEffect, useState} from 'react';

import accountLogo from '../../assets/icons/mainAppLogo.svg';
import {DashboardTable} from '../../components/DashboardTable/DashboardTable';
import {PurchasedAppsDashboardTableRow} from '../../components/DashboardTable/PurchasedAppsDashboardTableRow';
import {getAccount, getChannels, getOrders} from '../../utils/api';
import {DashboardPage} from '../DashBoardPage/DashboardPage';
import {initialDashboardNavigationItems} from './PurchasedDashboardPageUtil';
export interface PurchasedAppProps {
	image: string;
	name: string;
	orderId: number;
	project?: string;
	provisioning: string;
	purchasedBy: string;
	purchasedDate: string;
	type: string;
	version?: string;
}

interface PurchasedAppTable {
	items: PurchasedAppProps[];
	pageSize: number;
	totalCount: number;
}

const tableHeaders = [
	{
		title: 'Name',
	},
	{
		title: 'Purchased By',
	},
	{
		title: 'Type',
	},
	{
		title: 'Order ID',
	},
	{
		title: 'Provisioning',
	},
	{
		title: 'Installation',
	},
];

export function PurchasedAppsDashboardPage() {
	const [purchasedAppTable, setPurchasedAppTable] =
		useState<PurchasedAppTable>({items: [], pageSize: 7, totalCount: 1});
	const [page, setPage] = useState<number>(1);
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
		const makeFetch = async () => {
			const account = await getAccount();

			const channels = await getChannels();

			const channel =
				channels.find(
					(channel) => channel.name === 'Marketplace Channel'
				) || channels[0];

			const placedOrders = await getOrders(
				account.accountBriefs[0]?.id || 50307,
				channel.id,
				page,
				purchasedAppTable.pageSize
			);

			const newOrderItems = placedOrders.items.map((order) => {
				const [placeOrderItem] = order.placedOrderItems;

				const date = new Date(order.createDate);
				const options: Intl.DateTimeFormatOptions = {
					day: 'numeric',
					month: 'short',
					year: 'numeric',
				};
				const formattedDate = date.toLocaleDateString('en-US', options);

				return {
					image: placeOrderItem.thumbnail,
					name: placeOrderItem.name,
					orderId: order.id,
					provisioning: order.orderStatusInfo.label_i18n,
					purchasedBy: order.author,
					purchasedDate: formattedDate,
					type: placeOrderItem.subscription
						? 'Subscription'
						: 'Perpetual',
				};
			});

			setPurchasedAppTable({
				...purchasedAppTable,
				items: newOrderItems,
				totalCount: placedOrders.totalCount,
			});
		};
		makeFetch();
	}, [page]);

	return (
		<DashboardPage
			accountAppsNumber="0"
			accountLogo={accountLogo}
			accountTitle="Hourglass"
			buttonMessage="Add Apps"
			dashboardNavigationItems={dashboardNavigationItems}
			messages={messages}
			setDashboardNavigationItems={setDashboardNavigationItems}
		>
			<DashboardTable<PurchasedAppProps>
				emptyStateMessage={messages.emptyStateMessage}
				items={purchasedAppTable.items}
				tableHeaders={tableHeaders}
			>
				{(item) => (
					<PurchasedAppsDashboardTableRow
						item={item}
						key={item.name}
					/>
				)}
			</DashboardTable>

			{purchasedAppTable.items.length ? (
				<ClayPaginationBarWithBasicItems
					active={page}
					activeDelta={purchasedAppTable.pageSize}
					defaultActive={1}
					ellipsisBuffer={3}
					ellipsisProps={{'aria-label': 'More', 'title': 'More'}}
					onActiveChange={setPage}
					showDeltasDropDown={false}
					totalItems={purchasedAppTable?.totalCount}
				/>
			) : (
				<></>
			)}
		</DashboardPage>
	);
}
