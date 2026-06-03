import {
	configureProject,
	createProject,
	createTrialProject,
	fetchProject,
	fetchProjects,
	fetchProjectViaCorpProjectUuid,
	updateProject
} from '../projects';
import {isFSA} from 'flux-standard-action';

describe('Projects Actions', () => {
	describe('createProject', () => {
		it('should return an action', () => {
			const action = createProject();

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('configureProject', () => {
		it('should return an action', () => {
			const action = configureProject({
				emailAddressDomains: [],
				friendlyURL: 'ggwp',
				groupId: '123',
				name: 'Configure'
			});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('createTrialProject', () => {
		it('should return an action', () => {
			const action = createTrialProject();

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('fetchProject', () => {
		it('should return an action', () => {
			const action = fetchProject({groupId: '23'});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('fetchProjects', () => {
		it('should return an action', () => {
			const action = fetchProjects();

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('fetchProjectViaCorpProjectUuid', () => {
		it('should return an action', () => {
			const action = fetchProjectViaCorpProjectUuid({
				corpProjectUuid: '23-44'
			});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('updateProject', () => {
		it('should return an action', () => {
			const action = updateProject({
				emailAddressDomains: [],
				friendlyURL: 'bananas',
				groupId: '23',
				name: 'Test Test'
			});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});
});
