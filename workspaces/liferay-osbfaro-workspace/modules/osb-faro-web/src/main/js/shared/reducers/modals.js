import {actionTypes} from '../actions/modals';
import {createReducer} from 'redux-toolbox';
import {List, Map} from 'immutable';

export default createReducer(new List(), {
	[actionTypes.CLOSE_ALL_MODALS](state) {
		return state.clear();
	},

	[actionTypes.CLOSE_MODAL](state) {
		return state.pop();
	},

	[actionTypes.OPEN_MODAL](state, action) {
		const {closeOnBlur, props, type} = action.payload;

		return state.push(
			new Map({
				closeOnBlur,
				props: new Map(props),
				type
			})
		);
	}
});
