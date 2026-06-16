import * as actions from '../modals';
import {isFSA} from 'flux-standard-action';

describe('Modal Actions', () => {
	describe('open', () => {
		it('should return an action', () => {
			const action = actions.open();

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe(actions.actionTypes.OPEN_MODAL);
		});

		it('should contain modal type and props', () => {
			const action = actions.open('Foo', {hidden: true});

			expect(action.payload.props.hidden).toBe(true);
			expect(action.payload.type).toBe('Foo');
		});
	});

	describe('close', () => {
		it('should return an action', () => {
			const action = actions.close();

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe(actions.actionTypes.CLOSE_MODAL);
		});
	});

	describe('closeAll', () => {
		it('should return an action', () => {
			const action = actions.closeAll();

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe(actions.actionTypes.CLOSE_ALL_MODALS);
		});
	});
});
