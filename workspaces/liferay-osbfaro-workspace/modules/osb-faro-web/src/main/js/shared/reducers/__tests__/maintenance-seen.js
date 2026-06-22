import * as data from 'test/data';
import reducer from '../maintenance-seen';
import {actionTypes} from '../../actions/maintenance-seen';
import {Map} from 'immutable';

describe('Maintenance Seen Reducer', () => {
	it('should be a function', () => {
		expect(reducer).toBeInstanceOf(Function);
	});

	it(`should handle ${actionTypes.SET_MAINTENANCE_SEEN}`, () => {
		const currentUserId = '23';
		const groupId = '23';
		const stateStartDate = data.getTimestamp();

		const action = {
			payload: {
				currentUserId,
				groupId,
				stateStartDate
			},
			type: actionTypes.SET_MAINTENANCE_SEEN
		};

		const state = reducer(new Map(), action);

		expect(state).toEqual(
			new Map({
				[`${groupId}-${currentUserId}`]: stateStartDate
			})
		);
	});
});
