/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	EXPERIENCE_SUBSCRIPTIONS,
	PAAS_EXPERIENCE,
	PLAN_SUBSCRIPTIONS,
} from '~/utils/constants';
import {IAccountSubscription} from '~/utils/types';

export function hasExperienceSubscription(
	subscriptions: IAccountSubscription[] | undefined
): boolean {
	if (!subscriptions) {
		return false;
	}

	return subscriptions.some(
		({name}) => name && EXPERIENCE_SUBSCRIPTIONS.includes(name.trim())
	);
}

export function hasPaaSExperienceSubscription(
	subscriptions: IAccountSubscription[] | undefined
): boolean {
	if (!subscriptions) {
		return false;
	}

	return subscriptions.some(({name}) => name?.trim() === PAAS_EXPERIENCE);
}

export function hasPlanSubscription(
	subscriptions: IAccountSubscription[] | undefined
): boolean {
	if (!subscriptions) {
		return false;
	}

	return subscriptions.some(
		({name}) => name && PLAN_SUBSCRIPTIONS.includes(name.trim())
	);
}
