/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';

import useCart from '../../hooks/useCart';
import {
	getPaymentMethodURL,
	postCheckoutCart,
	postEmailAppInformation,
} from '../../utils/api';
import AccountSelection from './components/AccountSelection';
import ProductFooter from './components/Footer';
import {LicenseSelector} from './components/LicenseSelector';
import ProductCard from './components/ProductCard/ProductCard';
import {SelectPaymentMethod} from './components/SelectPaymentMethod/SelectPaymentMethod';
import StepWizard from './components/StepWizard/StepWizard';
import {initialBillingAddress} from './constants/initialBillingAddress';
import {paymentMethod} from './enums/paymentMethod';
import {StepType} from './enums/stepType';
import useGetAddresses from './hooks/useGetAddresses';
import useGetChannelInfo from './hooks/useGetChannelInfo';
import useGetProduct from './hooks/useGetProduct';
import useGetProductSkus from './hooks/useGetProductSkus';
import useProductPriceModel from './hooks/useProductPriceModel';
import buildNewCart from './utils/buildNewCart';
import {getProductOrderTypes} from './utils/getProductOrderTypes';
import {getProductSpecificationValues} from './utils/getProductSpecificationValues';
import getReplaceCurrentURL from './utils/getReplaceCurrentURL';
import {postCartByPaymentMethod} from './utils/postCartByPaymentMethod';

export type GetAppForm = {
	account?: Account;
	product?: Product;
	selectedPaymentMethod: paymentMethod;
	selectedSKU?: SKU;
	selectedTimeline?: string;
};

