import {ComponentType} from 'react';
import {CustomFunctionOperators, NotOperators} from '../utils/constants';
import {IDisplayComponentProps} from 'segment/components/criteria-card/types';
import {ISegmentEditorCustomInputBase} from '../utils/types';
import {Property} from 'shared/util/records';

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
