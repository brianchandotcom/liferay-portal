/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {zodResolver} from '@hookform/resolvers/zod';
import {useCallback, useEffect, useMemo, useState} from 'react';
import {useForm} from 'react-hook-form';
import {z} from 'zod';

import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import zodSchema from '../../../schema/zod';
import fetcher from '../../../services/fetcher';

const SINGLE_ACCOUNT = 1;

export type UserForm = z.infer<typeof zodSchema.accountCreator> & {
	accountSelected: Account | undefined;
};

const useAccountForm = () => {
	const [accountQuantity, setAccountQuantity] = useState<number>(0);
	const [accounts, setAccounts] = useState<Account[]>([]);
	const {myUserAccount} = useMarketplaceContext();

	const accountBriefs = useMemo(() => myUserAccount?.accountBriefs || [], [
		myUserAccount?.accountBriefs,
	]);

	const form = useForm<UserForm>({
		defaultValues: {
			accountSelected: undefined,
			companyName: '',
			country: '',
			emailAddress: '',
			extension: '',
			familyName: '',
			givenName: '',
			phone: {code: '+1', flag: 'en-us'},
			phoneNumber: undefined,
		},
		mode: 'all',
		resolver: zodResolver(zodSchema.accountCreator),
	});

	const {setValue, watch} = form;

	const fetchAccount = useCallback(async () => {
		const fetchedAccounts = [];

		for (const accountBrief of accountBriefs) {
			const accountInfo = await fetcher(
				`o/headless-admin-user/v1.0/accounts/${Number(
					accountBrief.id
				)}?nestedFields=accountUserAccounts`
			);

			fetchedAccounts.push(accountInfo);
		}

		return fetchedAccounts;
	}, [accountBriefs]);

	useEffect(() => {
		if (myUserAccount) {
			const {emailAddress, familyName, givenName} = myUserAccount;
			setValue('emailAddress', emailAddress || '');
			setValue('givenName', givenName || '');
			setValue('familyName', familyName || '');
		}

		(async () => {
			const userAccounts = await fetchAccount();

			if (userAccounts.length === SINGLE_ACCOUNT) {
				setValue('accountSelected', userAccounts[0]);
			}

			setAccounts(userAccounts);
			setAccountQuantity(userAccounts.length);
		})();
	}, [fetchAccount, myUserAccount, setValue]);

	return {
		...form,
		SINGLE_ACCOUNT,
		accountQuantity,
		accountSelected: watch('accountSelected'),
		accounts,
		setAccounts,
	};
};
export default useAccountForm;
