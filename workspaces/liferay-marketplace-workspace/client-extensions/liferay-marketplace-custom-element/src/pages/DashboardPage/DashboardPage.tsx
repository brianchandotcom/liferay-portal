import {useState} from 'react';

import accountLogo from '../../assets/icons/mainAppLogo.svg';
import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {
	AppProps,
	DashboardTable,
} from '../../components/DashboardTable/DashboardTable';
import {DashboardToolbar} from '../../components/DashboardToolbar/DashboardToolbar';
import {Footer} from '../../components/Footer/Footer';
import {Header} from '../../components/Header/Header';
import {AppDetailsPage} from '../AppDetailsPage/AppDetailsPage';
import {appList, initialDashboardNavigationItems} from './DashboardPageUtil';

import './DashboardPage.scss';

export function DashboardPage() {
	const [selectedApp, setSelectedApp] = useState<AppProps>();
	const [dashboardNavigationItems, setDashboardNavigationItems] = useState(
		initialDashboardNavigationItems
	);

	return (
		<div className="dashboard-page-container">
			<div>
				<div className="dashboard-page-body-container">
					<DashboardNavigation
						accountAppsNumber="4"
						accountIcon={accountLogo}
						accountTitle="Acme Co"
						dashboardNavigationItems={dashboardNavigationItems}
						onSelectAppChange={setSelectedApp}
						setDashboardNavigationItems={
							setDashboardNavigationItems
						}
					/>

					{selectedApp ? (
						<AppDetailsPage
							dashboardNavigationItems={dashboardNavigationItems}
							selectedApp={selectedApp}
							setSelectedApp={setSelectedApp}
						/>
					) : (
						<div>
							<div className="dashboard-page-body-header-container">
								<Header
									description="Manage and publish apps on the Marketplace"
									title="Apps"
								/>

								<a href="/create-new-app">
									<button className="dashboard-page-body-header-button">
										+ New App
									</button>
								</a>
							</div>

							<DashboardTable apps={appList} />
						</div>
					)}
				</div>
			</div>

			<Footer />
		</div>
	);
}
