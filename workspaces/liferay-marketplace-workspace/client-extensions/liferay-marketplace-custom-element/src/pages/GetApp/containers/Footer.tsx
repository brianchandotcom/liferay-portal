/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useModal} from '@clayui/modal';
import {useNavigate} from 'react-router-dom';

import {Checkbox} from '../../../components/Checkbox/Checkbox';
import {ContentModal} from '../../../components/ContentModal/ContentModal';
import {getSiteURL} from '../../../components/InviteMemberModal/services';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import useCart from '../../../hooks/useCart';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import {useGetAppContext} from '../GetAppContextProvider';
import {PaymentMethod} from '../enums/paymentMethod';
import {StepType} from '../enums/stepType';

const LicenseTermsCheckbox = () => {
	const [
		{
			payment: {eula, eulaCheckbox},
		},
		dispatch,
	] = useGetAppContext();
	const {properties} = useMarketplaceContext();
	const eulaModal = useModal();

	return (
		<div className="align-items-start d-flex eula-container mt-4">
			{eulaModal.open && (
				<ContentModal
					description={eula}
					header={i18n.translate('end-user-license-agreement')}
					{...eulaModal}
				/>
			)}
			<Checkbox
				checked={eulaCheckbox}
				onChange={() =>
					dispatch({
						payload: !eulaCheckbox,
						type: 'SET_EULA_CHECKBOX',
					})
				}
			/>
			I have read and agree to the
			<a onClick={() => eulaModal.onOpenChange(true)}>
				&nbsp;End User License Agreement&nbsp;
			</a>
			and the
			<a onClick={() => window.open(properties.eulaBaseURL)}>
				&nbsp;Terms&nbsp;
			</a>
			of Service.
		</div>
	);
};

type ProductFooterProps = {
	cartUtil: ReturnType<typeof useCart>;
	disabled: boolean;
	handleGetApp: (orderId?: number) => void;
	isFreeApp: boolean;
	selectedPaymentMethod: PaymentMethodSelector;
};

const ProductFooter: React.FC<ProductFooterProps> = ({
	cartUtil,
	disabled,
	handleGetApp,
	isFreeApp,
	selectedPaymentMethod,
}) => {
	const [
		{
			account,
			appResourceInfo: {hasResources},
			currentStep,
			formState: {isValid},
			project,
			steps,
		},
		dispatch,
	] = useGetAppContext();

	const navigate = useNavigate();

	const nextStep = steps[currentStep + 1];
	const previousStep = steps[currentStep - 1];
	const step = steps[currentStep];

	const getButtonText = () => {
		if (isFreeApp) {
			return 'Get App';
		}

		if (
			[StepType.ACCOUNT, StepType.LICENSES, StepType.PROJECT].includes(
				step.id
			)
		) {
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

	const onPrevious = () => {
		dispatch({payload: currentStep - 1, type: 'SET_STEP'});

		navigate(previousStep.path, {replace: true});
	};

	const onContinue = async () => {
		if (nextStep) {
			if (step.id === StepType.ACCOUNT && isFreeApp) {
				return handleGetApp(cartUtil.cart?.id);
			}

			if (step.id === StepType.PROJECT && !hasResources) {
				return navigate(
					`/insuficient-resources/${project}/${
						(account as Account).id
					}`
				);
			}

			dispatch({payload: currentStep + 1, type: 'SET_STEP'});

			return navigate(nextStep.path, {replace: true});
		}

		if (
			selectedPaymentMethod === PaymentMethod.TRIAL &&
			cartUtil?.cart?.id
		) {
			await cartUtil.removeCart(cartUtil?.cart?.id);
		}

		await handleGetApp(cartUtil.cart?.id);
	};

	const isPaymentStepType =
		StepType.PAYMENT === step.id &&
		PaymentMethod.PAY === selectedPaymentMethod;

	const displayTermsCheckbox = isPaymentStepType && !isFreeApp;

	return (
		<div>
			{displayTermsCheckbox && <LicenseTermsCheckbox />}

			<div className="mt-5 pt-2 text-black-50">
				<div className="d-flex justify-content-between">
					<ClayButton
						displayType={null}
						onClick={() => {
							if (cartUtil?.cart?.id) {
								cartUtil.removeCart(cartUtil.cart.id);
							}

							Liferay.Util.navigate(getSiteURL());
						}}
					>
						Cancel
					</ClayButton>

					<div>
						{previousStep && (
							<ClayButton
								displayType="secondary"
								onClick={onPrevious}
							>
								Back
							</ClayButton>
						)}

						<ClayButton
							className="ml-5"
							disabled={!isValid || disabled}
							onClick={onContinue}
						>
							{getButtonText()}
						</ClayButton>
					</div>
				</div>
			</div>

			{isPaymentStepType && (
				<div className="d-flex flex-column mt-5 text-gray text-right">
					<span className="text-2">
						You will be redirected to PayPal to complete payment
					</span>

					<span className="text-2">
						<ClayIcon className="mr-2" symbol="info-panel-open" />
						Terms, privacy, returns, or contact support. All costs
						are in US Dollars
					</span>
				</div>
			)}
		</div>
	);
};

export default ProductFooter;
