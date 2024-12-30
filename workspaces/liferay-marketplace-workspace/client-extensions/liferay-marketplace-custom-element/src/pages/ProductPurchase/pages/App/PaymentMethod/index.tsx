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
import {productPurchaseStore} from '../../../store/AppPurchaseStore';
import {PaymentMethodType} from '../../../types';
import {BillingAddress} from './BillingAddress/BillingAddress';
import InvoiceForm from './InvoiceForm';
import PayNow from './PayNow';
import {PaymentTypeSelector} from './PaymentTypeSelector';
import {TrialMethod} from './TrialMethod/TrialMethod';

const PaymentMethodFlows = {
	[PaymentMethodType.TRIAL]: {
		Component: TrialMethod,
		actionMessage: i18n.translate('start-trial'),
	},
	[PaymentMethodType.PAY_NOW]: {
		Component: PayNow,
		actionMessage: (price: string) => `Pay ${price} Now`,
	},
	[PaymentMethodType.INVOICE]: {
		Component: InvoiceForm,
		actionMessage: (price: string) => `Create PO for ${price}`,
	},
};

const isPrimaryButtonActive = () => {
	const {context} = productPurchaseStore.getSnapshot();

	const isAddressValid = zodSchema.billingAddress.safeParse(
		context.payment.billingAddress
	);

	if (!isAddressValid.success) {
		return false;
	}

	const {type: paymentMethodType} = context.payment;

	if (paymentMethodType === PaymentMethodType.TRIAL) {
		return true;
	}

	if (paymentMethodType === PaymentMethodType.PAY_NOW) {
		return context.payment.eulaAgreement;
	}

	const invoiceValues = Object.values(context.payment.invoice);

	return (
		!!invoiceValues.length && invoiceValues.every((value) => value.trim())
	);
};

export default function PaymentMethod() {
	const navigate = useNavigate();

	const {licenseType, payment: paymentStore} = useSelector(
		productPurchaseStore,
		(state) => state.context
	);

	const {
		actions: {previousStep},
		handlePurchase,
		productPurchaseCart: {cart, cartItems},
		selectedAccount,
	} = useProductPurchaseOutletContext();

	const onClickPayNow = async () => {
		if (licenseType === 'TRIAL') {
			return handlePurchase(ProductPurchaseApp, undefined, {
				isTrialSKU: true,
			});
		}

		await handlePurchase(ProductPurchaseApp, {
			...cart,
			billingAddress: paymentStore.billingAddress,
			cartItems,
			shippingAddress: paymentStore.billingAddress,
		});
	};

	useEffect(() => {
		if (!licenseType) {

			// Force redirect to checkout homepage

			navigate('/');
		}
	}, [licenseType, navigate]);

	const {data: addressResponse = {items: []}} = useAccountAddresses(
		selectedAccount?.id
	);

	const {Component: PaymentFlowComponent, actionMessage} =
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
					children:
						typeof actionMessage === 'function'
							? actionMessage(
									cart?.summary?.totalFormatted ?? '0'
								)
							: actionMessage,
					disabled: !isPrimaryButtonActive(),
					onClick: onClickPayNow,
				},
			}}
			title={i18n.translate('payment-method')}
		>
			<PaymentTypeSelector
				paymentMethodType={paymentStore.type}
				setPaymentMethodType={(paymentMethodType) =>
					productPurchaseStore.send({
						paymentMethodType,
						type: 'setPaymentMethodType',
					})
				}
			/>

			<BillingAddress
				addresses={addressResponse.items}
				billingAddress={paymentStore.billingAddress as BillingAddress}
				setBillingAddress={(billingAddress) =>
					productPurchaseStore.send({
						billingAddress,
						type: 'setBillingAddress',
					})
				}
			/>

			<PaymentFlowComponent />
		</ProductPurchase.Shell>
	);
}
