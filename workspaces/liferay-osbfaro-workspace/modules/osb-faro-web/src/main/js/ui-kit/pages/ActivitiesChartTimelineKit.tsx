/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import ActivitiesChartTimelineBase from '~/contacts/components/ActivitiesChartTimeline';
import {EntityTypes, RangeKeyTimeRanges} from '~/shared/util/constants';

const ActivitiesChartTimeline =
	ActivitiesChartTimelineBase as React.ComponentType<any>;

const activityHistory = Array.from({length: 30}, (_, i) => ({
	intervalInitDate: new Date(2019, 0, i + 1).getTime(),
	totalElements: Math.round(Math.random() * 100),
}));

const ActivitiesChartTimelineKit: React.FC = () => (
	<div>
		<ActivitiesChartTimeline
			activitiesLabel="Test label"
			entityType={EntityTypes.Account}
			groupId="23"
			history={activityHistory}
			id="1"
			rangeSelectors={{
				rangeKey: RangeKeyTimeRanges.Last30Days,
			}}
		/>
	</div>
);

export default ActivitiesChartTimelineKit;
