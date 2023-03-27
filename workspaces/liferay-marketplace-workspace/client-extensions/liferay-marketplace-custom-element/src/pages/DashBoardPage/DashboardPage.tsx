import {Dispatch, ReactNode, SetStateAction, useState} from 'react';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {AppProps} from '../../components/DashboardTable/DashboardTable';
import {Footer} from '../../components/Footer/Footer';
import {Header} from '../../components/Header/Header';
import {AppDetailsPage} from '../AppDetailsPage/AppDetailsPage';
import { MemberProps } from '../PublishedAppsDashboardPage/PublishedDashboardPageUtil';

import './DashboardPage.scss';

export interface DashboardListItems {
	itemIcon: string;
	itemName: string;
	itemSelected: boolean;
	itemTitle: string;
	items?: AppProps[];
}

type DashBoardPageProps = {
	accountAppsNumber: string;
	accountLogo: string;
	accountTitle: string;
	buttonMessage?: string;
	children: ReactNode;
	dashboardNavigationItems: DashboardListItems[];
	messages: {
		description: string;
		emptyStateMessage: {
			description1: string;
			description2: string;
			title: string;
		};
		title: string;
	};
	setDashboardNavigationItems: Dispatch<SetStateAction<DashboardListItems[]>>;
};

export function DashboardPage({
	accountAppsNumber,
	accountLogo,
	accountTitle,
	buttonMessage,
	children,
	dashboardNavigationItems,
	messages,
	setDashboardNavigationItems,
}: DashBoardPageProps) {
	const [selectedApp, setSelectedApp] = useState<AppProps>();

	return (
		<div className="dashboard-page-container">
			<div>
				<div className="dashboard-page-body-container">
					<DashboardNavigation
						accountAppsNumber={accountAppsNumber}
						accountIcon={accountLogo}
						accountTitle={accountTitle}
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
									description={messages.description}
									title={messages.title}
								/>

								{buttonMessage &&
									<a href="/create-new-app">
										<button className="dashboard-page-body-header-button">
											{buttonMessage}
										</button>
									</a>
								}
							</div>

							{children}
						</div>
					)}
				</div>
			</div>

			<Footer />
		</div>
	);
}
