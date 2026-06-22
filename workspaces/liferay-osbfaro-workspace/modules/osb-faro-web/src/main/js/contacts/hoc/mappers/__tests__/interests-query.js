import {createOrderIOMap} from 'shared/util/pagination';
import {mapCardPropsToOptions, mapPropsToOptions} from '../interests-query';

const mockProps = {
	channelId: '321321',
	delta: 5,
	id: '123123',
	orderIOMap: createOrderIOMap('default Test', 'DESC'),
	page: 2,
	query: 'test query'
};

describe('Interests Query Mapper', () => {
	describe('mapCardPropsToOptions', () => {
		it('should map interests list query card props to options', () => {
			const id = '123';

			expect(mapCardPropsToOptions({id})).toEqual(
				expect.objectContaining({
					variables: {
						active: true,
						id,
						size: 5,
						sort: {
							column: 'count',
							type: 'DESC'
						},
						start: 0
					}
				})
			);
		});
	});

	describe('mapPropsToOptions', () => {
		it('should map interests list query props to options', () => {
			const {delta, id, query} = mockProps;

			expect(mapPropsToOptions(mockProps)).toEqual(
				expect.objectContaining({
					variables: {
						active: true,
						channelId: '321321',
						id,
						keywords: query,
						size: delta,
						sort: {
							column: 'default Test',
							type: 'DESC'
						},
						start: 5
					}
				})
			);
		});
	});
});
