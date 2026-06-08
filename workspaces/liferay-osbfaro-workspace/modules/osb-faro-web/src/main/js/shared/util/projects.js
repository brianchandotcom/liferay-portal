/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Routes, toRoute} from '~/shared/util/router';
import {PLANS} from '~/shared/util/subscriptions';

/**
 * Returns only the basic tier Projects from an array of Projects.
 * @param {Array} projects - The array of Projects.
 * @param {boolean} unconfiguredOnly - Filter flag to return only unconfigured basic tier Projects.
 * @returns {Array} - the array of basic tier Projects
 */
export function getBasicProjects(projects, unconfiguredOnly = false) {
	return projects.filter(({faroSubscription, groupId}) => {
		const subscriptionName = faroSubscription.get('name');

		return unconfiguredOnly
			? subscriptionName === PLANS.basic.name && !groupId
			: subscriptionName === PLANS.basic.name;
	});
}

/**
 * Returns the route for a single Project as a string.
 * @param {Record} Project - The Project record.
 * @param {boolean} Project.corpProjectUuid - The corpProjectUuid of the project.
 * @param {number} Project.groupId - The groupId of the project. This value will
 * be 0 if the workspace does not exist.
 * @returns {string} - The route for the project as a string.
 */
export function getSingleProjectRoute({corpProjectUuid, groupId}) {
	return groupId
		? toRoute(Routes.WORKSPACE_WITH_ID, {groupId})
		: toRoute(Routes.WORKSPACE_ADD_WITH_CORP_PROJECT_UUID, {
				corpProjectUuid,
			});
}
