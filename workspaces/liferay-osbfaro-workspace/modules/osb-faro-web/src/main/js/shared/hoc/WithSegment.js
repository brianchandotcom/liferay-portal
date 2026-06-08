/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Routes, SEGMENTS, toRoute} from '~/shared/util/router';

import {fetchSegment} from '../actions/segments';
import withAction from './WithAction';

export const withSegment = function withSegment(
	includeReferencedObjects = false
) {
	return withAction(
		({groupId, id}) =>
			fetchSegment({groupId, includeReferencedObjects, segmentId: id}),
		(state, {id}) => state.getIn(['segments', id]),
		{
			errorPageProps: ({channelId, groupId}) => ({
				href: toRoute(Routes.CONTACTS_LIST_ENTITY, {
					channelId,
					groupId,
					type: SEGMENTS,
				}),
				linkLabel: Liferay.Language.get('go-to-segments'),
				message: Liferay.Language.get(
					'the-segment-you-are-looking-for-does-not-exist'
				),
				subtitle: Liferay.Language.get('segment-not-found'),
			}),
			propName: 'segment',
		}
	);
};

export default withSegment();
