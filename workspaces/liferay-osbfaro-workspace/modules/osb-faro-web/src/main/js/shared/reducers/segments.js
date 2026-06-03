import {actionTypes} from '../actions/segments';
import {createReducer} from 'redux-toolbox';
import {handleError, handleLoading} from '../util/redux';
import {Map} from 'immutable';

const actionHandlers = {
	[actionTypes.FETCH_SEGMENT_REQUEST]: handleLoading,
	[actionTypes.FETCH_SEGMENT_FAILURE]: handleError
};

export default createReducer(new Map(), actionHandlers);
