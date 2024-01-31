/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import useSWR from 'swr';

import RadioCardList, {
	RadioCardContent,
} from '../../../components/RadioCardList/RadioCardList';
import {getAccountInfo} from '../../../utils/api';
import LicenseTermsCheckbox from '../containers/LicenseTermsCheckbox';

const enabledAccountRoles = ['Account Administrator', 'Account Buyer'];

type AccountSelectionProps = {
	isFreeApp: boolean;
	onSelectAccount: (account: Account) => void;
	selectedAccount: Account | undefined;
	userAccount?: UserAccount;
};

const AccountSelection: React.FC<AccountSelectionProps> = ({
	isFreeApp,
	onSelectAccount,
	selectedAccount,
	userAccount,
}) => {
	const {data: accounts = []} = useSWR('/accounts', async () => {
		const radioAccountList: RadioCardContent<Account>[] = [];

		for (const accountBrief of userAccount?.accountBriefs ?? []) {
			let displayAccount = false;
			if (!accountBrief.roleBriefs.length) {
				const accountInfo: Account = await getAccountInfo({
					accountId: Number(accountBrief.id),
				});

				if (accountInfo.type === 'person') {
					displayAccount = true;
				}
			}
			else {
				displayAccount = accountBrief.roleBriefs.some((roleBrief) =>
					enabledAccountRoles.includes(roleBrief.name)
				);
			}

			if (displayAccount) {
				const accountInfo: Account = await getAccountInfo({
					accountId: Number(accountBrief.id),
				});

				radioAccountList.push({
					id: accountBrief.id,
					imageURL: accountInfo.logoURL,
					selected:
						selectedAccount?.externalReferenceCode ===
						accountInfo.externalReferenceCode,
					title: accountInfo.name,
					value: accountInfo,
				});
			}
		}

		return radioAccountList;
	});

	const handleSelectAccount = (radioOption: RadioOption<Account>) => {
		if (radioOption.value.id !== selectedAccount?.id) {
			onSelectAccount(radioOption.value);
		}
	};

	return (
		<div>
			<p className="mb-4 secondary-text">
				{`Accounts available for `}

				<strong>{userAccount?.emailAddress}</strong>

				{` (you)`}
			</p>

			<RadioCardList
				contentList={accounts.map((account) => ({
					...account,
					selected: selectedAccount?.id === account?.id,
					title: <h5>{account.title}</h5>,
				}))}
				leftRadio
				onSelect={handleSelectAccount}
				showImage
			/>

			{isFreeApp ? (
				<LicenseTermsCheckbox />
			) : (
				<>
					<span className="mr-1 secondary-text">
						Not seeing a specific Account?
					</span>

					<ClayLink
						className="font-weight-bold"
						href="http://help.liferay.com/"
					>
						Contact Support
					</ClayLink>
				</>
			)}
		</div>
	);
};

export default AccountSelection;
