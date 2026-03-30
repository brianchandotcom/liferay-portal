/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useState} from 'react';
import {z} from 'zod';

import Loading from '../../components/Loading';
import ProductPurchase from '../../components/ProductPurchase';
import ProductPurchaseFeedback from '../../components/ProductPurchase/Feedback';
import {useMarketplaceContext} from '../../context/MarketplaceContext';
import withProviders from '../../hoc/withProviders';
import useGetProductByOrderId from '../../hooks/useGetProductByOrderId';
import useProductPurchaseCart from '../../hooks/useProductPurchaseCart';
import i18n from '../../i18n';
import {Liferay} from '../../liferay/liferay';
import zodSchema from '../../schema/zod';
import HeadlessProductFeedback from '../../services/rest/HeadlessProductFeedback';
import {getSiteURL} from '../../utils/site';
import ProductPurchasePrice from '../ProductPurchase/ProductPurchasePrice';
import useAccounts from '../ProductPurchase/hooks/useAccounts';
import ProductPurchaseApp from '../ProductPurchase/services/ProductPurchaseApp';
import ProductFeedbackForm from './ProductFeedbackForm';

export function ProductFeedback() {
	const searchParams = new URLSearchParams(window.location.search);
	const orderId = searchParams.get('orderId');

	const {data, error, isLoading} = useGetProductByOrderId(orderId as string);
	const {selectedAccount} = useAccounts();
	const {properties} = useMarketplaceContext();

	const [isSubmitting, setIsSubmitting] = useState(false);
	const [isSubmitted, setIsSubmitted] = useState(false);

	const order = data?.placedOrder;
	const product = data?.product;

	const productPurchaseCart = useProductPurchaseCart(
		selectedAccount?.id,
		product,
		product
			? ProductPurchaseApp.getOrderTypeExternalReferenceCode(product)
			: ''
	);

	const onSubmit = async (
		form: z.infer<typeof zodSchema.productFeedback>
	) => {
		setIsSubmitting(true);

		const productRelationKey = properties?.featurePreview?.includes(
			'product-versioning-new-primary-key'
		)
			? 'r_productToProductFeedback_CProductId'
			: 'r_productToProductFeedback_CPDefinitionId';

		try {
			await HeadlessProductFeedback.createProductFeedback({
				...form,
				orderType: order!.orderTypeExternalReferenceCode,
				[productRelationKey]: product!.id,
				r_orderToProductFeedback_commerceOrderId: order!.id,
			});

			setIsSubmitted(true);
		}
		catch {
			Liferay.Util.openToast({
				message: i18n.translate('an-unexpected-error-occurred'),
				type: 'danger',
			});
		}
		finally {
			setIsSubmitting(false);
		}
	};

	if (!orderId || !product || !order || error || !selectedAccount) {
		return;
	}

	if (isLoading) {
		return <Loading className="my-7" />;
	}

	if (isSubmitted) {
		return (
			<ProductPurchaseFeedback
				className="my-9"
				description="Thank you for submitting your feedback."
				title="Feedback Submitted"
			>
				<div className="d-flex">
					<ClayButton
						displayType="secondary"
						onClick={() => Liferay.Util.navigate(getSiteURL())}
					>
						Return to Marketplace
					</ClayButton>

					<ClayButton
						className="ml-4"
						displayType="primary"
						onClick={() =>
							Liferay.Util.navigate(
								getSiteURL() + '/customer-dashboard/#/products'
							)
						}
					>
						Go Dashboard
					</ClayButton>
				</div>
			</ProductPurchaseFeedback>
		);
	}

	return (
		<ProductPurchase className="my-7">
			<ProductPurchase.Header
				product={data?.product}
				rightNode={
					<ProductPurchasePrice
						product={product}
						productPurchaseCart={productPurchaseCart}
					/>
				}
			/>

			<ProductFeedbackForm
				isSubmitting={isSubmitting}
				onSubmit={onSubmit}
				subtitle={i18n.translate(
					'thank-you-for-trying-the-beta-version-of-this-product-your-feedback-is-essential-to-improve-the-final-release-this-survey-takes-3–5-minutes'
				)}
				title={i18n.translate('beta-product-feedback-form')}
			/>
		</ProductPurchase>
	);
}

export default withProviders(ProductFeedback);
