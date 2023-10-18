/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import {useCallback, useEffect, useState} from 'react';

import RadioCardList, {
	RadioCardContent,
} from '../../../components/RadioCardList/RadioCardList';
import {getAccountInfo} from '../../../utils/api';

const enabledAccountRoles = ['Account Administrator', 'Account Buyer'];

interface AccountSelectionProps {
	onSelectAccount: (account: Account) => void;
	selectedAccount: Account | undefined;
	userAccount?: UserAccount;
}

const AccountSelection = ({
	onSelectAccount,
	selectedAccount,
	userAccount,
}: AccountSelectionProps) => {
	const [accounts, setAccounts] = useState<RadioCardContent<Account>[]>([]);

	const getAccountList = useCallback(async () => {
		if (userAccount) {
			const radioAccountList: RadioCardContent<Account>[] = [];

			for (const accountBrief of userAccount.accountBriefs) {
				let displayAccount = false;
				if (!accountBrief.roleBriefs.length) {
					const accountInfo: Account = await getAccountInfo({
						accountId: Number(accountBrief.id),
					});

					if (accountInfo.type === 'person') {
						displayAccount = true;
					}
				} else {
					displayAccount = accountBrief.roleBriefs.some((roleBrief) =>
						enabledAccountRoles.includes(roleBrief.name)
					);
				}

				if (displayAccount) {
					const accountInfo: Account = await getAccountInfo({
						accountId: Number(accountBrief.id),
					});

					radioAccountList.push({
						imageURL: accountInfo.logoURL,
						selected:
							selectedAccount?.externalReferenceCode ===
							accountInfo.externalReferenceCode,
						title: <h5>{accountInfo.name}</h5>,
						value: accountInfo,
					});
				}
			}

			setAccounts(radioAccountList);
		}
	}, [selectedAccount?.externalReferenceCode, userAccount]);

	useEffect(() => {
		getAccountList();
	}, [getAccountList]);

	const handleSelectAccount = (radioOption: RadioOption<Account>) => {
		onSelectAccount(radioOption.value);

		setAccounts((previousValue) =>
			previousValue.map((account, index) => ({
				...account,
				selected: index === radioOption.index,
			}))
		);
	};

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
				onSelect={handleSelectAccount}
				showImage
			/>

			<div>
				<span className="mr-1">Not seeing a specific Account?</span>

				<ClayLink href="http://help.liferay.com/">
					Contact Support
				</ClayLink>
			</div>
		</div>
	);
};

export default AccountSelection;
