/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import BasePage from '~/settings/components/base-page/BasePage';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import {getDefinitions} from '~/shared/util/breadcrumbs';

import TrackedBehaviorsList from '../hocs/TrackedBehaviorsList';

interface ITrackedBehaviorsProps {
	groupId: string;
}

export const TrackedBehaviors = function TrackedBehaviors({
	groupId,
}: ITrackedBehaviorsProps) {
	const currentUser = useCurrentUser();

	return (
		<BasePage
			breadcrumbItems={[
				getDefinitions({groupId}),
				{active: true, label: Liferay.Language.get('behaviors')},
			]}
			pageDescription={Liferay.Language.get(
				'this-is-the-data-model-of-behaviors-tracked-within-analytics-cloud.-instructions-for-tracking-third-party-assets-in-analytics-cloud-are-provided-when-you-click-each-behavior.-click-and-conversion-goals-can-be-tracked-using-click-events'
			)}
			pageTitle={Liferay.Language.get('tracked-behaviors')}
		>
			<TrackedBehaviorsList authorized={currentUser.isAdmin()} />
		</BasePage>
	);
};

export default TrackedBehaviors;
