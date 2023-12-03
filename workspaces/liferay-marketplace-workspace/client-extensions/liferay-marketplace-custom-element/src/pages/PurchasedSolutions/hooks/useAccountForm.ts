/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {zodResolver} from '@hookform/resolvers/zod';
import {useCallback, useEffect, useMemo, useState} from 'react';
import {useForm} from 'react-hook-form';

import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import zodSchema from '../../../schema/zod';
import fetcher from '../../../services/fetcher';
import {StepType} from '../PurchasedSolutions';

const SINGLE_ACCOUNT = 1;

const useAccountForm = (
	step: StepType,
	setStep: React.Dispatch<React.SetStateAction<StepType>>
) => {
	const [accountQuantity, setAccountQuantity] = useState<number>(0);
	const [accounts, setAccounts] = useState<Account[]>([]);
	const {myUserAccount} = useMarketplaceContext();

	const accountBriefs = useMemo(() => myUserAccount?.accountBriefs || [], [
		myUserAccount?.accountBriefs,
	]);

	const {
		formState: {errors, isValid},
		getValues,
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<UserForm>({
		defaultValues: {
			accountSelected: undefined,
			agreeToTermsAndConditions: false,
			companyName: '',
			emailAddress: '',
			extension: '',
			familyName: '',
			givenName: '',
			industry: '',
			phone: {code: '+1', flag: 'en-us'},
			phoneNumber: undefined,
		},
		mode: 'all',
		resolver: zodResolver(zodSchema.accountCreator),
	});

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
	}, [fetchAccount, myUserAccount, setStep, setValue, step]);

	return {
		SINGLE_ACCOUNT,
		accountQuantity,
		accounts,
		formState: {errors, isValid},
		getValues,
		handleSubmit,
		register,
		setAccounts,
		setValue,
		watch,
	};
};
export default useAccountForm;
