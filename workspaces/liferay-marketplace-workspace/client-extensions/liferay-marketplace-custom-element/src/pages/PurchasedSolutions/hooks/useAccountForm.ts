/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {zodResolver} from '@hookform/resolvers/zod';
import {useCallback, useEffect, useMemo} from 'react';
import {useForm} from 'react-hook-form';

import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import zodSchema from '../../../schema/zod';
import fetcher from '../../../services/fetcher';
import {StepType} from '../PurchasedSolutions';

const singleAccount = 1;

const useAccountForm = (
	step: StepType,
	setStep: React.Dispatch<React.SetStateAction<StepType>>
) => {
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
			accountQuantity: 0,
			accountSelected: undefined,
			accounts: [],
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

			if (userAccounts.length === singleAccount) {
				setValue('accountSelected', userAccounts[0]);
			}

			setValue('accounts', userAccounts);
			setValue('accountQuantity', userAccounts.length);
		})();
	}, [fetchAccount, myUserAccount, setStep, setValue, step]);

	const form = watch();

	const agreeToTermsAndConditions = form['agreeToTermsAndConditions'];

	const hasAllValidations = agreeToTermsAndConditions && isValid;

	return {
		formUtil: {
			form,
			formState: {errors, isValid},
			getValues,
			handleSubmit,
			hasAllValidations,
			register,
			setValue,
			watch,
		},
	};
};
export default useAccountForm;
