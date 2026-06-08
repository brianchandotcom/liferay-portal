/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import VocabularyDisplay from '~/segment/components/criteria-card/display-components/VocabularyDisplay';
import * as API from '~/shared/api';

import VocabularyInput from '../inputs/VocabularyInput';
import {CustomFunctionOperators, NotOperators} from '../utils/constants';
import {createVocabularyProperty} from '../utils/utils';
import {RemoteCriterionType} from './RemoteCriterionType';

export const vocabularyCriterionType: RemoteCriterionType = {
	get api() {
		return API.vocabularies.search;
	},

	createProperty: createVocabularyProperty,

	DisplayComponent: VocabularyDisplay,
	idProperty: 'vocabularies/id',

	InputComponent: VocabularyInput,
	nameProperty: 'vocabularies/name',
	negativeOperator: NotOperators.NotVocabulariesFilter,

	operators: new Set([
		CustomFunctionOperators.VocabulariesFilter,
		NotOperators.NotVocabulariesFilter,
	]),

	positiveOperator: CustomFunctionOperators.VocabulariesFilter,
	propertyKey: 'vocabulary',
	supportsCategories: true,
};
