import {fetchIndividual} from '../individuals';
import {isFSA} from 'flux-standard-action';

describe('Individuals', () => {
	describe('fetchIndividual', () => {
		it('should return an action', () => {
			const action = fetchIndividual('123');

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});
});
