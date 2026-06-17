import React from 'react';
import {
	ALL_APPLICATION_IDS,
	ALL_EVENT_IDS,
	APPLICATION_ID_ASSET_TYPE_MAP,
	ASSET_TYPE_APPLICATION_ID_MAP,
	ASSET_TYPE_COMPATIBLE_EVENTS_MAP,
	EVENT_ID_EVENT_TYPE_MAP,
	EVENT_TYPE_EVENT_ID_MAP,
	RelationalOperators,
	TimeSpans,
} from '../../utils/constants';
import {
	createCustomValueMap,
	getFilterCriterionIMap,
	getIndexFromPropertyName,
} from '../../utils/custom-inputs';
import {Criterion} from '../../utils/types';
import {CustomValue} from 'shared/util/records';
import {isValid} from '../../utils/utils';

export const EVENT_TYPE_OPTIONS = [
	{label: Liferay.Language.get('all-events'), value: 'all'},
	{label: Liferay.Language.get('view'), value: 'view'},
	{label: Liferay.Language.get('download'), value: 'download'},
	{label: Liferay.Language.get('impression'), value: 'impression'},
	{label: Liferay.Language.get('submit'), value: 'submit'},
	{label: Liferay.Language.get('comment'), value: 'comment'},
];

export const OCCURRENCE_OPTIONS = [
	{
		label: Liferay.Language.get('at-least').toLowerCase(),
		value: RelationalOperators.GE,
	},
	{
		label: Liferay.Language.get('at-most').toLowerCase(),
		value: RelationalOperators.LE,
	},
];

export const ASSET_TYPE_OPTIONS = [
	{label: Liferay.Language.get('any'), value: 'any'},
	{label: Liferay.Language.get('blogs'), value: 'blogs'},
	{label: Liferay.Language.get('forms'), value: 'forms'},
	{
		label: Liferay.Language.get('documents-and-media'),
		value: 'documents-and-media',
	},
	{label: Liferay.Language.get('web-content'), value: 'web-content'},
];

export const DEFAULT_CONJUNCTION_CRITERION = {
	operatorName: RelationalOperators.GT as any,
	propertyName: 'day',
	touched: false,
	valid: true,
	value: TimeSpans.Last24Hours,
} as Criterion & {touched: boolean; valid: boolean};

export const isValidOccurrenceCount = (count: number | string): boolean =>
	isValid(count) && Number(count) >= 0;

export const getAssetTypeFromValue = (
	value: CustomValue | undefined
): React.Key => {
	if (!value) {
		return 'any';
	}

	const appIdIndex = getIndexFromPropertyName(value, 'applicationId');

	if (appIdIndex >= 0) {
		const appIdValue = value.getIn([
			'criterionGroup',
			'items',
			appIdIndex,
			'value',
		]) as any;

		const appIds = appIdValue?.toJS?.() ?? appIdValue;

		if (Array.isArray(appIds) && appIds.length === 1) {
			return APPLICATION_ID_ASSET_TYPE_MAP[appIds[0]] ?? 'any';
		}
	}

	return 'any';
};

export const getEventTypeFromValue = (
	value: CustomValue | undefined
): React.Key => {
	if (!value) {
		return 'all';
	}

	const eventIdIndex = getIndexFromPropertyName(value, 'eventId');

	if (eventIdIndex >= 0) {
		const eventIdValue = value.getIn([
			'criterionGroup',
			'items',
			eventIdIndex,
			'value',
		]) as any;

		const eventIds = eventIdValue?.toJS?.() ?? eventIdValue;

		if (Array.isArray(eventIds) && eventIds.length === 1) {
			return EVENT_ID_EVENT_TYPE_MAP[eventIds[0]] ?? 'all';
		}
	}

	return 'all';
};

export const getConjunctionCriterionFromValue = (
	value: CustomValue | undefined
): Criterion & {touched: boolean; valid: boolean} => {
	if (!value) {
		return DEFAULT_CONJUNCTION_CRITERION;
	}

	const dayIndex = getIndexFromPropertyName(value, 'day');

	if (dayIndex < 0) {
		return DEFAULT_CONJUNCTION_CRITERION;
	}

	return (
		(getFilterCriterionIMap(value, dayIndex)?.toJS() as Criterion & {
			touched: boolean;
			valid: boolean;
		}) ?? DEFAULT_CONJUNCTION_CRITERION
	);
};

