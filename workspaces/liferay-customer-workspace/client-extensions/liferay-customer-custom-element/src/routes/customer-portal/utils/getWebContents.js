/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SLA_TYPES} from '../../../common/utils/constants/slaTypes';
import {
	PRODUCT_TYPES,
	SLA_NAMES,
	WEB_CONTENT_DXP_VERSION_TYPES,
} from './constants';

export function getWebContents(dxpVersion, slaCurrent, subscriptionGroups) {
	const webContents = [];
	const hasProjectSLA = Object.values(SLA_TYPES).some((slaType) =>
		slaCurrent?.includes(slaType)
	);

	const allProductsNames = Object.values(PRODUCT_TYPES);

	const allProductsKeys = Object.keys(PRODUCT_TYPES);

	const initialSubscriptions = allProductsKeys.map((productKey) => [
		productKey,
		false,
	]);

	const hasSubscriptionGroup = subscriptionGroups?.reduce(
		(subscriptionGroupsAccumulator, subscriptionGroup) => {
			const currentProductIndex = allProductsNames.findIndex(
				(productName) => productName === subscriptionGroup?.name
			);

			if (currentProductIndex !== -1) {
				const productKey = allProductsKeys[currentProductIndex];
				subscriptionGroupsAccumulator[productKey] = true;
			}

			return subscriptionGroupsAccumulator;
		},
		Object.fromEntries(initialSubscriptions)
	);

	const hasAnalyticsCloudNotActive = subscriptionGroups.find(
		(subscriptionGroup) =>
			subscriptionGroup.name === PRODUCT_TYPES.analyticsCloud &&
			(subscriptionGroup.activationStatus === 'In-Progress' ||
				!subscriptionGroup.activationStatus)
	);

	const hasPortalOrPartnershipNotActive = subscriptionGroups.find(
		(subscriptionGroup) =>
			subscriptionGroup.name === PRODUCT_TYPES.portal ||
			(subscriptionGroup.name === PRODUCT_TYPES.partnership &&
				(subscriptionGroup.activationStatus === 'In-Progress' ||
					!subscriptionGroup.activationStatus))
	);
	const hasDXPOrDXPCloudActive = subscriptionGroups.find(
		(subscriptionGroup) =>
			subscriptionGroup.name === PRODUCT_TYPES.dxp ||
			(subscriptionGroup.name === PRODUCT_TYPES.dxpCloud &&
				subscriptionGroup.activationStatus === 'Active')
	);

	const hasAccessToActivateAnalyticsCloudContent =
		(hasAnalyticsCloudNotActive && hasPortalOrPartnershipNotActive) ||
		(hasAnalyticsCloudNotActive && hasDXPOrDXPCloudActive) ||
		hasAnalyticsCloudNotActive;

	const hasAccessToSourceCodeContent =
		hasSubscriptionGroup.partnership ||
		hasSubscriptionGroup.dxpCloud ||
		hasSubscriptionGroup.dxp;

	const hasAccessToEnvironmentDetailContent =
		hasSubscriptionGroup.dxp ||
		hasSubscriptionGroup.portal ||
		hasSubscriptionGroup.commerce ||
		!(hasSubscriptionGroup.partnership || hasSubscriptionGroup.dxpCloud);

	if (hasProjectSLA) {
		webContents.push('WEB-CONTENT-ACTION-01');
	}
	if (
		!hasSubscriptionGroup.partnership &&
		slaCurrent !== SLA_NAMES.limitedSubscription
	) {
		webContents.push('WEB-CONTENT-ACTION-02');
	}
	if (hasAccessToSourceCodeContent) {
		webContents.push('WEB-CONTENT-ACTION-03');
	}
	if (hasSubscriptionGroup.dxp || hasSubscriptionGroup.dxpCloud) {
		webContents.push(
			dxpVersion
				? WEB_CONTENT_DXP_VERSION_TYPES[dxpVersion]
				: WEB_CONTENT_DXP_VERSION_TYPES['7.4']
		);
	}

	if (hasAccessToActivateAnalyticsCloudContent) {
		webContents.push('WEB-CONTENT-ACTION-09');
	}
	if (hasAccessToEnvironmentDetailContent) {
		webContents.push('WEB-CONTENT-ACTION-10');
	}

	return webContents;
}
