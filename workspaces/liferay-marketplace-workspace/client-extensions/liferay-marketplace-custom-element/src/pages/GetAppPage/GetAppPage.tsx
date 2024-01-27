/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useMemo, useState} from 'react';
import {useForm} from 'react-hook-form';

import {useMarketplaceContext} from '../../context/MarketplaceContext';
import {useDeliveryProduct} from '../../hooks/data/useProduct';
import useCart from '../../hooks/useCart';
import {
	getPaymentMethodURL,
	postCheckoutCart,
	postEmailAppInformation,
} from '../../utils/api';
import {getUrlParam} from '../../utils/getUrlParam';
import {getValueFromDeliverySpecifications} from '../../utils/util';
import AccountEmailInfo from '../CreateLicense/AccountInfo';
import AccountSelection from './components/AccountSelection';
import ProductFooter from './components/Footer';
import {LicenseSelector} from './components/LicenseSelector';
import ProductCard from './components/ProductCard/ProductCard';
import {SelectPaymentMethod} from './components/SelectPaymentMethod/SelectPaymentMethod';
import ProjectSelection from './components/SelectProject';
import StepWizard from './components/StepWizard/StepWizard';
import {initialBillingAddress} from './constants/initialBillingAddress';
import {LicenseType} from './enums/licenseType';
import {PaymentMethod} from './enums/paymentMethod';
import {SkuOptions} from './enums/skuOptions';
import {StepType} from './enums/stepType';
import useGetAddresses from './hooks/useGetAddresses';
import useGetResourceInfo from './hooks/useGetResourceInfo';
import buildNewCart from './utils/buildNewCart';
import {getProductOrderTypes} from './utils/getProductOrderTypes';
import getProductPriceModel from './utils/getProductPriceModel';
import {getProductSpecificationValues} from './utils/getProductSpecificationValues';
import getReplaceCurrentURL from './utils/getReplaceCurrentURL';
import {postCartByPaymentMethod} from './utils/postCartByPaymentMethod';

export type GetAppForm = {
	account?: Account;
	product?: DeliveryProduct;
	project: string;
	selectedPaymentMethod: PaymentMethod;
	selectedSKU?: DeliverySKU;
	selectedTimeline?: string;
	userAccount?: UserAccount;
};

const getProductBasePriceAndTrial = (product: DeliveryProduct) => {
	const baseValue = {
		basePrice: 0,
		firstSku: undefined,
		isTrial: false,
		trialSku: undefined,
	};

	if (!product) {
		return baseValue;
	}

	const {isFreeApp} = getProductPriceModel(product);
	const skus = (product.skus as unknown) as DeliverySKU[];

	if (isFreeApp) {
		return {
			...baseValue,
			firstSku: skus.find((sku) => sku.price.price === 0) ?? skus[0],
		};
	}

	const skusLicenseUsageTypes = skus
		.map(({skuOptions, ...sku}) => ({
			...sku,
			skuOptions: skuOptions.find((skuOption) =>
				[SkuOptions.STANDARD, SkuOptions.TRIAL].includes(
					skuOption.skuOptionValueKey as SkuOptions
				)
			),
		}))
		.filter(({skuOptions}) => skuOptions);

	const standardSku = skusLicenseUsageTypes.find(
		({skuOptions}) => skuOptions?.skuOptionValueKey === SkuOptions.STANDARD
	);

	const trialSku = skusLicenseUsageTypes.find(
		({skuOptions}) => skuOptions?.skuOptionValueKey === SkuOptions.TRIAL
	);

	return {
		basePrice: standardSku?.price?.price,
		firstSku: skus[0],
		standardSku,
		trialSku,
	};
};

