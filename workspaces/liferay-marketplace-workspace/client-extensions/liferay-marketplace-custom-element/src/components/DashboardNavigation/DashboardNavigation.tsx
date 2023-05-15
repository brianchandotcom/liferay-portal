import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {Dispatch} from 'react';

import {showAccountImage} from '../../utils/util';
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
	accountAppsNumber: number;
	accountIcon: string;
	accounts: Account[];
	currentAccount: Account;
	dashboardNavigationItems: DashboardListItems[];
	onSelectAppChange?: (value: AppProps | undefined) => void;
	setDashboardNavigationItems: (values: DashboardListItems[]) => void;
	setSelectedAccount: Dispatch<React.SetStateAction<Account>>;
}

export function DashboardNavigation({
	accountAppsNumber,
	accountIcon,
	accounts,
	currentAccount,
	dashboardNavigationItems,
	onSelectAppChange,
	setDashboardNavigationItems,
	setSelectedAccount,
}: DashboardNavigationProps) {
	return (
		<div className="dashboard-navigation-container">
			<ClayDropDown
				trigger={
					<div className="dashboard-navigation-header">
						<div className="dashboard-navigation-header-left-content">
							<img
								alt="account logo"
								className="dashboard-navigation-header-logo"
								src={showAccountImage(accountIcon)}
							/>

							<div className="dashboard-navigation-header-text-container">
								<span className="dashboard-navigation-header-title">
									{currentAccount.name}
								</span>

								<span className="dashboard-navigation-header-apps">
									{accountAppsNumber} apps
								</span>
							</div>
						</div>

						<ClayIcon
							className="dashboard-navigation-header-arrow-down"
							symbol="caret-bottom"
						/>
					</div>
				}
			>
				<ClayDropDown.ItemList>
					{accounts.map((account) => (
						<ClayDropDown.Item
							key={account.id}
							onClick={() => setSelectedAccount(account)}
						>
							{account.name}
						</ClayDropDown.Item>
					))}
				</ClayDropDown.ItemList>
			</ClayDropDown>

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
		</div>
	);
}
