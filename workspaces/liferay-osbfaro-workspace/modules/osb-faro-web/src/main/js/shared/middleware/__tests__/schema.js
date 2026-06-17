import {
	dataSource,
	getDistributionSchema,
	getLayoutSchema,
	individual,
	individuals,
	segment,
	segments,
} from 'shared/middleware/schema';
import {EntityTypes} from 'shared/util/constants';
import {
	mockIndividual,
	mockLayout,
	mockLiferayDataSource,
	mockSegment,
} from 'test/data';
import {normalize} from 'normalizr';

describe('Schema', () => {
	it('should normalize an individual', () => {
		const action = mockIndividual('foo');

		const result = normalize(action, individual);

		expect(result).toMatchObject({
			entities: {
				individuals: {
					foo: {
						data: expect.objectContaining({
							id: 'foo',
							name: 'Foo Bar',
						}),
					},
				},
			},
			result: 'foo',
		});
	});

	it('should normalize an array of individuals', () => {
		const action = [mockIndividual('foo'), mockIndividual('bar')];

		const result = normalize(action, individuals);

		expect(result).toMatchObject({
			entities: {
				individuals: {
					bar: {
						data: expect.objectContaining({id: 'bar'}),
					},
					foo: {
						data: expect.objectContaining({id: 'foo'}),
					},
				},
			},
			result: ['foo', 'bar'],
		});
	});

	it('should normalize a segment', () => {
		const action = mockSegment('foo');

		const result = normalize(action, segment);

		expect(result).toMatchObject({
			entities: {
				segments: {
					foo: {
						data: expect.objectContaining({
							id: 'foo',
						}),
					},
				},
			},
			result: 'foo',
		});
	});

	it('should normalize an array of segments', () => {
		const action = [mockSegment('foo'), mockSegment('bar')];

		const result = normalize(action, segments);

		expect(result).toMatchObject({
			entities: {
				segments: {
					bar: {
						data: expect.objectContaining({id: 'bar'}),
					},
					foo: {
						data: expect.objectContaining({id: 'foo'}),
					},
				},
			},
			result: ['foo', 'bar'],
		});
	});

	it('should normalize a liferay data-source', () => {
		const payload = mockLiferayDataSource(2);

		expect(normalize(payload, dataSource)).toMatchObject({
			entities: {
				dataSources: {
					2: {
						data: payload,
					},
				},
			},
		});
	});

	describe('getLayoutSchema', () => {
		it('should normalize a layout schema with a faroEntity of individual', () => {
			const action = mockLayout(1, mockIndividual());

			const result = normalize(
				action,
				getLayoutSchema(EntityTypes.Individual)
			);

			expect(result).toMatchObject({
				entities: {
					individuals: expect.any(Object),
					layouts: {
						1: {
							data: expect.objectContaining({id: 1}),
						},
					},
				},
				result: expect.objectContaining({
					contactsLayoutTemplate: 1,
					faroEntity: expect.any(String),
				}),
			});
		});

		it('should normalize a layout schema with a faroEntity of segment', () => {
			const action = mockLayout(2);

			const result = normalize(
				action,
				getLayoutSchema(EntityTypes.IndividualsSegment)
			);

			expect(result).toMatchObject({
				entities: {
					layouts: {
						2: {
							data: expect.objectContaining({id: 2}),
						},
					},
					segments: expect.any(Object),
				},
				result: expect.objectContaining({
					contactsLayoutTemplate: 2,
					faroEntity: expect.any(String),
				}),
			});
		});
	});

	describe('getDistributionSchema', () => {
		it('should normalize distribution data', () => {
			const payload = [{count: 2, values: ['foo', 'bar']}];

			expect(normalize(payload, getDistributionSchema(3))).toMatchObject({
				entities: {
					distributions: {
						3: {
							data: payload,
						},
					},
				},
			});
		});
	});
});
