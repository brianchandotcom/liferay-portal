import {useState} from 'react';

import {
	AppProps,
	DashboardTable,
} from '../../components/DashboardTable/DashboardTable';
import {Header} from '../../components/Header/Header';
import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {DashboardToolbar} from '../../components/DashboardToolbar/DashboardToolbar';
import {Footer} from '../../components/Footer/Footer';
import {AppDetailsPage} from '../AppDetailsPage/AppDetailsPage';
import {appList, initialDashboardNavigationItems} from './DashboardPageUtil';

import accountLogo from '../../assets/icons/mainAppLogo.svg';

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
						onSelectAppChange={setSelectedApp}
						accountAppsNumber="4"
						accountIcon={accountLogo}
						accountTitle="Acme Co"
						setDashboardNavigationItems={
							setDashboardNavigationItems
						}
						dashboardNavigationItems={dashboardNavigationItems}
					/>

					{selectedApp ? (
						<AppDetailsPage
							selectedApp={selectedApp}
							dashboardNavigationItems={dashboardNavigationItems}
							setSelectedApp={setSelectedApp}
						/>
					) : (
						<div>
							<div className="dashboard-page-body-header-container">
								<Header
									title="Apps"
									description="Manage and publish apps on the Marketplace"
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
