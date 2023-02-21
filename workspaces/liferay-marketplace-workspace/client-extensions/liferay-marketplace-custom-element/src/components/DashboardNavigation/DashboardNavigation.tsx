import ClayButton from '@clayui/button';
import {useState} from 'react';

import arrowDown from '../../assets/icons/arrow-down.svg';
import arrowUP from '../../assets/icons/arrow-up.svg';
import {DashboardNavigationList} from './DashboardNavigationList';

import './DashboardNavigation.scss';
import {AppProps} from '../DashboardTable/DashboardTable';
export interface DashboardListItems {
	itemIcon: string;
	itemName: string;
	itemSelected: boolean;
	itemTitle: string;
	items?: AppProps[];
}

interface DashboardNavigationProps {
	accountAppsNumber: string;
	accountIcon: string;
	accountTitle: string;
	dashboardNavigationItems: DashboardListItems[];
	onSelectAppChange: (value: AppProps) => void;
	setDashboardNavigationItems: (values: DashboardListItems[]) => void;
}

export function DashboardNavigation({
	accountAppsNumber,
	accountIcon,
	accountTitle,
	dashboardNavigationItems,
	onSelectAppChange,
	setDashboardNavigationItems,
}: DashboardNavigationProps) {
	const [expandList, setExpandList] = useState(true);

	return (
		<div className="dashboard-navigation-container">
			<div className="dashboard-navigation-header">
				<div className="dashboard-navigation-header-left-content">
					<img
						alt="account logo"
						className="dashboard-navigation-header-logo"
						src={accountIcon}
					/>

					<div className="dashboard-navigation-header-text-container">
						<span className="dashboard-navigation-header-title">
							{accountTitle}
						</span>

						<span className="dashboard-navigation-header-apps">
							{accountAppsNumber} apps
						</span>
					</div>
				</div>

				<ClayButton
					displayType="unstyled"
					onClick={() => setExpandList(!expandList)}
				>
					<img
						alt="Arrow Down"
						className="dashboard-navigation-header-arrow-down"
						src={expandList ? arrowUP : arrowDown}
					/>
				</ClayButton>
			</div>

			{expandList && (
				<div className="dashboard-navigation-body">
					{dashboardNavigationItems.map((navigationMock) => (
						<DashboardNavigationList
							dashboardNavigationItems={dashboardNavigationItems}
							key={navigationMock.itemName}
							navigationItemMock={navigationMock}
							navigationItemsMock={dashboardNavigationItems}
							onSelectAppChange={onSelectAppChange}
							setDashboardNavigationItems={
								setDashboardNavigationItems
							}
						/>
					))}
				</div>
			)}
		</div>
	);
}
