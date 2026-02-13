/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LoadingIndicator from '@clayui/loading-indicator';
import {useParams} from 'react-router-dom';

import {PaymentStatus} from '../../../../enums/Order';
import useGetProductByOrderId from '../../../../hooks/useGetProductByOrderId';
import i18n from '../../../../i18n';
import PaymentStatusLabel from '../../../FinanceDashboard/components/PaymentStatus/PaymentStatusBadge';
import {BaseOutlet} from '../Apps/App/AppOutlet';

const LiferayServicesOutlet = () => {
	const {orderId} = useParams();
	const {data, isLoading} = useGetProductByOrderId(orderId as string);

	const order = data?.placedOrder;

	return (
		<BaseOutlet
			backTitle={i18n.translate('back-to-liferay-services')}
			backURL="../services"
			customStatus={
				isLoading ? (
					<LoadingIndicator />
				) : (
					order?.paymentStatus === PaymentStatus.PENDING && (
						<PaymentStatusLabel
							paymentStatus={PaymentStatus.PENDING}
						/>
					)
				)
			}
			routes={[{name: i18n.translate('details'), path: ''}]}
			showActions={false}
		/>
	);
};

export default LiferayServicesOutlet;
