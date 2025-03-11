/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ProductType} from '../../../../../../../enums/ProductType';

export const getOfferingTypes = (type: ProductType) => {
	const offeringTypes = [];

	if (type === ProductType.CLOUD) {
		offeringTypes.push('Liferay SaaS');
		offeringTypes.push('Liferay PaaS');

		return offeringTypes;
	}
	if (type === ProductType.DXP) {
		offeringTypes.push('Liferay PaaS');
		offeringTypes.push('Liferay Self-Hosted');

		return offeringTypes;
	}

	return ['Liferay SaaS', 'Liferay PaaS', 'Liferay Self-Hosted'];
};
