import * as actions from '../maintenance-seen';
import {isFSA} from 'flux-standard-action';

describe('Maintenance Seen Actions', () => {
	describe('setMaintenanceSeen', () => {
		it('should return an action', () => {
			const action = actions.setMaintenanceSeen();

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe(actions.actionTypes.SET_MAINTENANCE_SEEN);
		});
	});
});
