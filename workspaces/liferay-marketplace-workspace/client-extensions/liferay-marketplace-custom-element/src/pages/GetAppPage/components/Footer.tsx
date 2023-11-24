/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';

import infoCircleIcon from '../../../assets/icons/info_circle_icon.svg';
import {getSiteURL} from '../../../components/InviteMemberModal/services';
import useCart from '../../../hooks/useCart';
import {Liferay} from '../../../liferay/liferay';
import {PaymentMethod} from '../enums/paymentMethod';
import {StepType} from '../enums/stepType';

interface ProductFooterProps {
	addresses: BillingAddress[];
	cartId?: number;
	cartUtil: ReturnType<typeof useCart>;
	enablePurchaseButton: boolean;
	handleGetApp: (orderId?: number) => void;
	isFreeApp: boolean;
	licenseSelected: boolean;
	selectedAccount?: Account;
	selectedPaymentMethod: PaymentMethodSelector;
	selectedSKU?: DeliverySKU;
	setStep: (nextStep: StepType) => void;
	step: StepType;
	stepsNavigation: StepsNavigation;
}

type StepsNavigation = {
	[key in StepType]: {
		backStep: StepType;
		nextStep: StepType;
	};
};

const onCancel = () => {
	Liferay.Util.navigate(getSiteURL());
};

const ProductFooter = ({
	addresses,
	cartUtil,
	enablePurchaseButton,
	handleGetApp,
	isFreeApp,
	licenseSelected,
	selectedAccount,
	selectedPaymentMethod,
	setStep,
	step,
	stepsNavigation,
}: ProductFooterProps) => {
	const getButtonText = () => {
		if (isFreeApp) {
			return 'Get App';
		}

		if ([StepType.ACCOUNT, StepType.LICENSES].includes(step)) {
			return 'Continue';
		}

		if (selectedPaymentMethod === PaymentMethod.PAY) {
			return `Pay ${cartUtil?.cart?.summary?.totalFormatted ?? 0} Now`;
		}

		if (selectedPaymentMethod === PaymentMethod.TRIAL) {
			return 'Start Free Trial';
		}

		if (selectedPaymentMethod === PaymentMethod.ORDER) {
			return `Create PO for ${cartUtil?.cart?.summary?.totalFormatted}`;
		}
	};

	const onPrevious = (previousStep: StepType) => setStep(previousStep);

	const onContinue = async (nextStep: StepType) => {
		const isAccountStep = step === StepType.ACCOUNT;
		const isLicenseStep = step === StepType.LICENSES;

		if ((!isFreeApp && isAccountStep && selectedAccount) || isLicenseStep) {
			return setStep(nextStep);
		}

		const isPaymentStep = step === StepType.PAYMENT;

		if (
			(isFreeApp && selectedAccount) ||
			(enablePurchaseButton && addresses && isPaymentStep)
		) {
			handleGetApp(cartUtil.cart?.id);
		}
	};

	return (
		<div className="mt-5 pt-2 text-black-50">
			<div className="d-flex justify-content-between">
				<ClayButton
					displayType={null}
					onClick={() => {
						if (cartUtil?.cart?.id) {
							cartUtil.removeCart(cartUtil.cart.id);
						}

						onCancel();
					}}
				>
					Cancel
				</ClayButton>
				<div>
					{stepsNavigation[step].backStep !== step && (
						<ClayButton
							displayType="secondary"
							onClick={() =>
								onPrevious(stepsNavigation[step].backStep)
							}
						>
							Back
						</ClayButton>
					)}
					{stepsNavigation[step].nextStep && (
						<ClayButton
							className="ml-5"
							disabled={
								(step === StepType.ACCOUNT &&
									!selectedAccount) ||
								(step === StepType.LICENSES && !licenseSelected)
							}
							onClick={() =>
								onContinue(stepsNavigation[step].nextStep)
							}
						>
							{getButtonText()}
						</ClayButton>
					)}
				</div>
			</div>

			{!isFreeApp &&
				step === StepType.PAYMENT &&
				selectedPaymentMethod === PaymentMethod.PAY && (
					<div className="align-items-end d-flex flex-column mt-4">
						<span>
							You will be redirected to PayPal to complete payment
						</span>
						<div className="mt-1">
							<img
								alt="Account icon"
								className="mr-2"
								src={infoCircleIcon}
							/>
							<span>
								Terms, privacy, returns, or contact support. All
								costs are in US Dollars
							</span>
						</div>
					</div>
				)}
		</div>
	);
};

export default ProductFooter;
