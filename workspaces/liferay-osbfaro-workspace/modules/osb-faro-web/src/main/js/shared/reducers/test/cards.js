/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map} from 'immutable';
import {actionTypes} from '~/shared/actions/cards';
import {actionTypes as layoutActionTypes} from '~/shared/actions/layouts';
import {getLayoutSchema} from '~/shared/middleware/schema';
import {EntityTypes} from '~/shared/util/constants';

import reducer from '../cards';

describe('Card Reducer', () => {
	it('adds card on fetch card success', () => {
		const cardId = 'cardId';
		const foo = 'foo';
		const id = 'testId';
		const type = EntityTypes.Individual;

		const action = {
			meta: {
				contactsEntityId: id,
				schema: getLayoutSchema(type),
				type,
			},
			payload: {
				entities: {},
				result: {contactsCardData: {foo}, contactsCardTemplate: cardId},
			},
			type: actionTypes.FETCH_CARD_SUCCESS,
		};

		const state = reducer(new Map(), action);

		expect(state.getIn([id, cardId, foo])).toBe(foo);
	});

	it('adds cards on fetch layouts success', () => {
		const cardId = 'cardId';
		const cardId1 = 'cardId1';
		const foo = 'foo';
		const id = 'testId';
		const type = EntityTypes.Individual;

		const action = {
			meta: {
				contactsEntityId: id,
				schema: getLayoutSchema(type),
				type,
			},
			payload: {
				entities: {},
				result: {contactsCardData: {cardId: {foo}, cardId1: {foo}}},
			},
			type: layoutActionTypes.FETCH_LAYOUT_SUCCESS,
		};

		const state = reducer(new Map(), action);

		const cardIMap = state.get(id);

		expect(cardIMap.getIn([cardId, foo])).toBe(foo);
		expect(cardIMap.getIn([cardId1, foo])).toBe(foo);
	});
});
