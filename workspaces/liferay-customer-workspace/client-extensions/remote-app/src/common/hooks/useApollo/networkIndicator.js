/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ActionTypes} from 'react-apollo-network-status';
import isOperationType from '../../utils/isOperationType';

const initialState = {
	error: undefined,
	success: undefined,
};

const reducer = (state, action) => {
	if (isOperationType(action.payload.operation, 'subscription')) {
		return state;
	}

	switch (action.type) {
		case ActionTypes.ERROR: {
			const {networkError, operation} = action.payload;

			return {
				error: {networkError, operation},
			};
		}
		case ActionTypes.SUCCESS: {
			const {operation, result} = action.payload;

			if (result) {
				if (result.errors) {
					return {
						error: {
							operation,
							response: result.errors.map(
								(error) => error.extensions
							),
						},
					};
				}

				return {
					success: {operation, response: result},
				};
			}

			return state;
		}
		default: {
			return state;
		}
	}
};

export const networkIndicator = {
	initialState,
	reducer,
};
