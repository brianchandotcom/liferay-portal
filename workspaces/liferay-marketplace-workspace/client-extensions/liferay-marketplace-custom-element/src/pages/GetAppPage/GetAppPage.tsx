/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';

import useCart from '../../hooks/useCart';
import {
	getPaymentMethodURL,
	postCheckoutCart,
	postEmailAppInformation,
} from '../../utils/api';
import {getUrlParam} from '../../utils/getUrlParam';
import AccountSelection from './components/AccountSelection';
import ProductFooter from './components/Footer';
import {LicenseSelector} from './components/LicenseSelector';
import ProductCard from './components/ProductCard';
import {SelectPaymentMethod} from './components/SelectPaymentMethod/SelectPaymentMethod';
import {initialBillingAddress} from './constants/initialBillingAddress';
import {paymentMethod} from './enums/paymentMethod';
import {StepType} from './enums/stepType';
import useGetAddresses from './hooks/useGetAddresses';
import useGetChannelInfo from './hooks/useGetChannelInfo';
import useGetProductSkus from './hooks/useGetProductSkus';
import useProductPriceModel from './hooks/useProductPriceModel';
import buildNewCart from './utils/buildNewCart';
import {getProductOrderTypes} from './utils/getProductOrderTypes';
import {getProductSpecificationValues} from './utils/getProductSpecificationValues';
import getReplaceCurrentURL from './utils/getReplaceCurrentURL';
import {postCartByPaymentMethod} from './utils/postCartByPaymentMethod';

export type GetAppForm = {
	product?: Product;
	selectedAccount?: Account;
	selectedSKU?: SKU;
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
	const [selectedPaymentMethod, setSelectedPaymentMethod] = useState<
		PaymentMethodSelector
	>(paymentMethod.PAY);
	const [step, setStep] = useState<StepType>(StepType.ACCOUNT);

	const {getValues, setValue, watch} = useForm<GetAppForm>({
		defaultValues: {
			product: undefined,
			selectedAccount: undefined,
			selectedSKU: undefined,
		},
	});

	const urlProductId = getUrlParam('productId');

	const {product, selectedAccount, selectedSKU} = getValues();

	const productId = product?.productId || urlProductId;
	const productName = product?.name.en_US;

	const {sku} = useGetProductSkus(setEnableTrialMethod, product);
	const {channel} = useGetChannelInfo();
	const {addresses} = useGetAddresses(selectedAccount?.id);
	const {isFreeApp, priceModel} = useProductPriceModel(product);

	const cartUtil = useCart({
		accountId: selectedAccount?.id!,
		channelId: channel?.id,
		orderType,
	});

	useEffect(() => {
		if (cartUtil?.cartItems?.length) {
			setLincenseSelected(true);
			setEnablePurchaseButton(true);
		}
	}, [cartUtil?.cartItems?.length]);

	useEffect(() => {
		(async () => {
			const productSpecificationValues = await getProductSpecificationValues(
				Number(productId)
			);

			const orderType = await getProductOrderTypes(
				productSpecificationValues
			);

			setOrderType(orderType);
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
			selectedAccount,
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
			productName,
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
						setValue('selectedAccount', account);
					}}
					selectedAccount={getValues('selectedAccount')}
				/>
			),
			nextStep: StepType.LICENSES,
			title: 'Account Selection',
		},
		[StepType.LICENSES]: {
			backStep: StepType.ACCOUNT,
			component: (
				<LicenseSelector
					cart={cartUtil}
					form={{
						getValues,
						setValue,
					}}
					onSelectLicense={(sku?: SKU) =>
						setValue('selectedSKU', sku)
					}
					selectedPaymentMethod={setSelectedPaymentMethod}
					selectedProduct={watch('product')}
					setLicenseSelected={setLincenseSelected}
					sku={sku}
				/>
			),
			nextStep: StepType.PAYMENT,
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
					purchaseOrderNumber={purchaseOrderNumber}
					selectedPaymentMethod={selectedPaymentMethod}
					setBillingAddress={setBillingAddress}
					setEmail={setEmail}
					setEnablePurchaseButton={setEnablePurchaseButton}
					setPurchaseOrderNumber={setPurchaseOrderNumber}
					setSelectedPaymentMethod={setSelectedPaymentMethod}
					step={step}
				/>
			),
			nextStep: StepType.PAYMENT,
			title: 'Payment Method',
		},
	};

	return (
		<>
			<ProductCard
				cartUtil={cartUtil}
				productId={Number(getUrlParam('productId'))}
				selectedAccount={watch('selectedAccount')}
				setProductToForm={(product: Product) =>
					setValue('product', product)
				}
				step={step}
			/>

			<div className="border d-flex flex-column mt-7 p-5 rounded">
				<div className="d-flex flex-column">
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
					sectionProperties={StepsInformation}
					selectedAccount={selectedAccount}
					selectedPaymentMethod={selectedPaymentMethod}
					selectedSKU={selectedSKU}
					setStep={setStep}
					step={step}
				/>
			</div>
		</>
	);
};

export default GetAppFlow;
