/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ProductType} from '../../../../../../../enums/ProductType';
import i18n from '../../../../../../../i18n';

type ProductTypeOption = {
	description: string;
	label: string;
	value: ProductType;
};

export const ProductTypeOptions: ProductTypeOption[] = [
	{
		description:
			'Backend client extensions delivered as deployed services (only available to SaaS and PaaS clients).',
		label: i18n.translate('cloud-app'),
		value: ProductType.CLOUD,
	},
	{
		description:
			'Module-based apps delivered as .lpkg files that the user can install to modify native Liferay behavior.',
		label: i18n.translate('dxp-app'),
		value: ProductType.DXP,
	},
];
