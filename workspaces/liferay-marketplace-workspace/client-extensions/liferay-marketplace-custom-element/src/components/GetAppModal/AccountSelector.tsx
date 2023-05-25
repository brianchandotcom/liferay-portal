/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {useEffect} from 'react';

import {getAccountInfoFromCommerce} from '../../utils/api';
import {showAccountImage} from '../../utils/util';
import {RadioCard} from '../RadioCard/RadioCard';

import './AccountSelector.scss';

export function AccountSelector({
	accounts,
	activeAccounts,
	selectedAccount,
	setActiveAccounts,
	setSelectedAccount,
	userEmail,
}: {
	accounts: AccountBrief[];
	activeAccounts: AccountBrief[];
	selectedAccount?: Partial<AccountBrief>;
	setActiveAccounts: (value: Partial<AccountBrief>[]) => void;
	setSelectedAccount: (value: Partial<AccountBrief>) => void;
	userEmail: string;
}) {
	useEffect(() => {
		const getAccountInfos = async () => {
			const accountInfo = await Promise.all(
				accounts?.map(async (account) => {
					const accountInfo = await getAccountInfoFromCommerce(
						account.id
					);

					return accountInfo;
				})
			);

			const filteredAccounts: CommerceAccount[] = accountInfo.filter(
				(account) => account.active
			);

			if (accounts.length === 1) {
				setSelectedAccount(filteredAccounts[0]);
			}

			setActiveAccounts(filteredAccounts);
		};

		getAccountInfos();
	}, [accounts, setActiveAccounts, setSelectedAccount]);

	return (
		<div className="account-selector">
			<span>
				Accounts available for <b>{userEmail}</b>&nbsp;(you){' '}
			</span>

			<div className="account-selector-cards">
				{activeAccounts?.map((account) => {
					return (
						<RadioCard
							icon={showAccountImage(account.logoURL)}
							key={account.id}
							onChange={() => {
								setSelectedAccount(account);
							}}
							position="right"
							selected={selectedAccount?.name === account.name}
							title={account.name}
						/>
					);
				})}
			</div>

			<span className="account-selector-contact-support">
				Not seeing a specific Account? <a href="#">Contact Support</a>
			</span>
		</div>
	);
}
