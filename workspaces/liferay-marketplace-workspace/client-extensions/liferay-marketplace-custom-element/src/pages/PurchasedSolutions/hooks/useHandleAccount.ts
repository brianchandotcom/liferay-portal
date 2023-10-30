/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetcher from '../../../services/fetcher';

type FormType = {
	company?: string;
	customFields?: {
		customValue?: {
			data: string;
		};
		name?: string;
	}[];
	externalReferenceCode?: string;
	name?: string;
	type?: string;
};

const accountTypes = {
	BUSINESS: 'business',
	PERSON: 'person',
};

const useHandleAccount = ({
	mutateMyUserAccout,
	myUserAccount,
}: {
	mutateMyUserAccout: any;
	myUserAccount: any;
}) => {
	const addUserAccountInAccount = async (data: Account) => {
		if (myUserAccount && data?.externalReferenceCode) {
			await fetcher.post(
				`o/headless-admin-user/v1.0/accounts/by-external-reference-code/${data?.externalReferenceCode}/user-accounts/by-external-reference-code/${myUserAccount.externalReferenceCode}`
			);

			mutateMyUserAccout(
				(prevMyUserAccount: UserAccount) => prevMyUserAccount,
				{
					revalidate: true,
				}
			);
		}
	};

	const updateAccount = async ({
		accountId,
		data,
	}: {
		accountId: number;
		data: FormType;
	}) => {
		const response = await fetcher
			.patch(`o/headless-admin-user/v1.0/accounts/${accountId}`, data)
			.catch((error) => console.error(error));

		await addUserAccountInAccount(response);
	};

	const createAccount = async (data: FormType) => {
		const response: Account = await fetcher
			.post('o/headless-admin-user/v1.0/accounts', data)
			.catch((error) => console.error(error));

		await addUserAccountInAccount(response);

		return response;
	};

	const formDataTransform = (form: UserForm) => {
		const submitForm: FormType = {
			company: form.companyName,
			customFields: [
				{
					customValue: {
						data: form.industry,
					},
					name: 'Industry',
				},
				{
					customValue: {
						data: `${form.phone.code} ${form.phoneNumber} ${form.extension}`,
					},
					name: 'Contact Phone',
				},
				{
					customValue: {
						data: `${form.emailAddress}`,
					},
					name: 'Contact Email',
				},
			],
			externalReferenceCode: `ACCOUNT${form.givenName}${form.familyName}`,
			name: `${form.givenName} ${form.familyName}`,
			type: accountTypes.PERSON,
		};

		if (form.accountQuantity === 1) {
			delete submitForm.type;
			delete submitForm.name;
			delete submitForm.externalReferenceCode;

			return {
				...submitForm,
				customFields: [
					{
						customValue: {
							data: form.industry,
						},
						name: 'Industry',
					},
					{
						customValue: {
							data: `${form.phone.code} ${form.phoneNumber} ${form.extension}`,
						},
						name: 'Contact Phone',
					},
				],
			};
		}

		if (form.accountQuantity > 1) {
			delete submitForm.type;
			delete submitForm.name;
			delete submitForm.externalReferenceCode;

			return {
				...submitForm,
				customFields: [
					{
						customValue: {
							data: form.industry,
						},
						name: 'Industry',
					},
					{
						customValue: {
							data: `${form.phone.code} ${form.phoneNumber} ${form.extension}`,
						},
						name: 'Contact Phone',
					},
				],
			};
		}

		return submitForm;
	};

	return {createAccount, formDataTransform, updateAccount};
};

export default useHandleAccount;
