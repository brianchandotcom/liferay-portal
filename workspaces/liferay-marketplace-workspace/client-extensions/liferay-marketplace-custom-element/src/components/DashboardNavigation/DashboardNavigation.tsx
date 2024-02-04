/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';

import {getAccountImage} from '../../utils/util';
import {DashboardNavigationList} from './DashboardNavigationList';

import './DashboardNavigation.scss';

import classNames from 'classnames';
import {useState} from 'react';

import {Liferay} from '../../liferay/liferay';
import CommerceSelectAccountImpl from '../../services/rest/CommerceSelectAccount';
import {AppProps} from '../DashboardTable/DashboardTable';
import Search from './Search';

export type DashboardListItems = {
	itemIcon: string;
	itemName: string;
	itemSelected?: boolean;
	itemTitle: string;
	items?: AppProps[];
	path: string;
};

type DashboardNavigationProps = {
	accountAppsNumber: number;
	accountIcon: string;
	accounts: Account[];
	currentAccount: Account;
	dashboardNavigationItems: DashboardListItems[];
};

export function DashboardNavigation({
	accountAppsNumber,
	accountIcon,
	accounts,
	currentAccount,
	dashboardNavigationItems,
}: DashboardNavigationProps) {
	const [search, setSearch] = useState('');

	const filteredAccounts = accounts.filter(({name}) =>
		search ? name.toLowerCase().includes(search.toLowerCase()) : true
	);

	const fewAccountsToSearch = accounts.length <= 5;

	return (
		<div className="dashboard-navigation-container">
			<ClayDropDown
				menuElementAttrs={{
					className: 'dashboard-navigation-container-dropdown p-0',
				}}
				trigger={
					<div className="dashboard-navigation-header">
						<div className="dashboard-navigation-header-left-content">
							<img
								alt="account logo"
								className="dashboard-navigation-header-logo"
								draggable={false}
								src={getAccountImage(accountIcon)}
							/>

							<div className="dashboard-navigation-header-text-container">
								<span
									className="dashboard-navigation-header-title"
									title={currentAccount?.name}
								>
									{currentAccount?.name}
								</span>

								{!!accountAppsNumber && (
									<span className="dashboard-navigation-header-apps">
										{accountAppsNumber} apps
									</span>
								)}
							</div>
						</div>

						<ClayIcon
							className="dashboard-navigation-header-arrow-down"
							symbol="caret-bottom"
						/>
					</div>
				}
			>
				<div className="dashboard-navigation-container-dropdown-body">
					{!fewAccountsToSearch && (
						<Search search={search} setSearch={setSearch} />
					)}

					<ClayDropDown.ItemList
						className={classNames({
							'overflow-auto': !fewAccountsToSearch,
							'overflow-hidden': fewAccountsToSearch,
						})}
					>
						{filteredAccounts.map((account) => (
							<ClayDropDown.Item
								active={account.id === currentAccount?.id}
								className="mb-1"
								key={account.id}
								onClick={() =>
									CommerceSelectAccountImpl.selectAccount(
										account.id
									).then(() => {
										Liferay.CommerceContext.account = {
											accountId: account.id,
										};

										window.location.reload();
									})
								}
							>
								<img
									className="mr-4 rounded-circle"
									height={32}
									src={account.logoURL}
									width={32}
								/>

								{account.name}
							</ClayDropDown.Item>
						))}

						{!filteredAccounts.length && search && (
							<span className="px-2">
								No Accounts match that name
							</span>
						)}
					</ClayDropDown.ItemList>
				</div>
			</ClayDropDown>

			<div className="dashboard-navigation-body">
				{dashboardNavigationItems.map((dashboardNavigation, index) => (
					<DashboardNavigationList
						dashboardNavigation={dashboardNavigation}
						key={index}
					/>
				))}
			</div>
		</div>
	);
}
