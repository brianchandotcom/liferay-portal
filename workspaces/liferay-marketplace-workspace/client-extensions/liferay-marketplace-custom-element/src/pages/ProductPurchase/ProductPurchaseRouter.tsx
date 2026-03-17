/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, Route, Routes} from 'react-router-dom';

import {useMarketplaceContext} from '../../context/MarketplaceContext';
import {MarketplaceDeliveryProduct} from '../../entity/MarketplaceDeliveryProduct';
import {MarketplaceCategories} from '../../enums/Categories';
import {
	ProductSpecificationKey,
	ProductTypeVocabulary,
	SolutionTypes,
} from '../../enums/Product';
import withProviders from '../../hoc/withProviders';
import {useDeliveryProduct} from '../../hooks/data/useProduct';
import i18n from '../../i18n';
import {
	getProductCategoriesByVocabularyName,
	getProductPriceModel,
	getProductSpecification,
} from '../../utils/productUtils';
import ProductPurchaseOutlet from './ProductPurchaseOutlet';
import ProductPurchaseAccountSelection from './pages/AccountSelection';
import AppAccountSelection from './pages/App/AccountSelection';
import {InsuficientResources} from './pages/App/InsuficientResources';
import ContactSalesPage from './pages/App/InsuficientResources/ContactSales';
import ContactSalesForm from './pages/App/InsuficientResources/ContactSalesForm';
import License from './pages/App/License';
import PaymentMethod from './pages/App/PaymentMethod';
import OrderSummary from './pages/App/PaymentMethod/OrderSummary/OrderSummary';
import AIHubForm from './pages/LiferayProduct/AIHubForm/AIHubForm';
import ActivationKeyForm from './pages/LiferayProduct/ActivationKeyForm';
import LDPOrderSummary from './pages/LiferayProduct/LDPOrderSummary';
import LDPProvisioning from './pages/LiferayProduct/LDPProvisioningForm';
import ProjectSelection from './pages/LiferayProduct/Project';
import NextSteps from './pages/NextSteps';
import SolutionProvisioningForm from './pages/Solution';

export const productTypeRoutes = {
	[ProductTypeVocabulary.APP]: {
		metadata: {
			tinyStepsDisplay: true,
			useCart: true,
		},
		routes: (product: DeliveryProduct) => {
			const {isPaidApp} = getProductPriceModel(product);

			return [
				{
					element: AppAccountSelection,
					index: true,
					title: i18n.translate('account'),
				},
				{
					element: License,
					isPaidOnly: true,
					path: 'license',
					title: i18n.translate('licenses'),
				},
				{
					element: PaymentMethod,
					isPaidOnly: true,
					path: 'payment-method',
					title: i18n.translate('payment'),
				},
				{
					element: OrderSummary,
					path: 'summary',
					title: i18n.translate('summary'),
				},
			].filter((route) => {
				if (isPaidApp) {
					return true;
				}

				return !route.isPaidOnly;
			});
		},
	},
	[ProductTypeVocabulary.LIFERAY_PRODUCT]: {
		metadata: {
			showSteps: true,
			skipSingleAccountSelection: true,
			tinyStepsDisplay: true,
			useCart: true,
		},
		routes: (product: DeliveryProduct) => {
			const marketplaceDeliveryProduct = new MarketplaceDeliveryProduct(
				product
			);

			const solutionType =
				marketplaceDeliveryProduct.specificationValues.SOLUTION_TYPE;

			if (solutionType === SolutionTypes.AI_HUB) {
				return [
					{
						element: ProductPurchaseAccountSelection,
						index: true,
						title: i18n.translate('account'),
					},
					{
						element: AIHubForm,
						path: 'ai-hub-form',
						title: i18n.translate('ai-hub'),
					},
				];
			}

			if (
				[SolutionTypes.CMP, SolutionTypes.DXP].includes(
					solutionType as SolutionTypes
				)
			) {
				return [
					{
						element: ProductPurchaseAccountSelection,
						index: true,
						title: i18n.translate('account'),
					},
					{
						element: ActivationKeyForm,
						path: 'activation-key-form',
						title: i18n.translate('activation-key'),
					},
				];
			}

			if (solutionType === SolutionTypes.LIFERAY_DATA_PLATFORM) {
				return [
					{
						element: ProductPurchaseAccountSelection,
						index: true,
						title: i18n.translate('account'),
					},
					{
						element: ProjectSelection,
						path: 'project',
						title: i18n.translate('project'),
					},

					{
						element: LDPProvisioning,
						path: 'provisioning',
						title: i18n.translate('provisioning'),
					},
					{
						element: LDPOrderSummary,
						path: 'summary',
						title: i18n.translate('summary'),
					},
				];
			}

			return [];
		},
	},
	[ProductTypeVocabulary.SOLUTION]: {
		metadata: {
			showAccountSelected: false,
			showSteps: false,
			skipSingleAccountSelection: true,
			tinyStepsDisplay: false,
		},
		routes: [
			{
				element: SolutionProvisioningForm,
				index: true,
				path: '',
				title: i18n.translate('form'),
			},
		],
	},
};

const ProductPurchaseRouter = () => {
	const {
		properties: {productId: pageProductId},
	} = useMarketplaceContext();

	// The productId that comes from the property can be used to hide the productId
	// search param is some places

	const productId =
		pageProductId ||
		(new URLSearchParams(window.location.search).get(
			'productId'
		) as unknown as string);

	const {data: product, isLoading} = useDeliveryProduct(productId);

	if (isLoading) {
		return null;
	}

	const productTypes = getProductCategoriesByVocabularyName(
		product?.categories || [],
		MarketplaceCategories.MARKETPLACE_PRODUCT_TYPE
	);

	const productTypeCategory = productTypes[0] as ProductTypeVocabulary;

	const solutionTypeSpecification = getProductSpecification(
		ProductSpecificationKey.SOLUTION_TYPE,
		product as DeliveryProduct
	);

	const solutionTypeSpecificationValue =
		solutionTypeSpecification?.value as SolutionTypes;

	const productTypeRoute =
		productTypeRoutes[
			productTypeCategory as keyof typeof productTypeRoutes
		];

	const {routes: _routes = []} = productTypeRoute || {};

	const routes =
		typeof _routes === 'function'
			? _routes(product as DeliveryProduct)
			: _routes;

	return (
		<HashRouter>
			<Routes>
				<Route
					element={
						<ProductPurchaseOutlet
							product={product as DeliveryProduct}
							productTypeRoute={
								{...productTypeRoute, routes} as any
							}
							solutionTypeSpecificationValue={
								solutionTypeSpecificationValue
							}
						/>
					}
				>
					{routes.map((route, index) => {
						const Element = route.element;

						return (
							<Route
								{...route}
								element={<Element />}
								key={index}
							/>
						);
					})}
				</Route>

				<Route
					element={
						<InsuficientResources
							product={product as DeliveryProduct}
						/>
					}
					path="insuficient-resources/:projectId/:accountId"
				>
					<Route element={<ContactSalesPage />} index />
					<Route element={<ContactSalesForm />} path="form" />
				</Route>

				<Route
					element={
						<NextSteps
							product={product as DeliveryProduct}
							productTypeCategory={productTypeCategory}
							solutionTypeSpecificationValue={
								solutionTypeSpecificationValue
							}
						/>
					}
					path="next-steps"
				/>
			</Routes>
		</HashRouter>
	);
};
export default withProviders(ProductPurchaseRouter);
