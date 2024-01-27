/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import {useModal} from '@clayui/modal';
import {useCallback, useEffect, useState} from 'react';

import {Checkbox} from '../../../components/Checkbox/Checkbox';
import {ContentModal} from '../../../components/ContentModal/ContentModal';
import RadioCardList, {
	RadioCardContent,
} from '../../../components/RadioCardList/RadioCardList';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import i18n from '../../../i18n';
import {useAppContext} from '../../../manage-app-state/AppManageState';
import {TYPES} from '../../../manage-app-state/actionTypes';
import {getAccountInfo} from '../../../utils/api';
import {getEulaDescription} from '../../../utils/util';

const enabledAccountRoles = ['Account Administrator', 'Account Buyer'];

interface AccountSelectionProps {
	isFreeApp: boolean;
	onSelectAccount: (account: Account) => void;
	selectedAccount: Account | undefined;
	userAccount?: UserAccount;
}

const AccountSelection = ({
	isFreeApp,
	onSelectAccount,
	selectedAccount,
	userAccount,
}: AccountSelectionProps) => {
	const [{eula, eulaCheckbox}, dispatch] = useAppContext();

	const {properties} = useMarketplaceContext();

	const eulaModal = useModal();

	const [accounts, setAccounts] = useState<RadioCardContent<Account>[]>([]);

	const getAccountList = useCallback(async () => {
		const eula = await getEulaDescription();

		dispatch({
			payload: {
				value: eula,
			},
			type: TYPES.UPDATE_EULA,
		});

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
	}, [dispatch, selectedAccount?.externalReferenceCode, userAccount]);

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
			<p className="mb-4 secondary-text">
				{`Accounts available for `}

				<strong>{userAccount?.emailAddress}</strong>

				{` (you)`}
			</p>

			<RadioCardList
				contentList={accounts}
				leftRadio
				onSelect={handleSelectAccount}
				showImage
			/>

			{isFreeApp ? (
				<div className="align-items-start d-flex eula-container mt-4">
					<Checkbox
						checked={eulaCheckbox}
						onChange={() =>
							dispatch({
								payload: {value: !eulaCheckbox},
								type: TYPES.UPDATE_EULA_CHECKBOX,
							})
						}
					/>
					I have read and agree to the
					<a onClick={() => eulaModal.onOpenChange(true)}>
						&nbsp;End User License Agreement&nbsp;
					</a>
					and the
					<a onClick={() => window.open(properties.eulaBaseURL)}>
						&nbsp;Terms&nbsp;
					</a>
					of Service.
				</div>
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

			{eulaModal.open && (
				<ContentModal
					description={eula}
					header={i18n.translate('end-user-license-agreement')}
					{...eulaModal}
				/>
			)}
		</div>
	);
};

export default AccountSelection;
