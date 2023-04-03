import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {DashboardListItems} from '../DashBoardPage/DashboardPage';

interface AccountDetailsPageProps {
	accountIcon: string;
	accountAppsNumber: string;
	accounts: AccountBrief[];
	dashboardNavigationItems: DashboardListItems[];
	setDashboardNavigationItems: (values: DashboardListItems[]) => void;
}

export function AccountDetailsPage({
	accountAppsNumber,
	accountIcon,
	accounts,
	dashboardNavigationItems,
	setDashboardNavigationItems,
}: AccountDetailsPageProps) {
	return (
		<>
			<DashboardNavigation
				accountAppsNumber={accountAppsNumber}
				accountIcon={accountIcon}
				accounts={accounts}
				dashboardNavigationItems={dashboardNavigationItems}
				setDashboardNavigationItems={setDashboardNavigationItems}
			/>

			{/* TODO AccountDetails Component */}
		</>
	);
}
