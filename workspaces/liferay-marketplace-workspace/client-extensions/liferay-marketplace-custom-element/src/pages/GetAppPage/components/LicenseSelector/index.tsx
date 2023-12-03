/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';

import {CardButton} from '../../../../components/CardButton/CardButton';

import './index.scss';

import {UseFormSetValue, UseFormWatch} from 'react-hook-form';

import useCart from '../../../../hooks/useCart';
import {GetAppForm} from '../../GetAppPage';
import {PaymentMethod} from '../../enums/paymentMethod';
import {PaidTimeline} from './components/PaidTimeline';
import {TrialTimeline} from './components/TrialTimeline';

type LicenseSelectorProps = {
	cartUtil: ReturnType<typeof useCart>;
	formUtils: {
		setValue: UseFormSetValue<GetAppForm>;
		watch: UseFormWatch<GetAppForm>;
	};
	onSelectLicense: (sku?: DeliverySKU) => void;
	selectedProduct?: DeliveryProduct;
	setLicenseSelected: (licenseSelected: boolean) => void;
	sku: DeliverySKU;
	trialSKU: any;
};

export function LicenseSelector({
	cartUtil,
	formUtils,
	onSelectLicense,
	selectedProduct,
	setLicenseSelected,
	trialSKU,
}: LicenseSelectorProps) {
	const handleLicenseSelect = () => {
		onSelectLicense(trialSKU);
		setLicenseSelected(true);
	};

	return (
		<div className="license-selector-timeline">
			<div className="license-selector mb-6">
				<CardButton
					description="Try now. Pay Later"
					disabled={!trialSKU}
					icon={
						<span className="license-icon">
							<ClayIcon symbol="check-circle" />
						</span>
					}
					onClick={() => {
						if (cartUtil?.cart?.id) {
							cartUtil.removeCart(cartUtil?.cart?.id);
						}

						formUtils.setValue(
							'selectedPaymentMethod',
							PaymentMethod.TRIAL
						);
						formUtils.setValue('selectedTimeline', 'trial');
					}}
					selected={formUtils.watch('selectedTimeline') === 'trial'}
					title={
						formUtils.watch('selectedTimeline') === 'trial'
							? '30-day Trial'
							: 'Trial'
					}
				/>

				<CardButton
					description="Pay Today"
					icon={
						<span className="license-icon">
							<ClayIcon symbol="credit-card" />
						</span>
					}
					onClick={() => {
						formUtils.setValue('selectedTimeline', 'paid');
						formUtils.setValue(
							'selectedPaymentMethod',
							PaymentMethod.PAY
						);
					}}
					selected={formUtils.watch('selectedTimeline') === 'paid'}
					title="Paid"
				/>
			</div>

			{formUtils.watch('selectedTimeline') && (
				<div className="timeline-container">
					{formUtils.watch('selectedTimeline') === 'trial' ? (
						<TrialTimeline
							handleLicenseSelect={handleLicenseSelect}
						/>
					) : (
						<PaidTimeline
							cartUtil={cartUtil}
							product={selectedProduct}
						/>
					)}
				</div>
			)}
		</div>
	);
}
