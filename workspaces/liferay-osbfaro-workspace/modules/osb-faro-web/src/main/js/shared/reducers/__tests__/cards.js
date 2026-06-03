import reducer from '../cards';
import {actionTypes} from 'shared/actions/cards';
import {EntityTypes} from 'shared/util/constants';
import {getLayoutSchema} from 'shared/middleware/schema';
import {actionTypes as layoutActionTypes} from 'shared/actions/layouts';
import {Map} from 'immutable';

describe('Card Reducer', () => {
	it('should add card on fetch card success', () => {
		const cardId = 'cardId';
		const foo = 'foo';
		const id = 'testId';
		const type = EntityTypes.Individual;

		const action = {
			meta: {
				contactsEntityId: id,
				schema: getLayoutSchema(type),
				type
			},
			payload: {
				entities: {},
				result: {contactsCardData: {foo}, contactsCardTemplate: cardId}
			},
			type: actionTypes.FETCH_CARD_SUCCESS
		};

		const state = reducer(new Map(), action);

		expect(state.getIn([id, cardId, foo])).toBe(foo);
	});

	it('should add cards on fetch layouts success', () => {
		const cardId = 'cardId';
		const cardId1 = 'cardId1';
		const foo = 'foo';
		const id = 'testId';
		const type = EntityTypes.Individual;

		const action = {
			meta: {
				contactsEntityId: id,
				schema: getLayoutSchema(type),
				type
			},
			payload: {
				entities: {},
				result: {contactsCardData: {cardId: {foo}, cardId1: {foo}}}
			},
			type: layoutActionTypes.FETCH_LAYOUT_SUCCESS
		};

		const state = reducer(new Map(), action);

		const cardIMap = state.get(id);

		expect(cardIMap.getIn([cardId, foo])).toBe(foo);
		expect(cardIMap.getIn([cardId1, foo])).toBe(foo);
	});
});
