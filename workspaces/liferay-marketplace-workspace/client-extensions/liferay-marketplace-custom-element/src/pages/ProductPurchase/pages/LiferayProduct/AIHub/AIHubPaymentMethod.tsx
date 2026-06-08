/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useSelector} from '@xstate/store/react';
import {useEffect, useMemo, useState} from 'react';
import {useNavigate} from 'react-router-dom';

import ProductPurchase from '../../../../../components/ProductPurchase';
import i18n from '../../../../../i18n';
import zodSchema from '../../../../../schema/zod';
import HeadlessAdminUser from '../../../../../services/rest/HeadlessAdminUser';
import HeadlessCommerceDeliveryCart from '../../../../../services/rest/HeadlessCommerceDeliveryCart';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';
import ProductPurchaseApp from '../../../services/ProductPurchaseApp';
import {cartStore} from '../../../store';
import {productPurchaseStore} from '../../../store/AppPurchaseStore';
import {PaymentMethodType} from '../../../types';
import BillingAddress from '../../App/PaymentMethod/BillingAddress/BillingAddress';
import {PaymentTypeSelector} from '../../App/PaymentMethod/PaymentTypeSelector';
import TaxIdDisplay from '../../App/PaymentMethod/TaxIdDisplay';

export default function PaymentMethod() {
	const [loading, setLoading] = useState(false);
	const navigate = useNavigate();

	const payment = useSelector(
		productPurchaseStore,
		(state) => state.context.payment
	);

	const isPrimaryButtonActive = useMemo(() => {
		const isAddressValid = zodSchema.billingAddress.safeParse(
			payment.billingAddress
		);

		return isAddressValid.success;
	}, [payment]);

	const {licenseType} = useSelector(
		productPurchaseStore,
		(state) => state.context
	);

	const {
		actions: {nextStep, previousStep},
		handlePurchase,
		productPurchaseCart,
		selectedAccount,
	} = useProductPurchaseOutletContext();

	useEffect(() => {
		if (!licenseType) {

			// Force redirect to checkout homepage

			navigate('/');
		}
	}, [licenseType, navigate]);

	useEffect(() => {
		productPurchaseStore.send({
			paymentMethodType: PaymentMethodType.PAY_NOW,
			type: 'setPaymentMethodType',
		});
	}, []);

	const onClickContinue = async () => {
		setLoading(true);

		try {
			if (licenseType === 'TRIAL') {
				return handlePurchase(ProductPurchaseApp, undefined, {
					isTrialSKU: true,
				});
			}

			await productPurchaseCart.updateCart(productPurchaseCart.cart.id, {
				billingAddress: payment.billingAddress,
			});

			if (payment.taxId && !selectedAccount.taxId) {
				await HeadlessAdminUser.updateAccount(selectedAccount.id, {
					taxId: payment.taxId,
				});
			}

			cartStore.send({
				cart: await HeadlessCommerceDeliveryCart.getCart(
					productPurchaseCart.cart.id
				),
				type: 'setCart',
			});

			nextStep();
		}
		finally {
			setLoading(false);
		}
	};

	return (
		<ProductPurchase.Shell
			className="select-payment-step"
			footerProps={{
				backButtonProps: {
					onClick: previousStep,
				},
				continueButtonProps: {
					children: i18n.translate('continue'),
					disabled: !isPrimaryButtonActive || loading,
					onClick: onClickContinue,
				},
			}}
			title={i18n.translate('payment-method')}
		>
			<BillingAddress hideNewAddressButton />

			<TaxIdDisplay />

			<PaymentTypeSelector
				allowedPaymentMethodTypes={[PaymentMethodType.PAY_NOW]}
			/>
		</ProductPurchase.Shell>
	);
}
