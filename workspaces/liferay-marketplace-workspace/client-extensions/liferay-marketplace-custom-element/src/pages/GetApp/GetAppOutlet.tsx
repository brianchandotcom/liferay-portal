/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {Outlet, useLocation, useNavigate} from 'react-router-dom';

import {useMarketplaceContext} from '../../context/MarketplaceContext';
import useCart from '../../hooks/useCart';
import useGetAddresses from '../../hooks/useGetAddresses';
import {
	getPaymentMethodURL,
	postCheckoutCart,
	postEmailAppInformation,
} from '../../utils/api';
import {useGetAppContext} from './GetAppContextProvider';
import ProductFooter from './containers/Footer';
import ProductHeader from './containers/ProductHeader';
import ProductStepWizard from './containers/ProductStepWizard';
import {PaymentMethod} from './enums/paymentMethod';
import {SkuOptions} from './enums/skuOptions';
import buildNewCart from './utils/buildNewCart';
import {getProductOrderTypes} from './utils/getProductOrderTypes';
import getProductPriceModel from './utils/getProductPriceModel';
import {getProductSpecificationValues} from './utils/getProductSpecificationValues';
import getReplaceCurrentURL from './utils/getReplaceCurrentURL';
import {postCartByPaymentMethod} from './utils/postCartByPaymentMethod';

import './styles/index.scss';

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

const GetAppOutlet = () => {
	const [
		{
			account,
			license: {selectedSKU},
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
	const navigate = useNavigate();

	const productBasePriceAndTrial = getProductBasePriceAndTrial(
		(product as unknown) as DeliveryProduct
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

	async function handleGetApp(orderId?: number) {
		setLoading(true);

		const productSpecificationValues = getProductSpecificationValues(
			product?.productSpecifications || []
		);

		const orderType = getProductOrderTypes(productSpecificationValues);

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
				selectedPaymentMethod: paymentMethod,
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

			if (paymentMethod === PaymentMethod.PAY) {
				const paymentMethodURL = await getPaymentMethodURL(
					cartResponse.id,
					nextStepsCallbackURL
				);

				window.location.href = paymentMethodURL;

				return;
			}

			window.location.href = nextStepsCallbackURL;
		} catch (error) {
			console.error('Unable to handleGetApp', error);
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

			<main>
				<div className="border d-flex flex-column mt-7 p-5 rounded">
					<div className="d-flex flex-column">
						<ProductStepWizard />

						<Outlet
							context={{
								addresses,
								cartUtil,
								productBasePriceAndTrial,
							}}
						/>
					</div>
				</div>
			</main>

			<ProductFooter
				cartUtil={cartUtil}
				disabled={loading}
				handleGetApp={handleGetApp}
				isFreeApp={isFreeApp}
				selectedPaymentMethod={paymentMethod}
			/>
		</div>
	);
};

export type GetAppOutletContext = {
	addresses: BillingAddress[];
	cartUtil: ReturnType<typeof useCart>;
	productBasePriceAndTrial: ReturnType<typeof getProductBasePriceAndTrial>;
};

export {getProductBasePriceAndTrial};

export default GetAppOutlet;
