/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ComponentType} from 'react';
import {IDisplayComponentProps} from '~/segment/components/criteria-card/types';
import {Property} from '~/shared/util/records';

import {CustomFunctionOperators, NotOperators} from '../utils/constants';
import {ISegmentEditorCustomInputBase} from '../utils/types';

export interface RemoteCriterionSearchParams {
	channelId: string;
	groupId: string;
	keywords?: string;
	page?: number;
	pageSize?: number;
}

export interface RemoteCriterionSearchResult {
	items: Array<{id: string; name: string}>;
	totalCount: number;
}

export interface RemoteCriterionType {
	DisplayComponent: ComponentType<IDisplayComponentProps>;
	InputComponent: ComponentType<ISegmentEditorCustomInputBase>;
	api: (
		params: RemoteCriterionSearchParams
	) => Promise<RemoteCriterionSearchResult>;
	createProperty: (data: {id: string; name: string}) => Property;
	idProperty: string;
	nameProperty: string;
	negativeOperator: NotOperators;
	operators: ReadonlySet<CustomFunctionOperators | NotOperators>;
	positiveOperator: CustomFunctionOperators;
	propertyKey: 'tag' | 'vocabulary';
	supportsCategories: boolean;
}
