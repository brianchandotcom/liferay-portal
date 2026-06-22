import {handleError, handleLoading} from '../redux';
import {Map} from 'immutable';
import {RemoteData} from '../records';

describe('redux', () => {
	describe('handleLoading', () => {
		it('should set loading on RemoteData record', () => {
			expect(handleLoading(new Map(), {payload: {id: 23}})).toEqual(
				new Map().set(23, new RemoteData({loading: true}))
			);
		});
	});

	describe('handleError', () => {
		it('should set error on RemoteData record', () => {
			expect(handleError(new Map(), {payload: {id: 23}})).toEqual(
				new Map().set(23, new RemoteData({error: true, loading: false}))
			);
		});
	});
});
