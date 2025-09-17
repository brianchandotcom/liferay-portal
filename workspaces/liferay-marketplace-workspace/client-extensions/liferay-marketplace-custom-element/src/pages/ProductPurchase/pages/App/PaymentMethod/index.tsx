/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useSelector} from '@xstate/store/react';
import {useEffect} from 'react';
import {useNavigate} from 'react-router-dom';

import ProductPurchase from '../../../../../components/ProductPurchase';
import useAccountAddresses from '../../../../../hooks/useAccountAddresses';
import i18n from '../../../../../i18n';
import zodSchema from '../../../../../schema/zod';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';
import ProductPurchaseApp from '../../../services/ProductPurchaseApp';
import {cartStore} from '../../../store';
import {productPurchaseStore} from '../../../store/AppPurchaseStore';
import {PaymentMethodType} from '../../../types';
import {BillingAddress} from './BillingAddress/BillingAddress';
import {PaymentTypeSelector} from './PaymentTypeSelector';
import TaxIdDisplay from './TaxIdDisplay';
import {TrialMethod} from './TrialMethod/TrialMethod';

const PaymentMethodFlows = {
	[PaymentMethodType.TRIAL]: {
		actionMessage: i18n.translate('start-trial'),
	},
	[PaymentMethodType.PAY_NOW]: {
		actionMessage: i18n.translate('continue'),
	},
	[PaymentMethodType.INVOICE]: {
		actionMessage: i18n.translate('continue'),
	},
};

const isPrimaryButtonActive = () => {
	const {context} = productPurchaseStore.getSnapshot();

	const {type: paymentMethodType} = context.payment;

	const isAddressValid = zodSchema.billingAddress.safeParse(
		context.payment.billingAddress
	);

	if (paymentMethodType === PaymentMethodType.TRIAL) {
		return isAddressValid.success && true;
	}

	if (paymentMethodType === PaymentMethodType.PAY_NOW) {
		return isAddressValid.success;
	}

	const invoiceValues = Object.values(context.payment.invoice);

	return (
		!!invoiceValues.length && invoiceValues.every((value) => value.trim())
	);
};

export default function PaymentMethod() {
	const navigate = useNavigate();
	const {context} = productPurchaseStore.getSnapshot();
	const {type: paymentMethodType} = context.payment;

	const {licenseType, payment: paymentStore} = useSelector(
		productPurchaseStore,
		(state) => state.context
	);

	const {
		actions: {nextStep, previousStep},
		handlePurchase,
		productPurchaseCart,
		selectedAccount,
	} = useProductPurchaseOutletContext();

	const onClickContinue = async () => {
		if (licenseType === 'TRIAL') {
			return handlePurchase(ProductPurchaseApp, undefined, {
				isTrialSKU: true,
			});
		}

		const updatedCart = await productPurchaseCart.updateCart(
			productPurchaseCart.cart.id,
			{billingAddress: context.payment.billingAddress}
		);

		cartStore.send({cart: updatedCart, type: 'setCart'});

		nextStep();
	};

	useEffect(() => {
		if (!licenseType) {

			// Force redirect to checkout homepage

			navigate('/');
		}
	}, [licenseType, navigate]);

	const {
		data: addressResponse = {items: []},
		mutate: mutateUserAccoutAddress,
	} = useAccountAddresses(selectedAccount?.id);

	const {actionMessage} =
		PaymentMethodFlows[
			paymentStore.type as keyof typeof PaymentMethodFlows
		];

	return (
		<ProductPurchase.Shell
			className="select-payment-step"
			footerProps={{
				backButtonProps: {
					onClick: previousStep,
				},
				continueButtonProps: {
					children: actionMessage,
					disabled: !isPrimaryButtonActive(),
					onClick: onClickContinue,
				},
			}}
			title={i18n.translate('payment-method')}
		>
			<BillingAddress
				addresses={addressResponse.items}
				billingAddress={paymentStore.billingAddress as BillingAddress}
				mutateUserAccoutAddress={mutateUserAccoutAddress}
				setBillingAddress={(billingAddress) =>
					productPurchaseStore.send({
						billingAddress,
						type: 'setBillingAddress',
					})
				}
			/>

			{paymentMethodType === PaymentMethodType.TRIAL ? (
				<TrialMethod />
			) : (
				<>
					<TaxIdDisplay />
					<PaymentTypeSelector />
				</>
			)}
		</ProductPurchase.Shell>
	);
}
