import {
	createLiferayDataSource,
	createSalesforceDataSource,
	deleteDataSource,
	fetchDataSource,
	updateCSVDataSource,
	updateLiferayDataSource,
	updateSalesforceDataSource
} from '../data-sources';
import {isFSA} from 'flux-standard-action';

describe('DataSources', () => {
	describe('createLiferayDataSource', () => {
		it('should return an action', () => {
			const action = createLiferayDataSource({});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('createSalesforceDataSource', () => {
		it('should return an action', () => {
			const action = createSalesforceDataSource({});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('deleteDataSource', () => {
		it('should return an action', () => {
			const action = deleteDataSource({});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('fetchDataSource', () => {
		it('should return an action', () => {
			const action = fetchDataSource({});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('updateCSVDataSource', () => {
		it('should return an action', () => {
			const action = updateCSVDataSource({});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('updateLiferayDataSource', () => {
		it('should return an action', () => {
			const action = updateLiferayDataSource({});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('updateSalesforceDataSource', () => {
		it('should return an action', () => {
			const action = updateSalesforceDataSource({});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});
});
