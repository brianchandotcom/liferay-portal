/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, Route, Routes} from 'react-router-dom';

import {useMarketplaceContext} from '../../context/MarketplaceContext';
import {
	ProductCategories,
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
	isCloudProduct,
} from '../../utils/productUtils';
import ProductPurchaseOutlet from './ProductPurchaseOutlet';
import ProductPurchaseAccountSelection from './pages/AccountSelection';
import AppAccountSelection from './pages/App/AccountSelection';
import {InsuficientResources} from './pages/App/InsuficientResources';
import ContactSalesPage from './pages/App/InsuficientResources/ContactSales';
import ContactSalesForm from './pages/App/InsuficientResources/ContactSalesForm';
import License from './pages/App/License';
import PaymentMethod from './pages/App/PaymentMethod';
import ProjectSelection from './pages/App/Project';
import NextSteps from './pages/NextSteps';
import SolutionProvisioningForm from './pages/Solution';

export const productTypeRoutes = {
	[ProductTypeVocabulary.APP]: {
		metadata: {
			isNavigationStepVisible: (product: DeliveryProduct) =>
				getProductPriceModel(product).isPaidApp,
			useCart: true,
		},
		routes: [
			{
				element: AppAccountSelection,
				index: true,
				title: i18n.translate('account'),
			},
			{
				element: ProjectSelection,
				path: 'project',
				stepVisible: (product: DeliveryProduct) =>
					isCloudProduct(product),
				title: i18n.translate('project'),
			},
			{
				element: License,
				path: 'license',
				title: i18n.translate('licenses'),
			},
			{
				element: PaymentMethod,
				path: 'payment-method',
				title: i18n.translate('payment'),
			},
		],
	},
	[ProductTypeVocabulary.SOLUTION]: {
		metadata: {
			skipSingleAccountSelection: true,
		},
		routes: [
			{
				element: ProductPurchaseAccountSelection,
				index: true,
				title: i18n.translate('account-selection'),
			},
			{
				element: SolutionProvisioningForm,
				path: 'form',
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
		ProductCategories.MARKETPLACE_PRODUCT_TYPE
	);

	const productTypeCategory = productTypes[0] as ProductTypeVocabulary;

	const solutionTypeSpecification = getProductSpecification(
		ProductSpecificationKey.SOLUTION_TYPE,
		product as DeliveryProduct
	);

	const solutionTypeSpecificationValue =
		solutionTypeSpecification?.value as SolutionTypes;

	const productTypeRoute = productTypeRoutes[productTypeCategory];

	const {routes = []} = productTypeRoute || {};

	return (
		<HashRouter>
			<Routes>
				<Route
					element={
						<ProductPurchaseOutlet
							product={product as DeliveryProduct}
							productTypeRoute={productTypeRoute as any}
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
