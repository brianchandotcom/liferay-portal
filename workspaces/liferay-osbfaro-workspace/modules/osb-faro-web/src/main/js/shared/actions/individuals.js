/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createActionTypes} from 'redux-toolbox';
import * as API from '~/shared/api';

import {CALL_API} from '../middleware/api';
import {individual} from '../middleware/schema';

export const actionTypes = {
	...createActionTypes('fetch', 'associated_segments'),
	...createActionTypes('fetch', 'individual'),
};

export function fetchIndividual({channelId, groupId, individualId}) {
	return {
		meta: {
			[CALL_API]: {
				data: {channelId, groupId, individualId},
				requestFn: API.individuals.fetch,
				schema: individual,
				types: [
					actionTypes.FETCH_INDIVIDUAL_REQUEST,
					actionTypes.FETCH_INDIVIDUAL_SUCCESS,
					actionTypes.FETCH_INDIVIDUAL_FAILURE,
				],
			},
		},
		payload: {
			id: individualId,
		},
		type: 'NO_OP',
	};
}
