import * as API from 'shared/api';
import TagDisplay from 'segment/components/criteria-card/display-components/TagDisplay';
import TagInput from '../../inputs/TagInput';
import VocabularyDisplay from 'segment/components/criteria-card/display-components/VocabularyDisplay';
import VocabularyInput from '../../inputs/VocabularyInput';
import {createTagProperty, createVocabularyProperty} from '../../utils/utils';
import {CustomFunctionOperators, NotOperators} from '../../utils/constants';
import {
	getRemoteCriterionTypeByOperator,
	getRemoteCriterionTypeByPropertyKey,
	isRemoteCriterionOperator,
	REMOTE_CRITERION_TYPES
} from '../registry';
import {tagCriterionType} from '../tagCriterionType';
import {vocabularyCriterionType} from '../vocabularyCriterionType';

describe('criterion-types/registry', () => {
	it('should expose vocabulary and tag in REMOTE_CRITERION_TYPES', () => {
		expect(REMOTE_CRITERION_TYPES).toContain(vocabularyCriterionType);
		expect(REMOTE_CRITERION_TYPES).toContain(tagCriterionType);
		expect(REMOTE_CRITERION_TYPES).toHaveLength(2);
	});

	describe('vocabularyCriterionType', () => {
		it('should advertise vocabulary-specific identifiers and behavior', () => {
			expect(vocabularyCriterionType.propertyKey).toBe('vocabulary');
			expect(vocabularyCriterionType.idProperty).toBe('vocabularies/id');
			expect(vocabularyCriterionType.nameProperty).toBe(
				'vocabularies/name'
			);
			expect(vocabularyCriterionType.supportsCategories).toBe(true);
			expect(vocabularyCriterionType.positiveOperator).toBe(
				CustomFunctionOperators.VocabulariesFilter
			);
			expect(vocabularyCriterionType.negativeOperator).toBe(
				NotOperators.NotVocabulariesFilter
			);
			expect(vocabularyCriterionType.operators.size).toBe(2);
			expect(
				vocabularyCriterionType.operators.has(
					CustomFunctionOperators.VocabulariesFilter
				)
			).toBe(true);
			expect(
				vocabularyCriterionType.operators.has(
					NotOperators.NotVocabulariesFilter
				)
			).toBe(true);
		});

		it('should wire the Vocabulary Input and Display components', () => {
			expect(vocabularyCriterionType.InputComponent).toBe(
				VocabularyInput
			);
			expect(vocabularyCriterionType.DisplayComponent).toBe(
				VocabularyDisplay
			);
		});

		it('should wire the Vocabulary API and property factory', () => {
			expect(vocabularyCriterionType.api).toBe(API.vocabularies.search);
			expect(vocabularyCriterionType.createProperty).toBe(
				createVocabularyProperty
			);
		});
	});

	describe('tagCriterionType', () => {
		it('should advertise tag-specific identifiers and behavior', () => {
			expect(tagCriterionType.propertyKey).toBe('tag');
			expect(tagCriterionType.idProperty).toBe('tags/id');
			expect(tagCriterionType.nameProperty).toBe('tags/name');
			expect(tagCriterionType.supportsCategories).toBe(false);
			expect(tagCriterionType.positiveOperator).toBe(
				CustomFunctionOperators.TagsFilter
			);
			expect(tagCriterionType.negativeOperator).toBe(
				NotOperators.NotTagsFilter
			);
			expect(tagCriterionType.operators.size).toBe(2);
			expect(
				tagCriterionType.operators.has(
					CustomFunctionOperators.TagsFilter
				)
			).toBe(true);
			expect(
				tagCriterionType.operators.has(NotOperators.NotTagsFilter)
			).toBe(true);
		});

		it('should wire the Tag Input and Display components', () => {
			expect(tagCriterionType.InputComponent).toBe(TagInput);
			expect(tagCriterionType.DisplayComponent).toBe(TagDisplay);
		});

		it('should wire the Tag API and property factory', () => {
			expect(tagCriterionType.api).toBe(API.tags.search);
			expect(tagCriterionType.createProperty).toBe(createTagProperty);
		});
	});

	describe('getRemoteCriterionTypeByPropertyKey', () => {
		it('should return the vocabulary type for "vocabulary"', () => {
			expect(getRemoteCriterionTypeByPropertyKey('vocabulary')).toBe(
				vocabularyCriterionType
			);
		});

		it('should return the tag type for "tag"', () => {
			expect(getRemoteCriterionTypeByPropertyKey('tag')).toBe(
				tagCriterionType
			);
		});

		it('should return undefined for any other key', () => {
			expect(getRemoteCriterionTypeByPropertyKey('individual')).toBe(
				undefined
			);
			expect(getRemoteCriterionTypeByPropertyKey('')).toBe(undefined);
			expect(getRemoteCriterionTypeByPropertyKey(null)).toBe(undefined);
			expect(getRemoteCriterionTypeByPropertyKey(undefined)).toBe(
				undefined
			);
		});
	});

	describe('getRemoteCriterionTypeByOperator', () => {
		it('should return vocabulary for both vocabulary operators', () => {
			expect(
				getRemoteCriterionTypeByOperator(
					CustomFunctionOperators.VocabulariesFilter
				)
			).toBe(vocabularyCriterionType);
			expect(
				getRemoteCriterionTypeByOperator(
					NotOperators.NotVocabulariesFilter
				)
			).toBe(vocabularyCriterionType);
		});

		it('should return tag for both tag operators', () => {
			expect(
				getRemoteCriterionTypeByOperator(
					CustomFunctionOperators.TagsFilter
				)
			).toBe(tagCriterionType);
			expect(
				getRemoteCriterionTypeByOperator(NotOperators.NotTagsFilter)
			).toBe(tagCriterionType);
		});

		it('should return undefined for any other operator', () => {
			expect(
				getRemoteCriterionTypeByOperator(
					CustomFunctionOperators.AccountsFilter
				)
			).toBe(undefined);
			expect(getRemoteCriterionTypeByOperator('eq')).toBe(undefined);
			expect(getRemoteCriterionTypeByOperator('')).toBe(undefined);
			expect(getRemoteCriterionTypeByOperator(null)).toBe(undefined);
			expect(getRemoteCriterionTypeByOperator(undefined)).toBe(undefined);
		});
	});

	describe('isRemoteCriterionOperator', () => {
		it('should return true for all remote operators', () => {
			expect(
				isRemoteCriterionOperator(
					CustomFunctionOperators.VocabulariesFilter
				)
			).toBe(true);
			expect(
				isRemoteCriterionOperator(NotOperators.NotVocabulariesFilter)
			).toBe(true);
			expect(
				isRemoteCriterionOperator(CustomFunctionOperators.TagsFilter)
			).toBe(true);
			expect(isRemoteCriterionOperator(NotOperators.NotTagsFilter)).toBe(
				true
			);
		});

		it('should return false for non-remote operators and empty inputs', () => {
			expect(
				isRemoteCriterionOperator(
					CustomFunctionOperators.AccountsFilter
				)
			).toBe(false);
			expect(isRemoteCriterionOperator('eq')).toBe(false);
			expect(isRemoteCriterionOperator('')).toBe(false);
			expect(isRemoteCriterionOperator(null)).toBe(false);
			expect(isRemoteCriterionOperator(undefined)).toBe(false);
		});
	});
});
