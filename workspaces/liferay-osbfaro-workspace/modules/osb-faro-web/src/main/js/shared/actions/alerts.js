import {Alert} from 'shared/types';
import {isNumber, uniqueId} from 'lodash';

export const actionTypes = {
	ADD_ALERT: 'ADD_ALERT',
	REMOVE_ALERT: 'REMOVE_ALERT',
	UPDATE_ALERT: 'UPDATE_ALERT'
};

const DEFAULT_TIMEOUT = 4000;

function removeAfterDelay(action, timeout) {
	if (timeout && !isNumber(timeout)) {
		timeout = DEFAULT_TIMEOUT;
	}

	return dispatch => {
		dispatch(action);

		return setTimeout(
			() => dispatch(removeAlert(action.payload.id)),
			timeout
		);
	};
}

export function addAlert({
	alertType,
	id = uniqueId(),
	message,
	timeout = true
}) {
	const action = {
		payload: {
			alertType,
			id,
			message
		},
		type: actionTypes.ADD_ALERT
	};

	let retVal = action;

	if (timeout && alertType !== Alert.Types.Pending) {
		retVal = removeAfterDelay(action, timeout);
	}

	return retVal;
}

export function updateAlert({alertType, id, message, timeout = true}) {
	const action = {
		payload: {
			alertType,
			id,
			message
		},
		type: actionTypes.UPDATE_ALERT
	};

	let retVal = action;

	if (timeout) {
		retVal = removeAfterDelay(action, timeout);
	}

	return retVal;
}

export function removeAlert(id) {
	return {
		payload: {id},
		type: actionTypes.REMOVE_ALERT
	};
}
