/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {INDIVIDUALS, Routes, toRoute} from '~/shared/util/router';

import {fetchIndividual} from '../actions/individuals';
import withAction from './WithAction';

export default withAction(
	({channelId, groupId, id}) =>
		fetchIndividual({channelId, groupId, individualId: id}),
	(state, {id}) => state.getIn(['individuals', id]),
	{
		errorPageProps: ({channelId, groupId}) => ({
			href: toRoute(Routes.CONTACTS_LIST_ENTITY, {
				channelId,
				groupId,
				type: INDIVIDUALS,
			}),
			linkLabel: Liferay.Language.get('go-to-individuals'),
			message: Liferay.Language.get(
				'the-individual-you-are-looking-for-does-not-exist'
			),
			subtitle: Liferay.Language.get('individual-not-found'),
		}),
		propName: 'individual',
	}
);
