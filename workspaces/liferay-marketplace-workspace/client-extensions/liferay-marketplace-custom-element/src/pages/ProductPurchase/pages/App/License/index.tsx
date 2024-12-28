/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {useSelector} from '@xstate/store/react';
import {useMemo} from 'react';

import CardButton from '../../../../../components/CardButton/CardButton';
import ProductPurchase from '../../../../../components/ProductPurchase';
import i18n from '../../../../../i18n';
import {
	LicenseType,
	productPurchaseStore,
} from '../../../store/AppPurchaseStore';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';
import PaidLicense from './PaidLicense';
import TrialLicense from './TrialLicense';

import {getSkuByOptionValueKey} from '../../../../../utils/productUtils';
import {SkuLicenseUsageTypeValue} from '../../../../../enums/Sku';
import {PaymentMethodType} from '../../../types';
import '../../../../GetApp/styles/index.scss';

const isContinueButtonDisabled = () => {
	const snapshot = productPurchaseStore.getSnapshot();

	if (snapshot.context.licenseType === null) {
		return true;
	}

	if (snapshot.context.licenseType === 'PAID') {
		return false;
	}

	return snapshot.context.payment.type !== PaymentMethodType.TRIAL;
};

const License = () => {
	const {product} = useProductPurchaseOutletContext();

	const {
		actions: {nextStep, previousStep},
	} = useProductPurchaseOutletContext();

	const {licenseType} = useSelector(
		productPurchaseStore,
		(state) => state.context
	);

	const licenseOptions = useMemo(
		() => [
			{
				disabled: !getSkuByOptionValueKey(
					product,
					SkuLicenseUsageTypeValue.TRIAL
				),
				description: 'Try now. Pay later.',
				icon: 'check-circle',
				title: '30-day Trial',
				type: 'TRIAL',
			},
			{
				description: 'Pay Today',
				icon: 'credit-card',
				title: 'Paid',
				type: 'PAID',
			},
		],
		[]
	);

	const Component = licenseType === 'PAID' ? PaidLicense : TrialLicense;

	return (
		<ProductPurchase.Shell
			className="d-flex flex-column license-selector-timeline"
			footerProps={{
				backButtonProps: {onClick: previousStep},
				continueButtonProps: {
					disabled: isContinueButtonDisabled(),
					onClick: () => nextStep(),
				},
			}}
			title={i18n.translate('license-selection')}
		>
			<div className="license-selector mb-6">
				{licenseOptions.map(({icon, type, ...licenseOption}, index) => (
					<CardButton
						{...licenseOption}
						icon={
							<span className="license-icon">
								<ClayIcon symbol={icon} />
							</span>
						}
						key={index}
						onClick={() =>
							productPurchaseStore.send({
								licenseType: type as LicenseType,
								type: 'setLicenseType',
							})
						}
						selected={licenseType === type}
					/>
				))}
			</div>

			{licenseType && (
				<>
					<span className="h5">
						Need help with license calculations?
					</span>

					<Component />
				</>
			)}
		</ProductPurchase.Shell>
	);
};

export default License;