export interface RemoteFilterInputConfig {
	idProperty: string;
	nameProperty: string;
	supportsCategories: boolean;
}

const resolveEventIds = (
	eventType: React.Key,
	assetType: React.Key,
	isAnyAsset: boolean
): string[] => {
	if (eventType === 'all') {
		if (isAnyAsset) {
			return ALL_EVENT_IDS;
		}

		const compatibleEvents =
			ASSET_TYPE_COMPATIBLE_EVENTS_MAP[assetType as string] || [];

		const ids: string[] = [];

		compatibleEvents.forEach((type) => {
			if (type !== 'all') {
				const eventIdForAsset =
					EVENT_TYPE_EVENT_ID_MAP[type]?.[assetType as string];

				if (eventIdForAsset) {
					ids.push(eventIdForAsset);
				}
			}
		});

		return Array.from(new Set(ids));
	}

	if (isAnyAsset) {
		const eventMapForType =
			EVENT_TYPE_EVENT_ID_MAP[eventType as string] || {};

		return Array.from(
			new Set(Object.keys(eventMapForType).map((k) => eventMapForType[k]))
		);
	}

	const specificId =
		EVENT_TYPE_EVENT_ID_MAP[eventType as string]?.[assetType as string];

	return specificId ? [specificId] : [];
};

export interface BuildRemoteFilterValueArgs {
	assetType: React.Key;
	categories?: Array<{id: string; name: string}>;
	conjunctionCriterion: Criterion & {touched: boolean; valid: boolean};
	entityId: string;
	entityName: string;
	eventType: React.Key;
	occurrenceCount: number | string;
	occurrenceOperator: React.Key;
}

export const buildRemoteFilterValue = (
	args: BuildRemoteFilterValueArgs,
	config: RemoteFilterInputConfig
): CustomValue => {
	const {
		assetType,
		categories,
		conjunctionCriterion,
		entityId,
		entityName,
		eventType,
		occurrenceCount,
		occurrenceOperator,
	} = args;

	const isAnyAsset = assetType === 'any';

	const criterionItems: (Criterion & {touched: boolean; valid: boolean})[] = [
		{
			operatorName: RelationalOperators.EQ as any,
			propertyName: config.idProperty,
			touched: false,
			valid: true,
			value: entityId,
		} as Criterion & {touched: boolean; valid: boolean},
		{
			operatorName: RelationalOperators.EQ as any,
			propertyName: config.nameProperty,
			touched: false,
			valid: true,
			value: entityName,
		} as Criterion & {touched: boolean; valid: boolean},
	];

	const applicationIds = isAnyAsset
		? ALL_APPLICATION_IDS
		: [ASSET_TYPE_APPLICATION_ID_MAP[assetType as string]];

	criterionItems.push({
		operatorName: RelationalOperators.In as any,
		propertyName: 'applicationId',
		touched: false,
		valid: true,
		value: applicationIds,
	} as Criterion & {touched: boolean; valid: boolean});

	criterionItems.push({
		operatorName: RelationalOperators.In as any,
		propertyName: 'eventId',
		touched: false,
		valid: true,
		value: resolveEventIds(eventType, assetType, isAnyAsset),
	} as Criterion & {touched: boolean; valid: boolean});

	if (config.supportsCategories && categories && categories.length > 0) {
		criterionItems.push({
			operatorName: RelationalOperators.In as any,
			propertyName: 'categories',
			touched: false,
			valid: true,
			value: categories,
		} as Criterion & {touched: boolean; valid: boolean});
	}

	criterionItems.push(conjunctionCriterion);

	return createCustomValueMap([
		{key: 'operator', value: occurrenceOperator},
		{key: 'value', value: occurrenceCount},
		{key: 'criterionGroup', value: criterionItems},
	]);
};
