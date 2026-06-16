import {actionTypes} from '../actions/individuals';
import {createReducer} from 'redux-toolbox';
import {handleError, handleLoading} from '../util/redux';
import {Map} from 'immutable';

const actionHandlers = {
	[actionTypes.FETCH_INDIVIDUAL_REQUEST]: handleLoading,
	[actionTypes.FETCH_INDIVIDUAL_FAILURE]: handleError
};

export default createReducer(new Map(), actionHandlers);
