import {fetchCard} from '../cards';
import {isFSA} from 'flux-standard-action';

describe('Card Actions', () => {
	describe('fetchCard', () => {
		it('should return an action', () => {
			const action = fetchCard({
				contactsCardTemplateId: 'test',
				contactsEntityId: '123',
				contactsEntityType: 1
			});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});
});
