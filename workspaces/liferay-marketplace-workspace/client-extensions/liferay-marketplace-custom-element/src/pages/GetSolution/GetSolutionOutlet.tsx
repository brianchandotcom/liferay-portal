/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useMemo, useState} from 'react';
import {useForm} from 'react-hook-form';
import {Outlet, useLocation, useNavigate} from 'react-router-dom';
import {z} from 'zod';

import {useMarketplaceContext} from '../../context/MarketplaceContext';
import zodSchema, {zodResolver} from '../../schema/zod';
import fetcher from '../../services/fetcher';
import CommerceSelectAccountImpl from '../../services/rest/CommerceSelectAccount';
import {postOrder} from '../../utils/api';
import AccountEmailInfo from '../CustomerDashboard/pages/Apps/App/Licenses/CreateLicense/AccountInfo';
import {ProductCardRevamp} from '../GetApp/components/ProductCard/ProductCard';
import {StepWizardRevamp} from '../GetApp/components/StepWizard/StepWizard';

type GetSolutionOutletProps = {
	product: DeliveryProduct;
};

export type UserForm = z.infer<typeof zodSchema.accountCreator> & {
	accountSelected: Account | undefined;
};

const productCustomFields = [
	'Github Username',
	'Project Name',
	'Site Initializer',
];

const getProductCustomFields = (customFields: CustomField[]) => {
	let data = {};

	productCustomFields.forEach((fieldName) => {
		customFields.forEach((field) => {
			if (field.name === fieldName) {
				data = {...data, [fieldName]: field.customValue.data};
			}
		});
	});

	return data;
};

const steps = [
	{
		id: 1,
		path: '/',
		title: 'Account Selection',
	},
	{
		id: 2,
		path: '/form',
		title: 'Create Trial',
	},
];

const getIcon = (image = '') => {
	if (window.location.href.startsWith('https')) {
		return image;
	}

	return image.replace('https', 'http');
};

const GetSolutionOutlet: React.FC<GetSolutionOutletProps> = ({product}) => {
	const marketplaceContext = useMarketplaceContext();

	const {channel, myUserAccount} = marketplaceContext;

	const accountBriefs = useMemo(() => myUserAccount?.accountBriefs || [], [
		myUserAccount?.accountBriefs,
	]);

	const [accounts, setAccounts] = useState<any[]>([]);
	const [account, setSelectedAccount] = useState<any>(null);

	const sku = product?.skus?.[0]?.id;
	const navigate = useNavigate();
	const location = useLocation();

	const accountForm = useForm<UserForm>({
		defaultValues: {
			companyName: '',
			country: '',
			emailAddress: myUserAccount.emailAddress,
			extension: '',
			familyName: myUserAccount.familyName,
			givenName: myUserAccount.givenName,
			phone: {code: '+1', flag: 'en-us'},
			phoneNumber: undefined,
		},
		mode: 'all',
		resolver: zodResolver(zodSchema.accountCreator),
	});

	const {setValue} = accountForm;

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
		(async () => {
			const userAccounts = await fetchAccount();

			if (userAccounts.length === 1) {
				setSelectedAccount(userAccounts[0]);
			}

			setAccounts(userAccounts);
		})();
	}, [fetchAccount, myUserAccount, setValue]);

	const customFields =
		product?.customFields?.filter((item) =>
			productCustomFields.find((field) => item.name === field)
		) || [];

	const onSubmit = async (responeAccount?: Account) => {
		const accountId = Number(account?.id || responeAccount?.id);

		await postOrder({
			account: {
				id: accountId,
				type:
					(account?.type as string) ||
					(responeAccount?.type as string),
			},
			accountExternalReferenceCode: account?.externalReferenceCode,
			accountId,
			channel: {
				currencyCode: channel?.currencyCode,
				id: channel?.id,
				type: channel?.type,
			},
			channelId: channel?.id,
			currencyCode: 'USD',
			customFields: getProductCustomFields(customFields),
			orderItems: [
				{
					id: 0,
					quantity: 1,
					skuId: Number(sku),
				},
			],
			orderStatus: 1,
			orderTypeExternalReferenceCode: 'SOLUTIONS7',
			shippingAmount: 0,
			shippingWithTaxAmount: 0,
		});

		await CommerceSelectAccountImpl.selectAccount(accountId);

		navigate('/finish', {replace: true});
	};

	const stepIndex = steps.findIndex(
		(step) => step.path === location.pathname
	);

	return (
		<div>
			<ProductCardRevamp
				icon={getIcon(product?.urlImage)}
				subtitle="7 Days Trial"
				title={product.name}
			>
				{account && (
					<>
						<hr />

						<div className="d-flex flex-row justify-content-between">
							<strong className="account-banner-title-text align-self-center">
								Account Selected
							</strong>

							<AccountEmailInfo
								userAccount={{
									...myUserAccount,
									...account,
									image: account.logoURL,
								}}
							/>
						</div>
					</>
				)}
			</ProductCardRevamp>

			<div className="border d-flex flex-column mt-7 p-5 rounded">
				<main>
					<div className="d-flex flex-column">
						{accounts.length > 1 && (
							<div className="d-flex justify-content-center mb-6">
								<StepWizardRevamp
									className="col-8"
									currentStep={steps[stepIndex]}
									stepIndex={stepIndex}
									steps={steps}
								/>
							</div>
						)}

						<Outlet
							context={{
								accountForm,
								accountSelected: account,
								accounts,
								navigate,
								onSubmit,
								product,
								setAccounts,
								setSelectedAccount,
							}}
						/>
					</div>
				</main>
			</div>
		</div>
	);
};

export default GetSolutionOutlet;
