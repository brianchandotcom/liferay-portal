/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PRODUCT_TYPES} from '../../../../../../../routes/customer-portal/utils/constants/productTypes';

import {
	AnalyticsIcon,
	CommerceIcon,
	DXPIcon,
	EnterpriseIcon,
	PortalIcon,
} from '../../../../../../icons/navigation-menu';

export const LOGO_PATH_TYPES = {
	[PRODUCT_TYPES.analyticsCloud]: AnalyticsIcon,
	[PRODUCT_TYPES.commerce]: CommerceIcon,
	[PRODUCT_TYPES.commerceCloud]: CommerceIcon,
	[PRODUCT_TYPES.dxp]: DXPIcon,
	[PRODUCT_TYPES.enterpriseSearch]: EnterpriseIcon,
	[PRODUCT_TYPES.dxpCloud]: DXPIcon,
	[PRODUCT_TYPES.liferayExperienceCloud]: DXPIcon,
	[PRODUCT_TYPES.portal]: PortalIcon,
	[PRODUCT_TYPES.partnership]: PortalIcon,
	[PRODUCT_TYPES.socialOffice]: PortalIcon,
	[PRODUCT_TYPES.other]: PortalIcon,
};
