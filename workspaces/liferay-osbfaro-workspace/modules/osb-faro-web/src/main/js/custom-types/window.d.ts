/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {compose} from 'redux';
import {
	ActivityActions,
	Applications,
	ChannelPermissionTypes,
	DataSourceDisplayStatuses,
	DataSourceProgressStatuses,
	DataSourceStatuses,
	DataSourceTypes,
	EntityTypes,
	FieldContexts,
	FieldOwnerTypes,
	FieldTypes,
	PreferencesScopes,
	ProjectStates,
	SegmentStates,
	SegmentTypes,
	SubscriptionStatuses,
	TimeIntervals,
	UserRoleNames,
	UserStatuses,
} from '~/shared/util/constants';

export {};

declare global {
	interface Window {
		__REDUX_DEVTOOLS_EXTENSION_COMPOSE__: compose;
		faroConstants: {
			activityActions: {[key: string]: ActivityActions};
			applications: {[key: string]: Applications};
			channelPermissionTypes: {[key: string]: ChannelPermissionTypes};
			dataSourceDisplayStatuses: {
				[key: string]: DataSourceDisplayStatuses;
			};
			dataSourceProgressStatuses: {
				[key: string]: DataSourceProgressStatuses;
			};
			dataSourceStatuses: {[key: string]: DataSourceStatuses};
			dataSourceTypes: {[key: string]: DataSourceTypes};
			entityTypes: {[key: string]: EntityTypes};
			faroURL: string;
			fieldContexts: {[key: string]: FieldContexts};
			fieldOwnerTypes: {[key: string]: FieldOwnerTypes};
			fieldTypes: {[key: string]: FieldTypes};
			locale: string;
			pagination: {
				cur: number;
				delta: number;
				deltaValues: number[];
				orderAscending: string;
				orderDefault: string;
				orderDescending: string;
			};
			pathThemeRoot: string;
			portletNamespace: string;
			preferencesScopes: {[key: string]: PreferencesScopes};
			projectLocations: {[key: string]: string};
			projectStates: {[key: string]: ProjectStates};
			segmentStates: {[key: string]: SegmentStates};
			segmentTypes: {[key: string]: SegmentTypes};
			subscriptionStatuses: {[key: string]: SubscriptionStatuses};
			timeIntervals: {[key: string]: TimeIntervals};
			userName: string;
			userRoleNames: {[key: string]: UserRoleNames};
			userStatuses: {[key: string]: UserStatuses};
		};
		hbspt: {
			forms: {
				create: Function;
			};
		};
		jQuery: object;
	}
}
