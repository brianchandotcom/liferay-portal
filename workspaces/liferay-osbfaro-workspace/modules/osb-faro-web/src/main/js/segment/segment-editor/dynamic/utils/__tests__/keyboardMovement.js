import {applyMoveTarget, getMoveTargets} from '../keyboardMovement';
import {Conjunctions, PropertyTypes} from '../constants';

const row = (rowId) => ({
	propertyName: rowId,
	rowId,
	type: PropertyTypes.Event,
});

const criteria = {
	conjunctionName: Conjunctions.And,
	criteriaGroupId: 'root',
	items: [
		row('row_a'),
		{
			conjunctionName: Conjunctions.Or,
			criteriaGroupId: 'nested',
			items: [row('row_b')],
		},
	],
};

const criterion = {
	defaultValue: 'default',
	propertyName: 'click',
	type: PropertyTypes.Event,
};

const describeTargets = (targets) =>
	targets.map(
		({groupId, index, node, position}) =>
			`${groupId}:${index}:${node.rowId ?? node.criteriaGroupId}:${position}`
	);

describe('getMoveTargets', () => {
	it('returns no targets for an empty canvas', () => {
		expect(getMoveTargets(null, false)).toEqual([]);
	});

	it('lists every drop position in reading order', () => {
		expect(describeTargets(getMoveTargets(criteria, false))).toEqual([
			'root:0:row_a:top',
			'root:0:row_a:middle',
			'root:1:nested:top',
			'nested:0:row_b:top',
			'nested:0:row_b:middle',
			'nested:1:row_b:bottom',
			'root:2:nested:bottom',
		]);
	});

	it('does not group rows inside nested groups in sequential mode', () => {
		expect(describeTargets(getMoveTargets(criteria, true))).not.toContain(
			'nested:0:row_b:middle'
		);
	});
});

describe('applyMoveTarget', () => {
	it('inserts the criterion at the targeted position', () => {
		const newCriteria = applyMoveTarget(
			criteria,
			{
				groupId: 'nested',
				index: 1,
				node: row('row_b'),
				position: 'bottom',
			},
			criterion,
			false
		);

		expect(
			newCriteria.items[1].items.map(({propertyName}) => propertyName)
		).toEqual(['row_b', 'click']);
		expect(criteria.items[1].items).toHaveLength(1);
	});

	it('groups the criterion with the targeted row', () => {
		const newCriteria = applyMoveTarget(
			criteria,
			{groupId: 'root', index: 0, node: row('row_a'), position: 'middle'},
			criterion,
			false
		);

		const group = newCriteria.items[0];

		expect(group.conjunctionName).toBe(Conjunctions.And);
		expect(group.items.map(({propertyName}) => propertyName)).toEqual([
			'row_a',
			'click',
		]);
	});

	it('groups with OR in sequential mode', () => {
		const newCriteria = applyMoveTarget(
			criteria,
			{groupId: 'root', index: 0, node: row('row_a'), position: 'middle'},
			criterion,
			true
		);

		expect(newCriteria.items[0].conjunctionName).toBe(Conjunctions.Or);
	});
});
