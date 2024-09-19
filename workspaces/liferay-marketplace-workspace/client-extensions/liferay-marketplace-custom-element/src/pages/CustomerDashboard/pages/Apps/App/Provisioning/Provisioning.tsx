/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useParams} from 'react-router-dom';

import ProvisioningTable from './components/ProvisioningTable';
import useProvisioningData from './hooks/useProvisioningData';

const Provisioning = () => {
	const {orderId} = useParams();
	const {mutateOrder, order, provisioningTableData, resourceRequirements} =
		useProvisioningData(orderId as string);

	return (
		<ProvisioningTable
			mutateOrder={mutateOrder}
			order={order}
			provisioningTableData={provisioningTableData || []}
			resourceRequirements={resourceRequirements}
		/>
	);
};

export default Provisioning;
