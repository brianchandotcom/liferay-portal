/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useSelector} from '@xstate/store/react';
import {Navigate} from 'react-router-dom';

import ProductPurchase from '../../../../components/ProductPurchase';
import {Section} from '../../../../components/Section/Section';
import i18n from '../../../../i18n';
import {Liferay} from '../../../../liferay/liferay';
import zodSchema, {z} from '../../../../schema/zod';
import {formatCurrency} from '../../../../utils/currencies';
import {useProductPurchaseOutletContext} from '../../ProductPurchaseOutlet';
import ProductPurchaseLDP from '../../services/ProductPurchaseLDP';
import {productPurchaseStore} from '../../store';
import LicenseTermsCheckbox from '../App/License/LicenseTermsCheckbox';

const LDPOrderSummary = () => {
	const {
		actions: {previousStep},
		form,
		handlePurchase,
		product,
		productPurchaseCart,
		selectedAccount,
	} = useProductPurchaseOutletContext();

	const summary = productPurchaseCart.cart.summary;
	const currencyCode = Liferay.CommerceContext.currency.currencyCode;

	const {payment: paymentStore, salesforceProject} = useSelector(
		productPurchaseStore,
		(state) => state.context
	);

	const valueFallBack = (value: string) => {
		if (!value) {
			return formatCurrency(0, currencyCode);
		}

		return value;
	};

	const onSubmit = async (
		form: z.infer<typeof zodSchema.ldpProvisioning>
	) => {
		const productPurchase = new ProductPurchaseLDP(
			selectedAccount,
			product
		);

		productPurchase.setForm({
			...form,
			salesforceProjectId:
				salesforceProject?.externalReferenceCode as string,
		});

		await handlePurchase(productPurchase, {
			...productPurchaseCart.cart,
			billingAddress: paymentStore.billingAddress,
			shippingAddress: paymentStore.billingAddress,
		});
	};

	if (!salesforceProject) {
		return <Navigate to="/" />;
	}

	return (
		<ProductPurchase.Shell
			className="product-purchase-summary select-payment-step"
			footerProps={{
				backButtonProps: {
					onClick: previousStep,
				},
				continueButtonProps: {
					children: i18n.translate('get-started'),
					disabled: !paymentStore.eulaAgreement,
					onClick: () =>
						onSubmit(
							form as z.infer<typeof zodSchema.ldpProvisioning>
						),
				},
			}}
			subtitle={
				<small className="text-black-50">
					{i18n.translate(
						'please-review-the-order-summary-below-and-flag-the-checkbox-to-complete-your-purchase'
					)}
				</small>
			}
			title={i18n.translate('summary')}
		>
			<Section label="Order Summary">
				<div className="d-flex mx-5">
					<div className="col-1 d-flex justify-content-end m-0 p-0 text-nowrap">
						{i18n.translate('net-price')}:
					</div>
					<span className="font-weight-bold ml-2">
						{valueFallBack(summary?.subtotalFormatted)}
					</span>
				</div>

				<div className="d-flex mx-5">
					<div className="col-1 d-flex justify-content-end m-0 p-0">
						{i18n.translate('vat')}:
					</div>
					<span className="font-weight-bold ml-2">
						{valueFallBack(summary?.taxValueFormatted)}
					</span>
				</div>

				<div className="d-flex mx-5">
					<div className="col-1 d-flex justify-content-end m-0 p-0">
						{i18n.translate('total')}:
					</div>
					<span className="font-weight-bold ml-2">
						{valueFallBack(summary?.totalFormatted)}
					</span>
				</div>
			</Section>

			<LicenseTermsCheckbox />
		</ProductPurchase.Shell>
	);
};

export default LDPOrderSummary;
