/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';

import {DashboardNavigationList} from './DashboardNavigationList';

import './DashboardNavigation.scss';
import useAccounts from '../../hooks/data/useAccounts';
import {Liferay} from '../../liferay/liferay';
import CommerceSelectAccountImpl from '../../services/rest/CommerceSelectAccount';
import {AppProps} from '../DashboardTable/DashboardTable';
import AccountSearchDropdown from './AccountSearchDropdown';

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
	accountIcon?: string;
	accountsSearch: ReturnType<typeof useAccounts>;
	currentAccount?: Account;
	dashboardNavigationItems: DashboardListItems[];
};

const DropdownItems: React.FC<{
	accounts: DashboardNavigationProps['accountsSearch']['items'];
}> = ({accounts = []}) => {
	return (
		<ClayDropDown.ItemList>
			{accounts.map((_account: UserAccount) => {
				const account = _account;
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
							alt="logo"
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
	accountAppsNumber,
	accountIcon,
	accountsSearch,
	currentAccount,
	dashboardNavigationItems,
}: DashboardNavigationProps) {
	return (
		<div className="dashboard-navigation-container">
			{accountsSearch && (
				<AccountSearchDropdown
					DropdownItems={DropdownItems}
					accountAppsNumber={accountAppsNumber}
					accountIcon={accountIcon}
					accountsSearch={accountsSearch}
					currentAccount={currentAccount}
				/>
			)}

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
