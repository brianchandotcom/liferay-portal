import {addCriterionAtIndex} from '../utils';
import {Conjunctions, PropertyTypes} from '../constants';

const criterion = {
	defaultValue: 'default',
	propertyName: 'click',
	touched: false,
	type: PropertyTypes.Event,
	valid: true,
};

describe('addCriterionAtIndex', () => {
	it('creates an AND group when there are no criteria yet', () => {
		const group = addCriterionAtIndex(null, 0, criterion);

		expect(group.conjunctionName).toBe(Conjunctions.And);
		expect(group.criteriaGroupId).toMatch(/^group_/);
		expect(group.items).toHaveLength(1);
		expect(group.items[0]).toMatchObject({
			propertyName: 'click',
			type: PropertyTypes.Event,
			value: 'default',
		});
		expect(group.items[0].rowId).toMatch(/^row_/);
	});

	it('inserts the criterion at the given index of an existing group', () => {
		const existing = {
			conjunctionName: Conjunctions.Or,
			criteriaGroupId: 'group_1',
			items: [{rowId: 'row_a'}, {rowId: 'row_b'}],
		};

		const group = addCriterionAtIndex(existing, 2, criterion);

		expect(group.conjunctionName).toBe(Conjunctions.Or);
		expect(group.criteriaGroupId).toBe('group_1');
		expect(
			group.items.map(({propertyName, rowId}) => propertyName ?? rowId)
		).toEqual(['row_a', 'row_b', 'click']);
		expect(existing.items).toHaveLength(2);
	});

	it('keeps a valid value over the default value', () => {
		const group = addCriterionAtIndex(null, 0, {
			...criterion,
			value: 'chosen',
		});

		expect(group.items[0].value).toBe('chosen');
	});
});
