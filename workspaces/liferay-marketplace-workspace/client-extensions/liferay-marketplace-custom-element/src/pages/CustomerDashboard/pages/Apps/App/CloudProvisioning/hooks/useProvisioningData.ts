/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addYears, format} from 'date-fns';
import {useMemo} from 'react';

import {ORDER_CUSTOM_FIELDS} from '../../../../../../../enums/Order';
import {PRODUCT_SPECIFICATION_KEY} from '../../../../../../../enums/Product';
import useGetProductByOrderId from '../../../../../../../hooks/useGetProductByOrderId';
import i18n from '../../../../../../../i18n';
import {getSpecificationByKey} from '../../../../../../../utils/productUtils';
import {safeJSONParse} from '../../../../../../../utils/util';
import useGetResourceInfo from '../../../../../../GetApp/hooks/useGetResourceInfo';
import {InstallStatus} from '../types';

const getExpirationDate = (createdDate: Date, licenseType: string) => {
	if (licenseType === 'Perpetual') {
		return 'DNE';
	}

	return format(addYears(createdDate, 1), 'MMM dd, yyyy');
};

const useProvisioningData = (orderId: string) => {
	const {data, mutate: mutateOrder} = useGetProductByOrderId(orderId);

	const order = data?.placedOrder || ({} as PlacedOrder);
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
					id: i,
					orderItem: orderItem.id,
					project,
					startDate: format(
						new Date(order.createDate),
						'MMM dd, yyyy'
					),
					status: deployment
						? InstallStatus.INSTALLED
						: InstallStatus.READY_TO_INSTALL,
					type: productLicenseType,
				});
			}
		}

		return items;
	}, [order.createDate, order.customFields, orderItems, productLicenseType]);

	return {
		mutateOrder,
		order,
		provisioningTableData,
		resourceRequirements,
	};
};

export default useProvisioningData;
