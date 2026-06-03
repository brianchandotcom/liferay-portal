import reducer from '../sidebar';
import {actionTypes} from '../../actions/sidebar';
import {Map} from 'immutable';

describe('Sidebar Reducer', () => {
	it('should be a function', () => {
		expect(reducer).toBeInstanceOf(Function);
	});

	it(`should handle ${actionTypes.COLLAPSE_SIDEBAR}`, () => {
		const currentUserId = '23';
		const collapsed = true;

		const action = {
			payload: {
				collapsed,
				currentUserId
			},
			type: actionTypes.COLLAPSE_SIDEBAR
		};

		const state = reducer(new Map(), action);

		expect(state).toEqual(
			new Map({
				[currentUserId]: collapsed
			})
		);
	});
});
