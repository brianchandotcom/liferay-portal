/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClaySticker from '@clayui/sticker';
import {useCallback, useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';

import {useMarketplaceContext} from '../../context/MarketplaceContext';
import emptyPictureIcon from '../../assets/icons/avatar.svg';
import useCart from '../../hooks/useCart';
import {
	getPaymentMethodURL,
	postCheckoutCart,
	postEmailAppInformation,
} from '../../utils/api';
import {getValueFromSpecifications} from '../../utils/util';
import AccountSelection from './components/AccountSelection';
import ProductFooter from './components/Footer';
import {LicenseSelector} from './components/LicenseSelector';
import ProductCard from './components/ProductCard/ProductCard';
import {SelectPaymentMethod} from './components/SelectPaymentMethod/SelectPaymentMethod';
import StepWizard from './components/StepWizard/StepWizard';
import {initialBillingAddress} from './constants/initialBillingAddress';
import {LicenseType} from './enums/licenseType';
import {paymentMethod} from './enums/paymentMethod';
import {SkuOptions} from './enums/skuOptions';
import {StepType} from './enums/stepType';
import useGetAddresses from './hooks/useGetAddresses';
import useGetProduct from './hooks/useGetProduct';
import useGetProductCreatorAccount from './hooks/useGetProductCreatorAccount';
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
	userAccount?: UserAccount;
};

const GetAppFlow = () => {
	const {channel, myUserAccount} = useMarketplaceContext();
	const [billingAddress, setBillingAddress] = useState<BillingAddress>(
		initialBillingAddress
	);
	const [email, setEmail] = useState<string>('');
	const [enablePurchaseButton, setEnablePurchaseButton] =
		useState<boolean>(false);
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
	const {addresses} = useGetAddresses(account?.id);
	const {isFreeApp, priceModel} = useProductPriceModel(product);
	const cartUtil = useCart({
		accountId: account?.id!,
		channelId: channel?.id,
		orderType,
	});
	const productCreatorAccount = useGetProductCreatorAccount(product);

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
				const productSpecificationValues =
					await getProductSpecificationValues(Number(productId));

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
					userAccount={myUserAccount}
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

	const [hasTrial, setHasTrial] = useState(false);
	const [basePrice, setBasePrice] = useState(0);

	const productHasTrialSKU = (skus: SKU[]) => {
		skus.forEach(async (sku) => {
			const licenseUsageType = sku?.skuOptions.find((option) => {
				return (
					option?.key === 'trial' &&
					option?.value === 'yes' &&
					option?.key
				);
			});
			if (
				licenseUsageType &&
				licenseUsageType?.key.toLowerCase() ===
					SkuOptions.TRIAL.toLowerCase()
			) {
				setHasTrial(true);
			}
		});
	};

	const getProductBasePrice = (product: Product) => {
		product?.skus?.forEach((sku) => {
			const licenseUsageType = sku?.skuOptions.find(
				(skuOption) =>
					skuOption?.key === 'standard' &&
					skuOption?.value === 'yes' &&
					skuOption?.key
			);

			if (
				licenseUsageType?.key.toLowerCase() ===
				SkuOptions.STANDARD.toLowerCase()
			) {
				setBasePrice(sku.price);
			}
		});
	};

	const getLicenseTagText = (product: Product) => {
		const licenseTypeSpecification = getValueFromSpecifications(
			product.productSpecifications,
			'license-type'
		).toLowerCase();

		if (licenseTypeSpecification) {
			return licenseTypeSpecification === LicenseType.Perpetual
				? 'One-Time'
				: 'Annually';
		}
	};

	useEffect(() => {
		if (product) {
			productHasTrialSKU(product.skus);
			getProductBasePrice(product);
		}
	}, [product]);

	if (!product) {
		return null;
	}

	const FormattedValues = () => {
		if (step === StepType.LICENSES || step === StepType.PAYMENT) {
			return (
				<span className="price-text-value">
					{cartUtil?.cart?.id
						? `${cartUtil?.cart?.summary?.totalFormatted}`
						: `$0`}
				</span>
			);
		}

		if (basePrice) {
			if (hasTrial) {
				return <span>30-day trial or ${basePrice}</span>;
			}

			return <span>${basePrice}</span>;
		}

		return <span className="price-text-value">Free</span>;
	};

	const PriceTypeInfo = () => (
		<div className="align-items-end d-flex flex-column price-text">
			<strong className="mr-1">Price</strong>

			<div className="mr-1 py-2">
				<FormattedValues />
			</div>

			<div className="license-tag px-2">{getLicenseTagText(product)}</div>
		</div>
	);

	const AccountEmailInfo = () => (
		<div className="align-items-center d-flex">
			<div className="account-banner-name-text align-items-end d-flex flex-column m-2">
				<strong>{productCreatorAccount?.name}</strong>

				<div className="account-banner-email-text">
					{myUserAccount?.emailAddress}
				</div>
			</div>

			<ClaySticker shape="circle" size="sm">
				<ClaySticker.Image
					alt="placeholder"
					height="24"
					src={productCreatorAccount?.logoURL ?? emptyPictureIcon}
					width="24"
				/>
			</ClaySticker>
		</div>
	);

	const ExtendBanner = () => (
		<div className="d-flex flex-row justify-content-between">
			<strong className="account-banner-title-text align-self-center">
				Account Selected
			</strong>

			<AccountEmailInfo />
		</div>
	);

	return (
		<>
			<ProductCard
				ExtendBanner={ExtendBanner}
				RightSideBanner={PriceTypeInfo}
				cartUtil={cartUtil}
				creatorAccount={productCreatorAccount}
				product={product}
				selectedAccount={account}
				step={step}
				userAccount={myUserAccount}
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