const GetAppPage = () => {
	const [loading, setLoading] = useState(false);
	const {channel, myUserAccount} = useMarketplaceContext();
	const [billingAddress, setBillingAddress] = useState<BillingAddress>(
		initialBillingAddress
	);
	const [email, setEmail] = useState<string>('');
	const [enablePurchaseButton, setEnablePurchaseButton] = useState<boolean>(
		false
	);

	const [licenseSelected, setLicenseSelected] = useState<boolean>(false);
	const [purchaseOrderNumber, setPurchaseOrderNumber] = useState<string>('');
	const [step, setStep] = useState<StepType>(StepType.ACCOUNT);

	const {setValue, watch} = useForm<GetAppForm>({
		defaultValues: {
			account: undefined,
			project: undefined,
			selectedPaymentMethod: PaymentMethod.PAY,
			selectedSKU: undefined,
			selectedTimeline: '',
		},
	});

	const {account, project, selectedPaymentMethod, selectedSKU} = watch();

	const {data: product} = useDeliveryProduct(getUrlParam('productId') ?? '');

	const {hasResources, resourceRequest} = useGetResourceInfo({
		product,
		selectedProject: project,
	});

	const {basePrice, firstSku, trialSku} = getProductBasePriceAndTrial(
		(product as unknown) as DeliveryProduct
	);
	const hasTrial = !!trialSku;
	const sku = trialSku ?? firstSku;
	const {addresses} = useGetAddresses(account?.id);

	const {isFreeApp, priceModel} = getProductPriceModel(product);

	const productSpecificationValues = getProductSpecificationValues(
		product?.productSpecifications || []
	);

	const orderType = getProductOrderTypes(productSpecificationValues);
	const productCreatorAccountName = product?.catalogName || '';

	const cartUtil = useCart({
		accountId: account?.id!,
		channelId: channel?.id,
		orderType,
		product: product as DeliveryProduct,
		setValue,
	});

	useEffect(() => {
		setLicenseSelected(!!cartUtil?.cartItems?.length);
		setEnablePurchaseButton(!!cartUtil?.cartItems?.length);
	}, [cartUtil?.cartItems?.length]);

	async function handleGetApp(orderId?: number) {
		setLoading(true);

		try {
			const productSpecificationValues = getProductSpecificationValues(
				product?.productSpecifications || []
			);

			const productType = productSpecificationValues;

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
				project,
				purchaseOrderNumber,
				selectedAccount: account,
				selectedPaymentMethod,
				selectedSKU,
				sku: sku as any,
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
				productName: product?.name,
				productType,
			});

			const nextStepsCallbackURL = getReplaceCurrentURL(
				'get-app',
				'next-steps',
				`${encodeURIComponent(cartResponse.id)}`
			);

			if (selectedPaymentMethod === PaymentMethod.PAY) {
				const paymentMethodURL = await getPaymentMethodURL(
					cartResponse.id,
					nextStepsCallbackURL
				);

				window.location.href = paymentMethodURL;

				return;
			}

			window.location.href = nextStepsCallbackURL;
		}
		catch (error) {
			console.error('Unable to handleGetApp', error);
		}

		setLoading(false);
	}
	const StepsInformation = useMemo(
		() => ({
			[StepType.ACCOUNT]: {
				backStep: StepType.ACCOUNT,
				component: (
					<AccountSelection
						isFreeApp={isFreeApp}
						onSelectAccount={(account: Account) => {
							setValue('account', account);
						}}
						selectedAccount={account}
						userAccount={myUserAccount}
					/>
				),
				nextStep:
					productSpecificationValues === 'cloud'
						? StepType.PROJECT
						: StepType.LICENSES,
				stepTitle: 'Account',
				title: 'Account Selection',
			},
			...(productSpecificationValues === 'cloud' && {
				[StepType.PROJECT]: {
					backStep: StepType.ACCOUNT,
					component: (
						<ProjectSelection
							onSelectProject={(project) =>
								setValue(
									'project',
									(project as unknown) as string
								)
							}
							resourceRequest={resourceRequest}
							selectedAccount={account as Account}
							selectedProject={project}
							userAccount={myUserAccount}
						/>
					),
					nextStep: StepType.LICENSES,
					stepTitle: 'Project',
					title: 'Project Selection',
				},
			}),
			[StepType.LICENSES]: {
				backStep:
					productSpecificationValues === 'cloud'
						? StepType.PROJECT
						: StepType.ACCOUNT,
				component: (
					<LicenseSelector
						cartUtil={cartUtil}
						formUtils={{
							setValue,
							watch,
						}}
						onSelectLicense={(sku?: DeliverySKU) =>
							setValue('selectedSKU', sku)
						}
						selectedProduct={product}
						setLicenseSelected={setLicenseSelected}
						sku={sku as DeliverySKU}
						trialSKU={trialSku}
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
						enableTrialMethod={hasTrial}
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
		}),
		[
			account,
			addresses,
			billingAddress,
			cartUtil,
			email,
			hasTrial,
			isFreeApp,
			myUserAccount,
			product,
			productSpecificationValues,
			project,
			purchaseOrderNumber,
			resourceRequest,
			selectedPaymentMethod,
			setValue,
			sku,
			step,
			trialSku,
			watch,
		]
	);

	const FormattedValues = () => {
		if (step === StepType.LICENSES || step === StepType.PAYMENT) {
			return (
				<span className="price-text-value">
					{cartUtil?.cart?.id && watch('selectedTimeline') !== 'trial'
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

	const getLicenseTagText = (product: DeliveryProduct) => {
		const licenseTypeSpecification = getValueFromDeliverySpecifications(
			product.productSpecifications,
			'license-type'
		).toLowerCase();

		if (licenseTypeSpecification) {
			return licenseTypeSpecification === LicenseType.Perpetual
				? 'One-Time'
				: 'Annually';
		}
	};

	if (!product) {
		return null;
	}

	const PriceTypeInfo = () => (
		<div className="align-items-end d-flex flex-column price-text">
			<strong className="mr-1">Price</strong>

			<div className="mr-1 py-2">
				<FormattedValues />
			</div>

			{!!basePrice && (
				<div className="license-tag px-2">
					{getLicenseTagText(product)}
				</div>
			)}
		</div>
	);

	const ExtendBanner = () => (
		<div className="d-flex flex-row justify-content-between">
			<strong className="account-banner-title-text align-self-center">
				Account Selected
			</strong>

			<AccountEmailInfo userAccount={myUserAccount} />
		</div>
	);

	const footerDisabled =
		loading ||
		(step === StepType.PROJECT &&
			productSpecificationValues === 'cloud' &&
			!project);

	return (
		<>
			<ProductCard
				ExtendBanner={ExtendBanner}
				RightSideBanner={PriceTypeInfo}
				creatorAccountName={productCreatorAccountName}
				product={product}
				showExtendBanner={!!account}
			/>

			<div className="border d-flex flex-column mt-7 p-5 rounded">
				<div className="d-flex flex-column">
					{!isFreeApp && product && (
						<div className="d-flex justify-content-center mb-6">
							<StepWizard
								className="col-9"
								currentStep={step}
								stepsInformation={StepsInformation}
								wizardSteps={{
									[StepType.ACCOUNT]:
										step !== StepType.ACCOUNT && !!account,
									[StepType.PROJECT]:
										step !== StepType.PROJECT && !!project,
									[StepType.LICENSES]:
										step !== StepType.LICENSES &&
										licenseSelected &&
										!!project,
									[StepType.PAYMENT]: false,
								}}
							/>
						</div>
					)}

					<div className="align-self-center h1 mb-6">
						{StepsInformation[step]?.title}
					</div>

					<div>{StepsInformation[step]?.component}</div>
				</div>

				<ProductFooter
					addresses={addresses}
					cartUtil={cartUtil}
					disabled={footerDisabled}
					enablePurchaseButton={enablePurchaseButton}
					handleGetApp={handleGetApp}
					hasResource={hasResources as boolean}
					isFreeApp={isFreeApp}
					licenseSelected={licenseSelected}
					productSpecificationValues={productSpecificationValues}
					selectedAccount={account}
					selectedPaymentMethod={selectedPaymentMethod}
					selectedProject={project}
					selectedSKU={selectedSKU}
					setStep={setStep}
					step={step}
					stepsNavigation={StepsInformation}
				/>
			</div>
		</>
	);
};

export default GetAppPage;
