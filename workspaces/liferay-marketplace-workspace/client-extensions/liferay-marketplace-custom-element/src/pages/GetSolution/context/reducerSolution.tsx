/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SolutionInitialStateProps} from './SolutionContext';

enum TYPES {
	UPDATE_COMPANY = 'UPDATE_COMPANY',
	UPDATE_CONTACT_US = 'UPDATE_CONTACT_US',
	UPDATE_CREATE = 'UPDATE_CREATE',
	UPDATE_DETAILS = 'UPDATE_DETAILS',
	UPDATE_HEADER = 'UPDATE_HEADER',
	UPDATE_PROFILE = 'UPDATE_PROFILE',
	UPDATE_SUBMIT = 'UPDATE_SUBMIT',
}

export type TAction = {
	payload?: any;
	type: TYPES;
};

export function solutionReducer(
	state: SolutionInitialStateProps,
	action: TAction
) {
	switch (action.type) {
		case 'UPDATE_CREATE': {
			const {solution} = action.payload.value;

			return {
				...state,
				solution,
			};
		}

		default:
			return state;
	}
}
