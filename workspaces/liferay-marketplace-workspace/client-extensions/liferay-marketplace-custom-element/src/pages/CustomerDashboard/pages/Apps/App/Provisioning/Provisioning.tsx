/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useParams} from 'react-router-dom';

import useGetProductByOrderId from '../../../../../../hooks/useGetProductByOrderId';
import ProvisioningTable from './ProvisioningTable';

const Provisioning = () => {
	const {orderId} = useParams();
	const orderInfo = useGetProductByOrderId(orderId as string);

	return <ProvisioningTable orderInfo={orderInfo} />;
};

export default Provisioning;
