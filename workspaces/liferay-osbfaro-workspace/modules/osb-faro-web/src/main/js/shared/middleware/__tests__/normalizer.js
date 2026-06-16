import normalizer from '../normalizer';
import {schema} from 'normalizr';

describe('Normalizer Middleware', () => {
	it('Should normalize a response with a schema', () => {
		const id = '123';
		const name = 'foo';
		const account = {id, name};

		const action = {
			meta: {schema: new schema.Entity('account')},
			payload: account,
			type: 'SUCCESS'
		};

		const normalized = normalizer()(val => val)(action);

		const {entities, result} = normalized.payload;

		const entity = entities.account[id];

		expect(result).toEqual(id);
		expect(entity.id).toBe(id);
		expect(entity.name).toBe(name);
	});
});
