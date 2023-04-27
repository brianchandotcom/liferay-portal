import {ReactNode} from 'react';

import {AppProps} from '../../components/DashboardTable/DashboardTable';
import {Footer} from '../../components/Footer/Footer';
import {Header} from '../../components/Header/Header';
import {AppDetailsPage} from '../AppDetailsPage/AppDetailsPage';

import './DashboardPage.scss';
import {Liferay} from '../../liferay/liferay';

export interface DashboardListItems {
	itemIcon: string;
	itemName: string;
	itemSelected: boolean;
	itemTitle: string;
	items?: AppProps[];
}

interface DashBoardPageProps {
	buttonHref?: string;
	buttonMessage?: string;
	children: ReactNode;
	dashboardNavigationItems: DashboardListItems[];
	messages: {
		description: string;
		emptyStateMessage?: {
			description1: string;
			description2: string;
			title: string;
		};
		title: string;
	};
	onButtonClick?: () => void;
	setSelectedApp?: (value: AppProps | undefined) => void;
	selectedApp?: AppProps;
}

export function DashboardPage({
	buttonHref,
	buttonMessage,
	children,
	dashboardNavigationItems,
	messages,
	onButtonClick,
	selectedApp,
	setSelectedApp,
}: DashBoardPageProps) {
	return (
		<div className="dashboard-page-container">
			<div>
				<div className="dashboard-page-body-container">
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

								{buttonMessage && (
									<a
										href={
											buttonHref ? `${buttonHref}` : '#'
										}
									>
										<button
											className="dashboard-page-body-header-button"
											onClick={() =>
												onButtonClick && onButtonClick()
											}
										>
											{buttonMessage}
										</button>
									</a>
								)}
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
