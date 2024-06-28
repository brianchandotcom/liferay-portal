/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo, useState} from 'react';
import {Outlet, useLocation, useNavigate} from 'react-router-dom';
import useSWR from 'swr';
import {z} from 'zod';

import {useMarketplaceContext} from '../../context/MarketplaceContext';
import {Analytics} from '../../core/Analytics';
import {ORDER_TYPES} from '../../enums/Order';
import useMarketplaceSpringBootOAuth2 from '../../hooks/useMarketplaceSpringBootOAuth2';
import zodSchema from '../../schema/zod';
import fetcher from '../../services/fetcher';
import CommerceSelectAccountImpl from '../../services/rest/CommerceSelectAccount';
import headlessCommerceDeliveryCart from '../../services/rest/HeadlessCommerceDeliveryCart';
import {scrollToTop} from '../../utils/browser';
import AccountEmailInfo from '../CustomerDashboard/pages/Apps/App/Licenses/CreateLicense/AccountInfo';
import {ProductCardRevamp} from '../GetApp/components/ProductCard/ProductCard';
import {StepWizardRevamp} from '../GetApp/components/StepWizard/StepWizard';

type GetSolutionOutletProps = {
	product: DeliveryProduct;
};

export type UserForm = z.infer<typeof zodSchema.accountCreator> & {
	accountSelected: Account | undefined;
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
	const [selectedAccount, setSelectedAccount] = useState<any>(null);
	const location = useLocation();
	const marketplaceContext = useMarketplaceContext();
	const marketplaceSpringBootOAuth2 = useMarketplaceSpringBootOAuth2();
	const navigate = useNavigate();

	const {channel, myUserAccount} = marketplaceContext;

	const accountBriefs = useMemo(
		() => myUserAccount?.accountBriefs || [],
		[myUserAccount?.accountBriefs]
	);

	const sku = product?.skus?.[0]?.id;

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

	const currentAccount = selectedAccount || accounts[0];

	const onSubmit = async () => {
		const accountId = Number(currentAccount?.id);

		const cart = await headlessCommerceDeliveryCart.createCart(channel.id, {
			accountId,
			cartItems: [
				{
					price: {
						currency: channel.currencyCode,
						discount: 0,
					},
					productId: product?.productId,
					quantity: 1,
					settings: {
						maxQuantity: 1,
					},
					skuId: sku,
				},
			],
			currencyCode: channel.currencyCode,
			orderTypeExternalReferenceCode: ORDER_TYPES.SOLUTIONS7,
		});

		const [trialAvailability] = await Promise.all([
			marketplaceSpringBootOAuth2.getTrialAvailability(),
			headlessCommerceDeliveryCart.checkoutCart(cart.id),
			CommerceSelectAccountImpl.selectAccount(accountId),
		]);

		await marketplaceSpringBootOAuth2.provisioningTrial(cart.id);

		const maxTrialsReached = trialAvailability.fallback
			? false
			: trialAvailability.available === 0;

		Analytics.track('TRIAL_CREATION', {
			accountId,
			productName: product.name,
		});

		scrollToTop();

		navigate(`/finish${maxTrialsReached ? '?state=hold' : ''}`, {
			replace: true,
		});
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
				{selectedAccount && (
					<>
						<hr />

						<div className="d-flex flex-row justify-content-between">
							<strong className="account-banner-title-text align-self-center">
								Account Selected
							</strong>

							<AccountEmailInfo
								userAccount={{
									...myUserAccount,
									...selectedAccount,
									image: selectedAccount.logoURL,
								}}
							/>
						</div>
					</>
				)}
			</ProductCardRevamp>

			<div className="border d-flex flex-column mt-7 p-5 rounded">
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
							accounts,
							navigate,
							onSubmit,
							product,
							selectedAccount: currentAccount,
							setSelectedAccount,
						}}
					/>
				</div>
			</div>
		</div>
	);
};

export default GetSolutionOutlet;
