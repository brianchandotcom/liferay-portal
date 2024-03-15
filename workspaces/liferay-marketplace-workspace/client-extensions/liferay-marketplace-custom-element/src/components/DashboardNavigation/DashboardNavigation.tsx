/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';

import {getAccountImage} from '../../utils/util';
import {DashboardNavigationList} from './DashboardNavigationList';

import './DashboardNavigation.scss';

import InfiniteScroll from 'react-infinite-scroll-component';

import useAccounts from '../../hooks/data/useAccounts';
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
	accountAppsNumber?: number;
	accountIcon: string;
	accountsSearch: ReturnType<typeof useAccounts>;
	currentAccount: Account;
	dashboardNavigationItems: DashboardListItems[];
};

const DropdownItems: React.FC<{
	accounts: DashboardNavigationProps['accountsSearch']['items'];
}> = ({accounts = []}) => {
	return (
		<ClayDropDown.ItemList>
			{accounts.map((_account) => {
				const account = _account as UserAccount;
				const isActive =
					account.id === Liferay.CommerceContext.account?.accountId;

				return (
					<ClayDropDown.Item
						active={isActive}
						autoFocus={isActive}
						className="mb-1"
						data-index={3}
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
				);
			})}
		</ClayDropDown.ItemList>
	);
};

export function DashboardNavigation({
	// accountAppsNumber,

	accountIcon,
	accountsSearch,
	currentAccount,
	dashboardNavigationItems,
}: DashboardNavigationProps) {
	const {infiniteSearch, items} = accountsSearch;
	const {allowFetching, fetchMore, search, setSearch} = infiniteSearch;

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

								{/* {!!accountAppsNumber && (
									<span className="dashboard-navigation-header-apps">
										{accountAppsNumber} apps
									</span>
								)} */}
							</div>
						</div>

						<ClayIcon
							className="dashboard-navigation-header-arrow-down"
							symbol="caret-bottom"
						/>
					</div>
				}
			>
				{!!(
					!!infiniteSearch.search.length ||
					infiniteSearch.totalCount > 5
				) && (
					<div className="dashboard-navigation-container-dropdown-body">
						<Search search={search} setSearch={setSearch} />
					</div>
				)}

				{infiniteSearch.totalCount > 5 ? (
					<InfiniteScroll
						dataLength={items.length}
						endMessage={
							<p className="text-center">
								<b>Yay! You have seen it all</b>
							</p>
						}
						hasMore={allowFetching}
						height={200}
						loader={<ClayLoadingIndicator />}
						next={fetchMore}
					>
						<DropdownItems accounts={items} />
					</InfiniteScroll>
				) : (
					<div className="py-2">
						<DropdownItems accounts={items} />
					</div>
				)}
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
