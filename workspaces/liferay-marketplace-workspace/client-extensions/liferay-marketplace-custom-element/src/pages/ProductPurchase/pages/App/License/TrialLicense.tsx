/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {useSelector} from '@xstate/store/react';

import CardButton from '../../../../../components/CardButton/CardButton';
import {productPurchaseStore} from '../../../store/AppPurchaseStore';
import {PaymentMethodType} from '../../../types';

const TrialLicense = () => {
	const paymentMethodType = useSelector(
		productPurchaseStore,
		({context}) => context.payment.type
	);

	return (
		<div className="timeline-container trial-timeline">
			<CardButton
				description="Trial licenses are intended for you to try the app before you buy. Typical trials are 30 days."
				icon={<ClayIcon className="mt-1" symbol="percentage-symbol" />}
				onClick={() =>
					productPurchaseStore.send({
						paymentMethodType: PaymentMethodType.TRIAL,
						type: 'setPaymentMethodType',
					})
				}
				selected={paymentMethodType === PaymentMethodType.TRIAL}
				title="Trial License"
			/>
		</div>
	);
};

export default TrialLicense;