const GetAppFlow = () => {
	const [billingAddress, setBillingAddress] = useState<BillingAddress>(
		initialBillingAddress
	);
	const [email, setEmail] = useState<string>('');
	const [enablePurchaseButton, setEnablePurchaseButton] = useState<boolean>(
		false
	);
	const [enableTrialMethod, setEnableTrialMethod] = useState<boolean>(false);
	const [licenseSelected, setLincenseSelected] = useState<boolean>(false);
	const [orderType, setOrderType] = useState<OrderType>();
	const [purchaseOrderNumber, setPurchaseOrderNumber] = useState<string>('');
	const [step, setStep] = useState<StepType>(StepType.ACCOUNT);

	const {setValue, watch} = useForm<GetAppForm>({
		defaultValues: {
			account: undefined,
			product: undefined,
			selectedPaymentMethod: paymentMethod.PAY,
			selectedSKU: undefined,
			selectedTimeline: '',
		},
	});

	const {account, product, selectedPaymentMethod, selectedSKU} = watch();

	const {productId} = useGetProduct(
		product,
		useCallback((value: Product) => setValue('product', value), [setValue])
	);
	const {sku} = useGetProductSkus(setEnableTrialMethod, product);
	const {channel} = useGetChannelInfo();
	const {addresses} = useGetAddresses(account?.id);
	const {isFreeApp, priceModel} = useProductPriceModel(product);
	const cartUtil = useCart({
		accountId: account?.id!,
		channelId: channel?.id,
		orderType,
	});

	useEffect(() => {
		if (cartUtil?.cartItems?.length) {
			setLincenseSelected(true);
			setEnablePurchaseButton(true);
		} else {
			setEnablePurchaseButton(false);
			setLincenseSelected(false);
		}
	}, [cartUtil?.cartItems?.length]);

	useEffect(() => {
		(async () => {
			if (productId) {
				const productSpecificationValues = await getProductSpecificationValues(
					Number(productId)
				);

				const orderType = await getProductOrderTypes(
					productSpecificationValues
				);

				setOrderType(orderType);
			}
		})();
	}, [productId]);

	async function handleGetApp(orderId?: number) {
		const productSpecificationValues = await getProductSpecificationValues(
			Number(productId)
		);

		const productType = productSpecificationValues?.en_US;

		const orderType = await getProductOrderTypes(
			productSpecificationValues
		);

		const cart = buildNewCart({
			billingAddress,
			channel,
			email,
			isFreeApp,
			orderType,
			product,
			purchaseOrderNumber,
			selectedAccount: account,
			selectedPaymentMethod,
			selectedSKU,
			sku,
		});

		const cartResponse = orderId
			? await cartUtil.updateCartItems(orderId, {
					...cart,
					cartItems: cartUtil.cartItems,
			  })
			: await postCartByPaymentMethod(cart, channel.id);

		await postCheckoutCart({cartId: cartResponse.id});

		await postEmailAppInformation({
			dashboardLink: getReplaceCurrentURL(
				'get-app',
				'customer-dashboard'
			),
			orderID: cartResponse.id,
			priceModel,
			productName: product?.name.en_US,
			productType,
		});

		const nextStepsCallbackURL = getReplaceCurrentURL(
			'get-app',
			'next-steps',
			`${encodeURIComponent(cartResponse.id)}`
		);

		if (selectedPaymentMethod === paymentMethod.PAY) {
			const paymentMethodURL = await getPaymentMethodURL(
				cartResponse.id,
				nextStepsCallbackURL
			);

			window.location.href = paymentMethodURL;

			return;
		}

		window.location.href = nextStepsCallbackURL;
	}

	const StepsInformation = {
		[StepType.ACCOUNT]: {
			backStep: StepType.ACCOUNT,
			component: (
				<AccountSelection
					onSelectAccount={(account: Account) => {
						setValue('account', account);
					}}
					selectedAccount={account}
				/>
			),
			nextStep: StepType.LICENSES,
			stepTitle: 'Account',
			title: 'Account Selection',
		},
		[StepType.LICENSES]: {
			backStep: StepType.ACCOUNT,
			component: (
				<LicenseSelector
					cartUtil={cartUtil}
					formUtils={{
						setValue,
						watch,
					}}
					onSelectLicense={(sku?: SKU) =>
						setValue('selectedSKU', sku)
					}
					selectedProduct={product}
					setLicenseSelected={setLincenseSelected}
					sku={sku}
				/>
			),
			nextStep: StepType.PAYMENT,
			stepTitle: 'Licenses',
			title: 'License Selection',
		},
		[StepType.PAYMENT]: {
			backStep: StepType.LICENSES,
			component: (
				<SelectPaymentMethod
					addresses={addresses}
					billingAddress={billingAddress}
					email={email}
					enableTrialMethod={enableTrialMethod}
					form={{
						setValue,
						watch,
					}}
					purchaseOrderNumber={purchaseOrderNumber}
					selectedPaymentMethod={selectedPaymentMethod}
					setBillingAddress={setBillingAddress}
					setEmail={setEmail}
					setEnablePurchaseButton={setEnablePurchaseButton}
					setPurchaseOrderNumber={setPurchaseOrderNumber}
					step={step}
				/>
			),
			nextStep: StepType.PAYMENT,
			stepTitle: 'Payment Method',
			title: 'Payment Method',
		},
	};

	return (
		<>
			<ProductCard
				cartUtil={cartUtil}
				product={product}
				selectedAccount={account}
				step={step}
			/>

			<div className="border d-flex flex-column mt-7 p-5 rounded">
				<div className="d-flex flex-column">
					{!isFreeApp && product && (
						<div className="d-flex justify-content-center mb-6">
							<StepWizard
								className="col-8"
								currentStep={step}
								stepsInformation={StepsInformation}
								wizardSteps={{
									[StepType.ACCOUNT]:
										step !== StepType.ACCOUNT && !!account,
									[StepType.LICENSES]:
										step !== StepType.LICENSES &&
										licenseSelected,
									[StepType.PAYMENT]: false,
								}}
							/>
						</div>
					)}

					<div className="align-self-center h1 mb-6">
						{StepsInformation[step].title}
					</div>

					<div>{StepsInformation[step].component}</div>
				</div>

				<ProductFooter
					addresses={addresses}
					cartUtil={cartUtil}
					enablePurchaseButton={enablePurchaseButton}
					handleGetApp={handleGetApp}
					isFreeApp={isFreeApp}
					licenseSelected={licenseSelected}
					selectedAccount={account}
					selectedPaymentMethod={selectedPaymentMethod}
					selectedSKU={selectedSKU}
					setStep={setStep}
					step={step}
					stepsNavigation={StepsInformation}
				/>
			</div>
		</>
	);
};

export default GetAppFlow;
