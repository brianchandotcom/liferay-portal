/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';
import {useForm} from 'react-hook-form';

import {
	getPaymentMethodURL,
	postCheckoutCart,
	postEmailAppInformation,
} from '../../utils/api';
import {getUrlParam} from '../../utils/getUrlParam';
import AccountSelection from './components/AccountSelection';
import ProductFooter from './components/Footer';
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
import getEmailInformation from './utils/getEmailInformation';
import {getProductOrderTypes} from './utils/getProductOrderTypes';
import {getProductSpecificationValues} from './utils/getProductSpecificationValues';
import getReplaceCurrentURL from './utils/getReplaceCurrentURL';
import {postCartByPaymentMethod} from './utils/postCartByPaymentMethod';

type StepComponent = {
	[key in StepType]?: JSX.Element;
};

type getAppProps = {
	product?: Product;
	selectedAccount?: Account;
};

const sectionProperties = {
	[StepType.ACCOUNT]: {
		backStep: StepType.ACCOUNT,
		nextStep: StepType.LICENSES,
		title: 'Account Selection',
	},
	[StepType.LICENSES]: {
		backStep: StepType.ACCOUNT,
		nextStep: StepType.PAYMENT,
		title: 'License Selection',
	},
	[StepType.PAYMENT]: {
		backStep: StepType.LICENSES,
		nextStep: StepType.PAYMENT,
		title: 'Payment Method',
	},
};

const GetAppFlow = () => {
	const [step, setStep] = useState<StepType>(StepType.ACCOUNT);
	const [showAccount, setShowAccount] = useState<boolean>(false);
	const [enablePurchaseButton, setEnablePurchaseButton] = useState<boolean>(
		false
	);
	const [email, setEmail] = useState<string>('');
	const [purchaseOrderNumber, setPurchaseOrderNumber] = useState<string>('');
	const [selectedPaymentMethod, setSelectedPaymentMethod] = useState<
		PaymentMethodSelector
	>(paymentMethod.PAY);
	const [enableTrialMethod, setEnableTrialMethod] = useState<boolean>(false);
	const [billingAddress, setBillingAddress] = useState<BillingAddress>(
		initialBillingAddress
	);
	const [userAccount, setUserAccount] = useState<UserAccount>();

	const {getValues, setValue, watch} = useForm<getAppProps>({
		defaultValues: {
			product: undefined,
			selectedAccount: undefined,
		},
	});

	const {product, selectedAccount} = getValues();
	const productId = product?.productId;
	const productName = product?.name.en_US;

	const {sku} = useGetProductSkus(setEnableTrialMethod, product);
	const {channel} = useGetChannelInfo();
	const {addresses} = useGetAddresses(selectedAccount?.id);
	const {isFreeApp, priceModel} = useProductPriceModel(product);

	async function handleGetApp() {
		const productSpecificationValues = await getProductSpecificationValues(
			productId
		);

		const productType = productSpecificationValues.en_US;

		const orderType = await getProductOrderTypes(
			productSpecificationValues
		);
		const cart = buildNewCart(
			billingAddress,
			channel,
			email,
			isFreeApp,
			orderType,
			purchaseOrderNumber,
			selectedPaymentMethod,
			sku,
			product,
			selectedAccount
		);

		const cartResponse = await postCartByPaymentMethod(cart, channel.id);

		await postCheckoutCart({cartId: cartResponse.id});

		const dashboardURL = getReplaceCurrentURL(
			'get-app',
			'customer-dashboard'
		);

		const emailAppInformation = getEmailInformation(
			dashboardURL,
			cartResponse.id,
			productType,
			priceModel,
			productName
		);

		await postEmailAppInformation(emailAppInformation);

		const encodedOrderId = `${encodeURIComponent(cartResponse.id)}`;

		const nextStepsCallbackURL = getReplaceCurrentURL(
			'get-app',
			'next-steps',
			encodedOrderId
		);

		if (selectedPaymentMethod === paymentMethod.PAY) {
			const paymentMethodURL = await getPaymentMethodURL(
				cartResponse.id,
				nextStepsCallbackURL
			);

			window.location.href = paymentMethodURL;
		}
		else {
			window.location.href = nextStepsCallbackURL;
		}
	}

  const StepFormComponent: StepComponent = {
    [StepType.ACCOUNT]: (
      <AccountSelection
        onSelectAccount={(account: Account) => {
          setValue("selectedAccount", account);
        }}
        selectedAccount={getValues('selected')}
      />
    ),
    [StepType.PAYMENT]: (
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
			/>
    ),
  };

  return (
    <>
     <ProductCard
				productId={Number(getUrlParam('productId'))}
				selectedAccount={watch('selectedAccount')}
				setProductToForm={(product: Product) =>
					setValue('product', product)
				}
			/>

			<div className="border d-flex flex-column mt-7 p-5 rounded">
				<div className="d-flex flex-column">
					<div className="align-self-center h1 mb-6">
						{sectionProperties[step].title}
					</div>
					<div>{StepFormComponent[step]}</div>
				</div>
				<ProductFooter
					addresses={addresses}
					enablePurchaseButton={enablePurchaseButton}
					handleGetApp={handleGetApp}
					isFreeApp={isFreeApp}
					sectionProperties={sectionProperties}
					selectedAccount={selectedAccount}
					selectedPaymentMethod={selectedPaymentMethod}
					setStep={setStep}
					sku={sku}
					step={step}
				/>
			</div>
		</>
	);
};

export default GetAppFlow;
