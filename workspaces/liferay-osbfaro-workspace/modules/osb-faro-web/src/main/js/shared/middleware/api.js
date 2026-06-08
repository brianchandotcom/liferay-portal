/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {get, isNil} from 'lodash';
import sendRequest from '~/shared/util/request';

export const CALL_API = 'CALL_API';

export function toAction(type, ...objs) {
	const action = Object.assign(...objs, {type});

	delete action.meta[CALL_API];

	return action;
}

const Api = function Api() {
	return (next) => (action) => {
		const request = get(action, ['meta', CALL_API]);

		if (isNil(request)) {
			return next(action);
		}

		const {data, requestFn, types} = request;

		const [requestType, successType, failureType] = types;

		next(toAction(requestType, action));

		const retVal = requestFn ? requestFn(data) : sendRequest(request);

		return retVal.then(
			(payload) => {
				next(
					toAction(successType, action, {
						meta: {
							...action.meta,
							schema: request.schema,
						},
						payload,
					})
				);

				return {payload};
			},
			(error) => {
				next(
					toAction(failureType, action, {
						error: true,
					})
				);

				throw error;
			}
		);
	};
};

export default Api;
