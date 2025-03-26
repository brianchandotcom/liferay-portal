/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {Outlet, useLocation, useNavigate} from 'react-router-dom';

import {useMarketplaceContext} from '../../context/MarketplaceContext';
import {Analytics} from '../../core/Analytics';
import useCart from '../../hooks/useCart';
import useCommerceRegions from '../../hooks/useCommerceRegions';
import useGetAddresses from '../../hooks/useGetAddresses';
import i18n from '../../i18n';
import {Liferay} from '../../liferay/liferay';
import CommerceSelectAccountImpl from '../../services/rest/CommerceSelectAccount';
import HeadlessAdminUserImpl from '../../services/rest/HeadlessAdminUser';
import {Region} from '../../services/rest/HeadlessCommerceAdminAddress';
import {
	getPaymentMethodURL,
	postCheckoutCart,
	postEmailAppInformation,
} from '../../utils/api';
import {useGetAppContext} from './GetAppContextProvider';
import ProductHeader from './containers/ProductHeader';
import ProductStepWizard from './containers/ProductStepWizard';
import {PaymentMethod} from './enums/paymentMethod';
import buildNewCart from './utils/buildNewCart';
import {getProductOrderTypes} from './utils/getProductOrderTypes';
import getProductPriceModel from './utils/getProductPriceModel';
import {getProductSpecificationValues} from './utils/getProductSpecificationValues';
import getReplaceCurrentURL from './utils/getReplaceCurrentURL';
import {postCartByPaymentMethod} from './utils/postCartByPaymentMethod';

import './styles/index.scss';
import {SkuOptions} from '../../enums/Product';

const getProductBasePriceAndTrial = (
	product: DeliveryProduct,
	isCloudApp: boolean
) => {
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

	const skus = (product.skus as unknown as DeliverySKU[])?.filter(
		({purchasable}) => purchasable
	);

	if (isFreeApp) {
		return {
			...baseValue,
			firstSku: skus.find((sku) => sku.price.price === 0) ?? skus[0],
		};
	}

	let standardSku;
	let trialSku;

	if (isCloudApp) {
		trialSku = skus.find(({skuOptions}) =>
			skuOptions.find(
				(skuOption) =>
					skuOption.skuOptionKey === SkuOptions.TRIAL &&
					skuOption.skuOptionValueKey === 'yes'
			)
		);

		standardSku = skus.find(({skuOptions}) =>
			skuOptions.find(
				(skuOption) =>
					skuOption.skuOptionKey === SkuOptions.TRIAL &&
					skuOption.skuOptionValueKey === 'no'
			)
		);
	}
	else {
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

		standardSku = skusLicenseUsageTypes.find(
			({skuOptions}) =>
				skuOptions?.skuOptionValueKey === SkuOptions.STANDARD
		);

		trialSku = skusLicenseUsageTypes.find(
			({skuOptions}) => skuOptions?.skuOptionValueKey === SkuOptions.TRIAL
		);
	}

	return {
		basePrice: standardSku?.price?.price,
		firstSku: skus[0],
		standardSku,
		trialSku,
	};
};

