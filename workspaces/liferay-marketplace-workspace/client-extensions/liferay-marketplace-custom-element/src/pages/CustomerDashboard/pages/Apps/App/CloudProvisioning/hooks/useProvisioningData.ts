/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addYears, format} from 'date-fns';
import {useEffect, useMemo, useState} from 'react';

import {ORDER_CUSTOM_FIELDS} from '../../../../../../../enums/Order';
import {PRODUCT_SPECIFICATION_KEY} from '../../../../../../../enums/Product';
import useGetProductByOrderId from '../../../../../../../hooks/useGetProductByOrderId';
import i18n from '../../../../../../../i18n';
import {getSpecificationByKey} from '../../../../../../../utils/productUtils';
import {safeJSONParse} from '../../../../../../../utils/util';
import {LicenseType} from '../../../../../../GetApp/enums/licenseType';
import useGetResourceInfo from '../../../../../../GetApp/hooks/useGetResourceInfo';
import {InstallStatus} from '../types';

export type ProvisioningRow = ReturnType<
	typeof useProvisioningData
>['provisioningTableData'][0];

const ACTIVE_REFRESH_INTERVAL = 60 * 1000;
const DEFAULT_REFRESH_INTERVAL = 240 * 1000;

const getExpirationDate = (createdDate: Date, licenseType: string) => {
	if (licenseType === 'Perpetual') {
		return 'DNE';
	}

	return format(addYears(createdDate, 1), 'MMM dd, yyyy');
};

const getStatus = (
	deployment: any,
	licenseType: string,
	order: PlacedOrder
) => {
	if (deployment?.loading) {
		return InstallStatus.IN_PROGRESS;
	}

	if (
		licenseType.toLowerCase() === LicenseType.Subscription &&
		new Date(order.createDate) > addYears(new Date(order.createDate), 1)
	) {
		return InstallStatus.EXPIRED;
	}

	return deployment
		? InstallStatus.INSTALLED
		: InstallStatus.READY_TO_INSTALL;
};

const useProvisioningData = (orderId: string) => {
	const [refreshInterval, setRefreshInterval] = useState(
		DEFAULT_REFRESH_INTERVAL
	);

	const {data, mutate: mutateOrder} = useGetProductByOrderId(orderId, {
		refreshInterval,
	});

	const order = useMemo(
		() => data?.placedOrder || ({} as PlacedOrder),
		[data?.placedOrder]
	);
	const orderItems = order.placedOrderItems;
	const product = data?.product;

	const resourceRequirements = useGetResourceInfo({
		product,
		selectedProject: undefined,
		shouldFetch: true,
	});

	const productLicenseType = useMemo(
		() =>
			getSpecificationByKey(
				PRODUCT_SPECIFICATION_KEY.APP_LICENSING_TYPE,
				product as DeliveryProduct
			)?.value || '',
		[product]
	);

	const provisioningTableData = useMemo(() => {
		const items = [];

		const [cloudProvisioning] = safeJSONParse<{deployments: any[]}[]>(
			order.customFields[ORDER_CUSTOM_FIELDS.CLOUD_PROVISIONING],
			[{deployments: []}]
		);

		for (const orderItem of orderItems) {
			for (let i = 0; i < orderItem.quantity; i++) {
				const deployment = cloudProvisioning.deployments[i];

				let environment = i18n.translate('not-installed');
				let project = i18n.translate('not-installed');

				if (deployment) {
					[project, environment] = deployment.projectId.split('-');

					environment = environment.toUpperCase();
					project = project.toUpperCase();
				}

				items.push({
					environment,
					expirationDate: getExpirationDate(
						new Date(order.createDate),
						productLicenseType
					),
					host: '',
					id: deployment?.id || i,
					loading: deployment?.loading,
					orderItemId: orderItem.id,
					project,
					startDate: format(
						new Date(order.createDate),
						'MMM dd, yyyy'
					),
					status: getStatus(deployment, productLicenseType, order),
					type: productLicenseType,
				});
			}
		}

		return items;
	}, [order, orderItems, productLicenseType]);

	useEffect(() => {
		const refresh = provisioningTableData.some(
			(provisioning) => provisioning.loading === true
		);

		if (refresh) {
			setRefreshInterval(ACTIVE_REFRESH_INTERVAL);
		}
	}, [provisioningTableData]);

	return {
		mutateOrder,
		order,
		provisioningTableData,
		resourceRequirements,
	};
};

export default useProvisioningData;
