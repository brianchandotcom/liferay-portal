/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Routes, toRoute} from '~/shared/util/router';

import {fetchDataSource} from '../actions/data-sources';
import withAction from './WithAction';

export default withAction(
	({groupId, id}) => fetchDataSource({groupId, id}),
	(state, {id}) => state.getIn(['dataSources', id]),
	{
		errorPageProps: ({groupId}) => ({
			href: toRoute(Routes.SETTINGS_DATA_SOURCE_LIST, {
				groupId,
			}),
			linkLabel: Liferay.Language.get('go-to-data-sources'),
			message: Liferay.Language.get(
				'the-data-source-you-are-looking-for-does-not-exist'
			),
			subtitle: Liferay.Language.get('data-source-not-found'),
		}),
		propName: 'dataSource',
	}
);
