import * as API from 'shared/api';
import TagDisplay from 'segment/components/criteria-card/display-components/TagDisplay';
import TagInput from '../inputs/TagInput';
import {createTagProperty} from '../utils/utils';
import {CustomFunctionOperators, NotOperators} from '../utils/constants';
import {RemoteCriterionType} from './RemoteCriterionType';

export const tagCriterionType: RemoteCriterionType = {
	get api() {
		return API.tags.search;
	},
	createProperty: createTagProperty,
	DisplayComponent: TagDisplay,
	idProperty: 'tags/id',
	InputComponent: TagInput,
	nameProperty: 'tags/name',
	negativeOperator: NotOperators.NotTagsFilter,
	operators: new Set([
		CustomFunctionOperators.TagsFilter,
		NotOperators.NotTagsFilter,
	]),
	positiveOperator: CustomFunctionOperators.TagsFilter,
	propertyKey: 'tag',
	supportsCategories: false,
};
