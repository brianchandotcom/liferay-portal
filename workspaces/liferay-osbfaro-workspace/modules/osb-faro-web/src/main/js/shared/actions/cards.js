/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createActionTypes} from 'redux-toolbox';

import {CALL_API} from '../middleware/api';
import {getCardSchema} from '../middleware/schema';

export const actionTypes = {
	...createActionTypes('fetch', 'card'),
};

export function fetchCard({
	contactsCardTemplateId,
	contactsCardTemplateSettings = {},
	contactsEntityId,
	contactsEntityType,
	groupId,
}) {
	return {
		meta: {
			[CALL_API]: {
				data: {
					contactsCardTemplateId,
					contactsCardTemplateSettings,
					contactsEntityId,
					contactsEntityType,
				},
				method: 'GET',
				path: `contacts/${groupId}/contacts_card`,
				schema: getCardSchema(contactsEntityType),
				types: [
					actionTypes.FETCH_CARD_REQUEST,
					actionTypes.FETCH_CARD_SUCCESS,
					actionTypes.FETCH_CARD_FAILURE,
				],
			},
			contactsEntityId,
			type: contactsEntityType,
		},
		type: 'NO_OP',
	};
}
