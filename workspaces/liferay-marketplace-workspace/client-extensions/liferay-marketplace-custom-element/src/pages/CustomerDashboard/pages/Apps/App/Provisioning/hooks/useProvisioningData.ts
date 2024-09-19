/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import useGetResourceInfo from '../../../../../../GetApp/hooks/useGetResourceInfo';
import {isCloudProduct} from '../../../../../../../utils/productUtils';
import useGetProductByOrderId from '../../../../../../../hooks/useGetProductByOrderId';
import {InstallStatus} from '../components/InstallStatus';
import {CUSTON_FIELKEYS, LICENSE_TYPE_KEY} from '../Types';
import {addDays} from 'date-fns';
import {safeJSONParse} from '../../../../../../../utils/util';
import i18n from '../../../../../../../i18n';

const useProvisioningData = (orderId: string) => {
	const {data} = useGetProductByOrderId(orderId);

	const order = data?.placedOrder;
	const orderItems = order.placedOrderItems;
	const isCloudApp = isCloudProduct(data?.product);

	const resourceRequirements = useGetResourceInfo({
		product: data?.product,
		selectedProject: undefined,
		shouldFetch: isCloudApp,
	});

	const [cloudProvisioning] = safeJSONParse(
		order.customFields[CUSTON_FIELKEYS.CLOUD_PROVISIONING],
		{deployments: []}
	);

	const isIstalled = cloudProvisioning?.deployments?.lenght;

	const notIstalledPlaceHolder = isIstalled
		? order.customFields[CUSTON_FIELKEYS.PROJECT_NAME]
		: i18n.translate('not-installed');

	const produtctLicenseType =
		data?.product?.productSpecifications.filter(
			(specification) =>
				specification.specificationKey === LICENSE_TYPE_KEY
		) || [];

	const getExpirationDate = (createdDate: Date, licenseType: string) => {
		if (licenseType === 'Perpetual') {
			return i18n.translate('never-expires');
		}

		return addDays(createdDate, 30);
	};

	const provisioningTableData = useMemo(() => {
		const items = orderItems?.map((item: PlacedOrderItems) => {
			return {
				environment: notIstalledPlaceHolder,
				expirationDate: getExpirationDate(
					order.createDate,
					produtctLicenseType[0]?.value
				),
				host: notIstalledPlaceHolder,
				id: cloudProvisioning.orderItemId,
				project: notIstalledPlaceHolder,
				startDate: order.createDate,
				status: isIstalled
					? InstallStatus.INSTALLED
					: InstallStatus.READY_TO_INSTALL,
				type: produtctLicenseType[0]?.value,
			};
		});

		return items;
	}, []);

	return {
		order,
		provisioningTableData,
		resourceRequirements,
	};
};

export default useProvisioningData;
