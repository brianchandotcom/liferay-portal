/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {useState} from 'react';
import {
	Outlet,
	useLocation,
	useNavigate,
	useOutletContext,
} from 'react-router-dom';

import Loading from '../../components/Loading';
import ProductPurchase from '../../components/ProductPurchase';
import {useMarketplaceContext} from '../../context/MarketplaceContext';
import {SolutionTypes} from '../../enums/Product';
import useProductPurchaseCart from '../../hooks/useProductPurchaseCart';
import i18n from '../../i18n';
import {Liferay} from '../../liferay/liferay';
import {scrollToMiddleOfPage} from '../../utils/browser';
import ProductPurchasePrice from './ProductPurchasePrice';
import {productTypeRoutes} from './ProductPurchaseRouter';
import useAccounts from './hooks/useAccounts';
import ProductPurchaseService from './services/ProductPurchase';
import ProductPurchaseApp from './services/ProductPurchaseApp';

type ProductPurchaseOutletProps = {
	product: DeliveryProduct;
	productTypeRoute: (typeof productTypeRoutes)['App'] &
		(typeof productTypeRoutes)['Solution'];
	solutionTypeSpecificationValue: SolutionTypes;
};

function sendRedirect(link: string) {
	window.location.href = link;
}

export type ProductPurchaseOutletContext = {
	actions: {
		nextStep: () => void;
		previousStep: () => void;
	};
	handlePurchase: (
		ProductPurchase: ProductPurchaseService | typeof ProductPurchaseService,
		cart?: Cart | undefined,
		cartOptions?: any
	) => Promise<void>;
	product: DeliveryProduct;
	productPurchaseCart: ReturnType<typeof useProductPurchaseCart>;
	productTypeRoute: ProductPurchaseOutletProps['productTypeRoute'];
	solutionTypeSpecificationValue: SolutionTypes;
} & Omit<ReturnType<typeof useAccounts>, 'myUserAccount'>;

const ProductPurchaseOutlet: React.FC<ProductPurchaseOutletProps> = ({
	product,
	productTypeRoute,
	solutionTypeSpecificationValue,
}) => {
	const [isSubmitting, setSubmitting] = useState(false);
	const {channel} = useMarketplaceContext();
	const {accounts, selectedAccount, setSelectedAccount} = useAccounts();
	const {pathname} = useLocation();
	const navigate = useNavigate();

	const productPurchaseCart = useProductPurchaseCart(
		selectedAccount?.id,
		product,

		// Currently only the App Purchase uses the cart hook

		ProductPurchaseApp.getOrderTypeExternalReferenceCode(product)
	);

	const {metadata, routes = []} = productTypeRoute || {};

	const steps = routes
		.filter(({stepVisible}) =>
			typeof stepVisible === 'function' ? stepVisible(product) : true
		)
		.map((route) => {
			const key = route.index ? '/' : `/${route.path}`;

			return {
				...route,
				active: pathname === key,
				key,
			};
		});

	const activeStepIndex = steps.findIndex(({active}) => active);

	const stepNavigate = (stepNumber: number) => {
		const step = steps[activeStepIndex + stepNumber];

		if (step) {
			navigate(step.path || '');

			scrollToMiddleOfPage();
		}
	};

	const handlePurchase = async (
		ProductPurchase: ProductPurchaseService | typeof ProductPurchaseService,
		cart: Cart,
		cartOptions: unknown
	) => {
		setSubmitting(true);

		try {
			const _productPurchase =
				ProductPurchase instanceof ProductPurchaseService
					? ProductPurchase
					: new ProductPurchase(selectedAccount, channel, product);

			const order = await _productPurchase.createOrder(cart, cartOptions);

			const link = await _productPurchase.getNextStepsLink(order);

			if (link.startsWith('http')) {
				return sendRedirect(link);
			}

			navigate(link);
		}
		catch (error) {
			console.error(error);

			Liferay.Util.openToast({
				message: i18n.translate('an-unexpected-error-occurred'),
				type: 'danger',
			});
		}

		setSubmitting(false);
	};

	const displaySteps = metadata?.isNavigationStepVisible
		? metadata.isNavigationStepVisible(product)
		: true;

	const context = {
		accounts,
		actions: {
			nextStep: () => stepNavigate(1),
			previousStep: () => stepNavigate(-1),
		},
		handlePurchase,
		product,
		productPurchaseCart,
		routes: steps,
		selectedAccount,
		setSelectedAccount,
		solutionTypeSpecificationValue,
	};

	return (
		<ProductPurchase className="my-7">
			{isSubmitting && (
				<Loading.FullScreen>
					Hang tight, <b>{product.name}</b> purchase is processed by{' '}
					<b>Marketplace</b>.
				</Loading.FullScreen>
			)}

			<ProductPurchase.Header
				product={product}
				rightNode={
					metadata.useCart ? (
						<ProductPurchasePrice
							activeStepIndex={activeStepIndex}
							product={product}
						/>
					) : null
				}
			>
				<ProductPurchase.HeaderAccount account={selectedAccount} />
			</ProductPurchase.Header>

			{displaySteps && (
				<ProductPurchase.Steps
					className="mt-5 px-8"
					onClickIndicator={(step) => navigate(step.key)}
					steps={steps}
				/>
			)}

			<ProductPurchase.Body
				className={classNames({'mt-7': accounts.length === 1})}
			>
				<Outlet context={context} />
			</ProductPurchase.Body>
		</ProductPurchase>
	);
};

const useProductPurchaseOutletContext = () => {
	return useOutletContext<ProductPurchaseOutletContext>();
};

export {useProductPurchaseOutletContext};

export default ProductPurchaseOutlet;
