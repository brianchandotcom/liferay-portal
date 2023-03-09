import {DashboardPage} from '../DashBoardPage/DashboardPage';

export function PurchasedAppsDashboardPage() {
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

	return (
		<DashboardPage
			buttonMessage="Add Apps"
			messages={messages}
		></DashboardPage>
	);
}
