import {fetchSegment} from '../segments';
import {isFSA} from 'flux-standard-action';

describe('Segments', () => {
	describe('fetchSegment', () => {
		it('should return an action', () => {
			const action = fetchSegment('123');

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});
});