const GetAppOutlet = () => {
	const [
		{
			account,
			isCloudApp,
			license: {selectedSKU, type},
			payment: {
				billingAddress,
				invoice: {email, purchaseOrderNumber},
				method: paymentMethod,
			},
			product,
			project = '',
		},
	] = useGetAppContext();

	const [loading, setLoading] = useState(false);
	const {addresses} = useGetAddresses(account?.id);
	const {channel} = useMarketplaceContext();
	const location = useLocation();

	const {data: regionsResponse} = useCommerceRegions();
	const regions = regionsResponse?.items ?? [];

	const navigate = useNavigate();

	const productBasePriceAndTrial = getProductBasePriceAndTrial(
		product as unknown as DeliveryProduct,
		isCloudApp
	);

	const {firstSku, trialSku} = productBasePriceAndTrial;

	const sku = trialSku ?? firstSku;

	const productSpecificationValues = getProductSpecificationValues(
		product?.productSpecifications || []
	);

	const cartUtil = useCart({
		accountId: account?.id as number,
		orderType: getProductOrderTypes(productSpecificationValues),
		product: product as DeliveryProduct,
	});

	const {isFreeApp, priceModel} = getProductPriceModel(product);

	const getCountryNameByCode = (regions: Region[], countryCode?: string) => {
		const country = regions.find((region) => region.a2 === countryCode);

		return (
			country?.title_i18n[Liferay.ThemeDisplay.getLanguageId()] ||
			country?.title_i18n[Liferay.ThemeDisplay.getDefaultLanguageId()] ||
			country?.name
		);
	};

	const getRegionByCountryCode = (
		regions: Region[],
		regionISOCode?: string,
		countryCode?: string
	) => {
		const country = regions.find((region) => region.a2 === countryCode);
		const addressRegion = country?.regions.find(
			(region) => region.regionCode === regionISOCode
		);

		return addressRegion?.name;
	};

	async function handleGetApp(orderId = cartUtil?.cart?.id) {
		setLoading(true);

		if (billingAddress.saveAddress) {
			await HeadlessAdminUserImpl.postAddress(account?.id as number, {
				addressCountry: getCountryNameByCode(
					regions,
					billingAddress?.country
				),
				addressLocality: billingAddress.city,
				addressRegion: getRegionByCountryCode(
					regions,
					billingAddress.regionISOCode,
					billingAddress?.country
				),
				addressType: 'billing-and-shipping',
				name: billingAddress.name,
				phoneNumber: billingAddress.phoneNumber,
				postalCode: billingAddress.zip,
				primary: false,
				streetAddressLine1: billingAddress.street1,
				streetAddressLine2: billingAddress.street2,
			});
		}

		const productSpecificationValues = getProductSpecificationValues(
			product?.productSpecifications || []
		);

		const orderType = getProductOrderTypes(productSpecificationValues);

		delete billingAddress.saveAddress;

		try {
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
				selectedPaymentMethod:
					type.toLowerCase() === PaymentMethod.TRIAL && selectedSKU
						? 'trial'
						: paymentMethod,
				selectedSKU,
				sku: sku as any,
			});

			const cartResponse = orderId
				? await cartUtil.updateCart(orderId, {
						...cart,
						cartItems: cartUtil.cartItems,
					})
				: await postCartByPaymentMethod(cart, channel.id);

			await postCheckoutCart({cartId: cartResponse.id});

			Analytics.track('APP_PURCHASE', {
				isFreeApp,
				paymentMethod,
				productName: product.name,
			});

			await postEmailAppInformation({
				dashboardLink: getReplaceCurrentURL(
					'get-app',
					'customer-dashboard'
				),
				orderID: cartResponse.id,
				priceModel,
				productName: product?.name,
				productType: productSpecificationValues,
			});

			const nextStepsCallbackURL = getReplaceCurrentURL(
				'get-app',
				'next-steps',
				`${encodeURIComponent(cartResponse.id)}`
			);

			const paymentMethodURL = await getPaymentMethodURL(
				cartResponse.id,
				nextStepsCallbackURL
			);

			await CommerceSelectAccountImpl.selectAccount(
				account?.id as number
			);

			window.location.href = paymentMethodURL || nextStepsCallbackURL;
		}
		catch (error) {
			console.error('Unable to handleGetApp', error);

			Liferay.Util.openToast({
				message: i18n.translate('an-unexpected-error-occurred'),
				type: 'danger',
			});
		}

		setLoading(false);
	}

	useEffect(() => {
		if (location.pathname !== '/' && !account) {
			navigate('/');
		}
	}, [account, location.pathname, navigate]);

	if (!product) {
		return null;
	}

	return (
		<div>
			<ProductHeader
				productBasePriceAndTrial={productBasePriceAndTrial}
			/>

			<div className="border d-flex flex-column mt-7 p-5 rounded">
				<main>
					<div className="d-flex flex-column">
						{!isFreeApp && <ProductStepWizard />}

						<Outlet
							context={{
								account,
								addresses,
								cartUtil,
								handleGetApp,
								isFreeApp,
								loading,
								productBasePriceAndTrial,
								selectedPaymentMethod: paymentMethod,
							}}
						/>
					</div>
				</main>
			</div>
		</div>
	);
};

export type GetAppOutletContext = {
	addresses: BillingAddress[];
	cartUtil: ReturnType<typeof useCart>;
	handleGetApp: (orderId?: number) => void;
	isFreeApp: boolean;
	loading: boolean;
	productBasePriceAndTrial: ReturnType<typeof getProductBasePriceAndTrial>;
	selectedPaymentMethod: PaymentMethod;
};

export {getProductBasePriceAndTrial};

export default GetAppOutlet;
