/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PreferencesScopes} from '~/shared/util/constants';

import {fetchDefaultChannelId} from '../actions/preferences';
import {RemoteData} from '../util/records';
import withAction from './WithAction';

export default withAction(
	({groupId}) => fetchDefaultChannelId(groupId),

	(state) =>
		state.getIn(
			['preferences', PreferencesScopes.User, 'defaultChannelId'],
			new RemoteData()
		),
	{propName: 'defaultChannelId'}
);
