/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useModal} from '@clayui/modal';
import {useState} from 'react';

import {Checkbox} from '../../../components/Checkbox/Checkbox';
import {ContentModal} from '../../../components/ContentModal/ContentModal';
import {getSiteURL} from '../../../components/InviteMemberModal/services';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import useCart from '../../../hooks/useCart';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import {getSiteStructuredContentByKey} from '../../../utils/api';
import {PaymentMethod} from '../enums/paymentMethod';
import {StepType} from '../enums/stepType';

interface ProductFooterProps {
	addresses: BillingAddress[];
	cartId?: number;
	cartUtil: ReturnType<typeof useCart>;
	disabled: boolean;
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
	disabled,
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
	const {properties} = useMarketplaceContext();

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

		if (
			selectedPaymentMethod === PaymentMethod.TRIAL &&
			cartUtil?.cart?.id
		) {
			await cartUtil.removeCart(cartUtil?.cart?.id);
		}

		if ((!isFreeApp && isAccountStep && selectedAccount) || isLicenseStep) {
			return setStep(nextStep);
		}

		const isPaymentStep = step === StepType.PAYMENT;

		if (
			(isFreeApp && selectedAccount) ||
			(enablePurchaseButton && addresses && isPaymentStep)
		) {
			await handleGetApp(cartUtil.cart?.id);
		}
	};

	const [eulaCheckbox, setEulaCheckbox] = useState<boolean>(false);

	const [eulaDescription, setEulaDescription] = useState<string>('');

	const eulaModal = useModal();

	const getEulaDescription = async () => {
		const keyEula = 'EULA';
		const response = await getSiteStructuredContentByKey(keyEula);

		setEulaDescription(response?.contentFields[0]?.contentFieldValue?.data);

		return response?.contentFields[0]?.contentFieldValue?.data;
	};

	return (
		<>
			<div>
				{!isFreeApp &&
					step === StepType.PAYMENT &&
					selectedPaymentMethod === PaymentMethod.PAY && (
						<div className="align-items-start d-flex mt-4">
							<Checkbox
								checked={eulaCheckbox}
								onChange={() =>
									eulaCheckbox
										? setEulaCheckbox(false)
										: setEulaCheckbox(true)
								}
							/>
							<div>
								<span>I have read and agree to the </span>
								<a
									onClick={async () => {
										eulaModal.onOpenChange(true);
										await getEulaDescription();
									}}
								>
									End User License Agreement
								</a>
								<span> and the</span>
								<a
									onClick={() => {
										window.open(properties.eulaBaseURL);
									}}
								>
									{' '}
									Terms
								</a>
								<span> of Service.</span>
							</div>
						</div>
					)}
				{eulaModal.open && (
					<ContentModal
						description={eulaDescription}
						header={i18n.translate('end-user-license-agreement')}
						{...eulaModal}
					/>
				)}
			</div>
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
									disabled ||
									(step === StepType.ACCOUNT &&
										!selectedAccount) ||
									(step === StepType.LICENSES &&
										!licenseSelected) ||
									(step === StepType.PAYMENT && !eulaCheckbox)
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
			</div>
		</>
	);
};

export default ProductFooter;
