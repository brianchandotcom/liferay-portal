import {actionTypes} from '../actions/cards';
import {createReducer} from 'redux-toolbox';
import {fromJS, Map} from 'immutable';
import {actionTypes as layoutActionTypes} from '../actions/layouts';

const actionHandlers = {
	[actionTypes.FETCH_CARD_SUCCESS]: (state, action) => {
		const {
			meta: {contactsEntityId},
			payload: {
				result: {contactsCardData, contactsCardTemplate}
			}
		} = action;

		return state.mergeIn(
			[contactsEntityId],
			fromJS({[contactsCardTemplate]: contactsCardData})
		);
	},
	[layoutActionTypes.FETCH_LAYOUT_SUCCESS]: (state, {meta, payload}) =>
		state.mergeIn(
			[meta.contactsEntityId],
			fromJS(payload.result.contactsCardData)
		)
};

export default createReducer(new Map(), actionHandlers);
