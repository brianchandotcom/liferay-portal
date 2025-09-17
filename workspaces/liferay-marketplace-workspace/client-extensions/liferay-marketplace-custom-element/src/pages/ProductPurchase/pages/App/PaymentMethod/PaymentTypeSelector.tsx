/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {useSelector} from '@xstate/store/react';

import paypal from '../../../../../assets/images/paypal.png';
import {RadioCard} from '../../../../../components/RadioCard/RadioCard';
import {Section} from '../../../../../components/Section/Section';
import {Tooltip} from '../../../../../components/Tooltip/Tooltip';
import i18n from '../../../../../i18n';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';
import {productPurchaseStore} from '../../../store/AppPurchaseStore';
import {PaymentMethodType} from '../../../types';

const PaymentTypeSelector = () => {
	const {context} = productPurchaseStore.getSnapshot();
	const {selectedAccount} = useProductPurchaseOutletContext();

	const invoice = useSelector(
		productPurchaseStore,
		({context}) => context.payment.invoice
	);

	const paymentModes = [
		{
			action: () =>
				productPurchaseStore.send({
					paymentMethodType: PaymentMethodType.PAY_NOW,
					type: 'setPaymentMethodType',
				}),
			cardContent: (
				<div className="align-items-center d-flex flex-row justify-content-center">
					<div className="d-flex">
						<img
							alt="paypal"
							className="mr-3"
							height={18}
							src={paypal}
							width={16}
						/>
					</div>
					<div>
						<div className="align-items-center d-flex">
							<p className="mb-0 mr-3">PayPal with card</p>
							<Tooltip
								tooltip="you-ll-be-redirected-to-paypal-to-complete-your-purchase-securely-after-your-payment-you-are-able-to-activate-the-license-on-customer-dashboard-right-awayy"
								tooltipText={i18n.translate('more-info')}
							/>
						</div>
						<p className="font-weight-normal mb-0 text-black-50">
							Online payments with PayPal
						</p>
					</div>
				</div>
			),
			name: 'PayPal',
			type: PaymentMethodType.PAY_NOW,
		},
		{
			action: () => {
				productPurchaseStore.send({
					paymentMethodType: PaymentMethodType.INVOICE,
					type: 'setPaymentMethodType',
				});

				productPurchaseStore.send({
					invoice: {
						...invoice,
						email: selectedAccount.emailAddress,
						purchaseOrderNumber: context.payment.billingAddress
							.phoneNumber as string,
					},
					type: 'setInvoice',
				});
			},
			cardContent: (
				<div className="align-items-center d-flex flex-row justify-content-center">
					<div className="d-flex">
						<ClayIcon
							className="mr-3"
							color=" #0B5FFF"
							fontSize={16}
							symbol="document-text"
						/>
					</div>
					<div>
						<div className="align-items-center d-flex">
							<p className="mb-0 mr-3">PayPal with invoice</p>

							<Tooltip
								tooltip="you-will-receive-an-invoice-via-email-with-all-the-details-needed-to-complete-your-payment-after-you-complete-the-payment-you-can-activate-your-license-from-the-customer-dashboard"
								tooltipText={i18n.translate('more-info')}
							/>
						</div>
						<p className="font-weight-normal mb-0 text-black-50">
							Offline payemnts using the invoice
						</p>
					</div>
				</div>
			),
			name: 'Invoice',
			type: PaymentMethodType.INVOICE,
		},
	];

	return (
		<div className="d-flex flex-column justify-content-around">
			<Section label="Payment Method" required>
				{paymentModes.map((paymentMode, index) => (
					<RadioCard
						className="mb-3 w-100"
						content={paymentMode.cardContent}
						key={index}
						onChange={() => paymentMode.action()}
						selected={context.payment.type === paymentMode.type}
						small
					/>
				))}
			</Section>
		</div>
	);
};

export {PaymentTypeSelector};
