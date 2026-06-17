import {CustomFunctionOperators, NotOperators} from '../../utils/constants';
import {extractRemoteCriterionEntries} from '../extract';
import {fromJS} from 'immutable';
import {tagCriterionType} from '../tagCriterionType';
import {vocabularyCriterionType} from '../vocabularyCriterionType';

function makeFilterCriterion({
	idProperty,
	idValue,
	nameProperty,
	nameValue,
	operatorName,
	propertyName,
}) {
	const items = [];

	if (idProperty) {
		items.push({
			operatorName: 'eq',
			propertyName: idProperty,
			value: idValue,
		});
	}

	if (nameProperty) {
		items.push({
			operatorName: 'eq',
			propertyName: nameProperty,
			value: nameValue,
		});
	}

	return {
		operatorName,
		propertyName,
		value: fromJS({
			criterionGroup: {
				conjunctionName: 'and',
				items,
			},
		}),
	};
}

describe('criterion-types/extractRemoteCriterionEntries', () => {
	it('should return an empty array for null or undefined criteria', () => {
		expect(extractRemoteCriterionEntries(null)).toEqual([]);
		expect(extractRemoteCriterionEntries(undefined)).toEqual([]);
	});

	it('should return an empty array for criteria with a non-remote operator', () => {
		const criterion = makeFilterCriterion({
			idProperty: 'name',
			idValue: 'foo',
			operatorName: 'eq',
			propertyName: 'firstName',
		});

		expect(extractRemoteCriterionEntries(criterion)).toEqual([]);
	});

	it('should extract a vocabulary entry when the criterion uses VocabulariesFilter', () => {
		const criterion = makeFilterCriterion({
			idProperty: 'vocabularies/id',
			idValue: 'vocab-id',
			nameProperty: 'vocabularies/name',
			nameValue: 'My Vocabulary',
			operatorName: CustomFunctionOperators.VocabulariesFilter,
			propertyName: 'vocab-id',
		});

		expect(extractRemoteCriterionEntries(criterion)).toEqual([
			{
				criterionType: vocabularyCriterionType,
				id: 'vocab-id',
				name: 'My Vocabulary',
			},
		]);
	});

	it('should extract a vocabulary entry when the criterion uses NotVocabulariesFilter', () => {
		const criterion = makeFilterCriterion({
			idProperty: 'vocabularies/id',
			idValue: 'vocab-id',
			nameProperty: 'vocabularies/name',
			nameValue: 'My Vocabulary',
			operatorName: NotOperators.NotVocabulariesFilter,
			propertyName: 'vocab-id',
		});

		expect(extractRemoteCriterionEntries(criterion)).toEqual([
			{
				criterionType: vocabularyCriterionType,
				id: 'vocab-id',
				name: 'My Vocabulary',
			},
		]);
	});

	it('should extract a tag entry when the criterion uses TagsFilter', () => {
		const criterion = makeFilterCriterion({
			idProperty: 'tags/id',
			idValue: 'tag-id',
			nameProperty: 'tags/name',
			nameValue: 'My Tag',
			operatorName: CustomFunctionOperators.TagsFilter,
			propertyName: 'tag-id',
		});

		expect(extractRemoteCriterionEntries(criterion)).toEqual([
			{criterionType: tagCriterionType, id: 'tag-id', name: 'My Tag'},
		]);
	});

	it('should extract a tag entry when the criterion uses NotTagsFilter', () => {
		const criterion = makeFilterCriterion({
			idProperty: 'tags/id',
			idValue: 'tag-id',
			nameProperty: 'tags/name',
			nameValue: 'My Tag',
			operatorName: NotOperators.NotTagsFilter,
			propertyName: 'tag-id',
		});

		expect(extractRemoteCriterionEntries(criterion)).toEqual([
			{criterionType: tagCriterionType, id: 'tag-id', name: 'My Tag'},
		]);
	});

	it('should fall back to propertyName when the name item is missing', () => {
		const criterion = makeFilterCriterion({
			operatorName: CustomFunctionOperators.VocabulariesFilter,
			propertyName: 'vocab-id-fallback',
		});

		expect(extractRemoteCriterionEntries(criterion)).toEqual([
			{
				criterionType: vocabularyCriterionType,
				id: 'vocab-id-fallback',
				name: 'vocab-id-fallback',
			},
		]);
	});

	it('should recurse into nested groups and return all matching entries', () => {
		const vocab = makeFilterCriterion({
			idProperty: 'vocabularies/id',
			idValue: 'vocab-id',
			nameProperty: 'vocabularies/name',
			nameValue: 'My Vocabulary',
			operatorName: CustomFunctionOperators.VocabulariesFilter,
			propertyName: 'vocab-id',
		});

		const tag = makeFilterCriterion({
			idProperty: 'tags/id',
			idValue: 'tag-id',
			nameProperty: 'tags/name',
			nameValue: 'My Tag',
			operatorName: CustomFunctionOperators.TagsFilter,
			propertyName: 'tag-id',
		});

		const nonRemote = makeFilterCriterion({
			operatorName: 'eq',
			propertyName: 'firstName',
		});

		const mixed = {
			items: [
				vocab,
				{
					items: [tag, nonRemote],
				},
			],
		};

		expect(extractRemoteCriterionEntries(mixed)).toEqual([
			{
				criterionType: vocabularyCriterionType,
				id: 'vocab-id',
				name: 'My Vocabulary',
			},
			{criterionType: tagCriterionType, id: 'tag-id', name: 'My Tag'},
		]);
	});
});
