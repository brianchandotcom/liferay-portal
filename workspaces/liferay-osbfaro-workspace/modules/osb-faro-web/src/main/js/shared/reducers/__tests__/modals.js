import reducer from '../modals';
import {actionTypes} from '../../actions/modals';
import {fromJS, List, Map, Set} from 'immutable';

describe('Modals Reducer', () => {
	it('should be a function', () => {
		expect(reducer).toBeInstanceOf(Function);
	});

	it(`should handle ${actionTypes.OPEN_MODAL}`, () => {
		const action = {
			payload: {
				closeOnBlur: true,
				props: {
					foo: 'bar'
				},
				type: 'MyModal'
			},
			type: actionTypes.OPEN_MODAL
		};

		const state = reducer(new List(), action);

		expect(state).toEqual(
			new List([
				new Map({
					closeOnBlur: true,
					props: new Map({
						foo: 'bar'
					}),
					type: 'MyModal'
				})
			])
		);
	});

	it('should not deeply convert modal props', () => {
		const action = {
			payload: {
				closeOnBlur: true,
				name: 'MyModal',
				props: {
					foo: Set.of(1)
				}
			},
			type: actionTypes.OPEN_MODAL
		};

		const state = reducer(new List(), action);

		expect(state.getIn([0, 'props', 'foo'])).toEqual(Set.of(1));
	});

	it(`should handle ${actionTypes.CLOSE_MODAL}`, () => {
		const intitialState = fromJS([
			{
				name: 'Foo',
				props: {}
			},
			{
				name: 'Bar',
				props: {}
			}
		]);

		const action = {
			type: actionTypes.CLOSE_MODAL
		};

		const state = reducer(intitialState, action);

		expect(state).toEqual(
			fromJS([
				{
					name: 'Foo',
					props: {}
				}
			])
		);
	});

	it(`should handle ${actionTypes.CLOSE_ALL_MODALS}`, () => {
		const intitialState = fromJS([
			{
				name: 'Foo',
				props: {}
			},
			{
				name: 'Bar',
				props: {}
			}
		]);

		const action = {
			type: actionTypes.CLOSE_ALL_MODALS
		};

		const state = reducer(intitialState, action);

		expect(state).toEqual(new List());
	});
});
