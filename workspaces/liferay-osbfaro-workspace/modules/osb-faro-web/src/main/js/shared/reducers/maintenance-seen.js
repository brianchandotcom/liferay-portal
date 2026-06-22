import {actionTypes} from '../actions/maintenance-seen';
import {createReducer} from 'redux-toolbox';
import {Map} from 'immutable';

const actionHandlers = {
	[actionTypes.SET_MAINTENANCE_SEEN]: (state, {payload}) => {
		const {currentUserId, groupId, stateStartDate} = payload;

		return state.set(`${groupId}-${currentUserId}`, stateStartDate);
	}
};

export default createReducer(new Map(), actionHandlers);
