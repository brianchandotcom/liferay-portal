import {sequence} from '../promise';

describe('sequence', () => {
	it('should resolve with the first truthy error and skip remaining validators', () => {
		expect.assertions(1);

		const errorVal = 'error';
		const secondValidator = jest.fn(() => Promise.resolve('other'));

		const response = sequence([
			() => Promise.resolve(''),
			() => Promise.resolve(errorVal),
			secondValidator
		])();

		return response.then(result => {
			expect(result).toBe(errorVal);
		});
	});

	it('should resolve with a falsy value when all validators pass', () => {
		expect.assertions(1);

		const response = sequence([
			() => Promise.resolve(''),
			() => Promise.resolve('')
		])();

		return expect(response).resolves.toBeFalsy();
	});
});
