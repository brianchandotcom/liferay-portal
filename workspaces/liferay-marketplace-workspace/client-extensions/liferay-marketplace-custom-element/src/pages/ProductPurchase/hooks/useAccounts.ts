/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo, useState} from 'react';
import useSWR from 'swr';

import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import fetcher from '../../../services/fetcher';

const useAccounts = () => {
	const {myUserAccount} = useMarketplaceContext();
	const [selectedAccount, setSelectedAccount] = useState<Account | null>(
		null
	);

	const accountBriefs = useMemo(
		() => myUserAccount?.accountBriefs || [],
		[myUserAccount?.accountBriefs]
	);

	const {data: accounts = []} = useSWR(
		{accountBriefs, key: '/accounts-briefs/'},
		() => {
			return Promise.all(
				accountBriefs.map((accountBrief) =>
					fetcher(
						`o/headless-admin-user/v1.0/accounts/${Number(
							accountBrief.id
						)}?nestedFields=accountUserAccounts`
					)
				)
			);
		}
	);

	return {
		accounts,
		myUserAccount,
		selectedAccount: accounts.length === 1 ? accounts[0] : selectedAccount,
		setSelectedAccount,
	};
};

export default useAccounts;
