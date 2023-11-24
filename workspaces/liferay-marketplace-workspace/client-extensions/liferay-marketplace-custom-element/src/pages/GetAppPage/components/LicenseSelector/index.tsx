/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {useCallback, useEffect, useState} from 'react';

import {CardButton} from '../../../../components/CardButton/CardButton';

import './index.scss';

import {UseFormSetValue, UseFormWatch} from 'react-hook-form';

import useCart from '../../../../hooks/useCart';
import {GetAppForm} from '../../GetAppPage';
import {PaymentMethod} from '../../enums/paymentMethod';
import {PaidTimeline} from './components/PaidTimeline';
import {TrialTimeline} from './components/TrialTimeline';

interface LicenseSelectorProps {
	cartUtil: ReturnType<typeof useCart>;
	formUtils: {
		setValue: UseFormSetValue<GetAppForm>;
		watch: UseFormWatch<GetAppForm>;
	};
	onSelectLicense: (sku?: DeliverySKU) => void;
	selectedProduct?: DeliveryProduct;
	setLicenseSelected: (licenseSelected: boolean) => void;
	sku: DeliverySKU;
}

export function LicenseSelector({
	cartUtil,
	formUtils,
	onSelectLicense,
	selectedProduct,
	setLicenseSelected,
}: LicenseSelectorProps) {
	const [trialSKU, setTrialSKU] = useState<DeliverySKU>();
	const [disabledButton, setDisabledButton] = useState<boolean>(true);

	const hasTrialSkuVerification = useCallback(() => {
		const skus = selectedProduct?.skus;

		const [trialSkuOption] =
			skus?.filter((sku) =>
				sku?.skuOptions.find(
					(skuOption) => {
						return skuOption.skuOptionKey.toLocaleLowerCase() === 'trial' &&
							skuOption.skuOptionValueKey.toLocaleLowerCase() === 'yes'
					}
				)
			) || [];

		if (trialSkuOption) {
			setDisabledButton(false);
			setTrialSKU(trialSkuOption);
		}
	}, [selectedProduct?.skus]);

	useEffect(() => {
		hasTrialSkuVerification();
	}, [hasTrialSkuVerification]);

	const handleLicenseSelect = (licenseSelected: boolean) => {
		if (licenseSelected) {
			onSelectLicense(trialSKU);
			setLicenseSelected(true);
			setDisabledButton(false);
		}
	};

	return (
		<div className="license-selector-timeline">
			<div className="license-selector mb-6">
				<CardButton
					description="Try now. Pay Later"
					disabled={disabledButton}
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
					disabled={false}
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
							setLicenseSelected={handleLicenseSelect}
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
