/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import TagDisplay from '~/segment/components/criteria-card/display-components/TagDisplay';
import * as API from '~/shared/api';

import TagInput from '../inputs/TagInput';
import {CustomFunctionOperators, NotOperators} from '../utils/constants';
import {createTagProperty} from '../utils/utils';
import {RemoteCriterionType} from './RemoteCriterionType';

export const tagCriterionType: RemoteCriterionType = {
	DisplayComponent: TagDisplay,
	InputComponent: TagInput,

	get api() {
		return API.tags.search;
	},

	createProperty: createTagProperty,
	idProperty: 'tags/id',
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
