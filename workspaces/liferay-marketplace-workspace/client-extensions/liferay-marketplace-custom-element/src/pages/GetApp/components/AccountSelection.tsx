/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useMemo} from 'react';
import useSWR from 'swr';

import RadioCardList from '../../../components/RadioCardList/RadioCardList';
import headlessCommerceAdminUser from '../../../services/rest/HeadlessCommerceAdminUser';
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
	const accountBriefs = useMemo(() => userAccount?.accountBriefs ?? [], [
		userAccount?.accountBriefs,
	]);

	const accountBriefIds = accountBriefs.map(({id}) => id);

	const {data: accountsInfo = [], isLoading} = useSWR(
		{accountBriefIds, key: 'commerce-account-info'},
		() =>
			Promise.all(
				accountBriefIds.map((accountBriefId) =>
					headlessCommerceAdminUser.getAccountInfo(accountBriefId)
				)
			)
	);

	const accounts = useMemo(
		() =>
			accountsInfo
				.map((accountInfo, index) => {
					const accountBrief = accountBriefs[index];
					let displayAccount = accountInfo.type === 'person';

					if (accountBrief.roleBriefs.length) {
						displayAccount = accountBriefs[
							index
						].roleBriefs.some((roleBrief) =>
							enabledAccountRoles.includes(roleBrief.name)
						);
					}

					return {
						displayAccount,
						id: accountBrief.id,
						imageURL: accountInfo.logoURL,
						selected:
							selectedAccount?.externalReferenceCode ===
							accountInfo.externalReferenceCode,
						title: accountInfo.name,
						value: accountInfo,
					};
				})
				.filter(({displayAccount}) => displayAccount),
		[accountBriefs, accountsInfo, selectedAccount?.externalReferenceCode]
	);

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

			{isLoading ? (
				<ClayLoadingIndicator />
			) : accounts.length ? (
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
			) : (
				<p className="font-weight-bold my-5">No accounts available</p>
			)}

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
