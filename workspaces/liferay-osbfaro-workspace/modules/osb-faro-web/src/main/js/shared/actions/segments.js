/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createActionTypes} from 'redux-toolbox';
import * as API from '~/shared/api';

import {CALL_API} from '../middleware/api';
import {segment} from '../middleware/schema';

export const actionTypes = {
	...createActionTypes('fetch', 'segment'),
};

export function fetchSegment(data) {
	return {
		meta: {
			[CALL_API]: {
				data,
				requestFn: API.individualSegment.fetch,
				schema: segment,
				types: [
					actionTypes.FETCH_SEGMENT_REQUEST,
					actionTypes.FETCH_SEGMENT_SUCCESS,
					actionTypes.FETCH_SEGMENT_FAILURE,
				],
			},
		},
		payload: {
			id: data.segmentId,
		},
		type: 'NO_OP',
	};
}
