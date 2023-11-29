/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {useNavigate} from 'react-router-dom';

import {getAccountImage} from '../../utils/util';
import {DashboardNavigationList} from './DashboardNavigationList';

import './DashboardNavigation.scss';
import {Liferay} from '../../liferay/liferay';
import CommerceSelectAccountImpl from '../../services/rest/CommerceSelectAccount';
import {AppProps} from '../DashboardTable/DashboardTable';
export interface DashboardListItems {
	itemIcon: string;
	itemName: string;
	itemSelected?: boolean;
	itemTitle: string;
	items?: AppProps[];
	path: string;
}

interface DashboardNavigationProps {
	accountAppsNumber: number;
	accountIcon: string;
	accounts: Account[];
	currentAccount: Account;
	dashboardNavigationItems: DashboardListItems[];
}

export function DashboardNavigation({
	accountAppsNumber,
	accountIcon,
	accounts,
	currentAccount,
	dashboardNavigationItems,
}: DashboardNavigationProps) {
	const navigate = useNavigate();

	return (
		<div className="dashboard-navigation-container">
			<ClayDropDown
				trigger={
					<div className="dashboard-navigation-header">
						<div className="dashboard-navigation-header-left-content">
							<img
								alt="account logo"
								className="dashboard-navigation-header-logo"
								src={getAccountImage(accountIcon)}
							/>

							<div className="dashboard-navigation-header-text-container">
								<span
									className="dashboard-navigation-header-title"
									title={currentAccount?.name}
								>
									{currentAccount?.name?.length >= 18
										? `${currentAccount?.name.substring(
												0,
												18
										  )} ...`
										: currentAccount?.name}
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
							active={account.id === currentAccount?.id}
							key={account.id}
							onClick={() =>
								CommerceSelectAccountImpl.selectAccount(
									account.id
								).then(() => {
									Liferay.CommerceContext.account = {
										accountId: account.id,
									};

									navigate('/');

									window.location.reload();
								})
							}
						>
							{account.name}
						</ClayDropDown.Item>
					))}
				</ClayDropDown.ItemList>
			</ClayDropDown>

			<div className="dashboard-navigation-body">
				{dashboardNavigationItems.map((navigationMock, index) => (
					<DashboardNavigationList
						key={index}
						navigationItemMock={navigationMock}
					/>
				))}
			</div>
		</div>
	);
}
