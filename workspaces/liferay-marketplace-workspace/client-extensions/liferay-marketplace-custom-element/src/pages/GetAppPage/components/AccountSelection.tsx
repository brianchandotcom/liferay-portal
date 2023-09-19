/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import {useEffect, useState} from 'react';

import RadioCardList, {
	RadioCardContent,
} from '../../../components/RadioCardList/RadioCardList';
import {getAccountInfo, getUserAccount} from '../../../utils/api';

const enabledAccountRoles = ['Account Administrator', 'Account Buyer'];

interface AccountSelectionProps {
	onSelectAccount: (account: Account) => void;
}

const AccountSelection = ({onSelectAccount}: AccountSelectionProps) => {
	const [userAccount, setUserAccount] = useState<UserAccount>();
	const [accounts, setAccounts] = useState<RadioCardContent<Account>[]>([]);

	const getUserAccountList = async () => {
		const userAccount: UserAccount = await getUserAccount();

		setUserAccount(userAccount);

		const radioAccountList: RadioCardContent<Account>[] = [];

		for (const accountBrief of userAccount.accountBriefs) {
			const displayAccount = accountBrief.roleBriefs.reduce(
				(display, roleBrief) => {
					if (enabledAccountRoles.includes(roleBrief.name)) {
						return true;
					}

					return display;
				},
				false
			);

			if (displayAccount) {
				const accountInfo: Account = await getAccountInfo({
					accountId: Number(accountBrief.id),
				});

				radioAccountList.push({
					imageURL: accountInfo.logoURL,
					title: accountInfo.name,
					value: accountInfo,
				});
			}
		}

		setAccounts(radioAccountList);
	};

	useEffect(() => {
		getUserAccountList();
	}, []);

	return (
		<div>
			<div className="mb-4">
				<span>
					{`Accounts available for `}

					<strong>{userAccount?.emailAddress}</strong>

					{` (you)`}
				</span>
			</div>

			<RadioCardList
				contentList={accounts}
				leftRadio
				onSelect={onSelectAccount}
				showImage
			/>

			<div>
				<span>Not seeing a specific Account?</span>

				<ClayLink href="http://help.liferay.com/">
					Contact Support
				</ClayLink>
			</div>
		</div>
	);
};

export default AccountSelection;
