/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import dateFns from 'date-fns';
import {Map, fromJS} from 'immutable';
import {every, isBoolean, isString, isUndefined} from 'lodash';
import {v4 as uuidv4} from 'uuid';
import {Event} from '~/event-analysis/utils/types';
import {FieldContexts, FieldOwnerTypes} from '~/shared/util/constants';
import {Property} from '~/shared/util/records';

import {EntityType, ReferencedEntities} from '../context/referencedObjects';
import {
	ACCOUNT_PROPERTIES,
	INDIVIDUAL_PROPERTIES,
	ORGANIZATION_PROPERTIES,
	SESSION_PROPERTIES,
	WEB_BEHAVIORS,
} from '../utils/properties';
import {
	Conjunctions,
	CustomFunctionOperators,
	MAX_NESTED_OR_CRITERIA,
	MAX_SEQUENTIAL_CRITERIA,
	NestedOrLimitState,
	NotOperators,
	PropertyTypes,
	SUPPORTED_OPERATORS_MAP,
	SequentialLimitState,
	isKnown,
	isUnknown,
} from './constants';
import {Criteria, Criterion, CriterionGroup, Operator} from './types';

const GROUP_ID_NAMESPACE = 'group_';
const ROW_ID_NAMESPACE = 'row_';

export const createInterestProperty = function createInterestProperty(
	name: string
): Property {
	return new Property({
		entityName: Liferay.Language.get('individual'),
		label: name,
		name,
		propertyKey: 'interest',
		type: PropertyTypes.Interest,
	});
};

export const createVocabularyProperty = function createVocabularyProperty({
	id,
	name,
}: {
	id: string;
	name: string;
}): Property {
	return new Property({
		entityName: Liferay.Language.get('vocabularies-and-categories'),
		label: name,
		name: id,
		propertyKey: 'vocabulary',
		type: PropertyTypes.Vocabulary,
	});
};

export function createTagProperty({
	id,
	name,
}: {
	id: string;
	name: string;
}): Property {
	return new Property({
		entityName: Liferay.Language.get('tags'),
		label: name,
		name: id,
		propertyKey: 'tag',
		type: PropertyTypes.Tag,
	});
}

/**
 * Generates a unique group id.
 */
export const generateGroupId = function generateGroupId(): string {
	return `${GROUP_ID_NAMESPACE}${uuidv4()}`;
};

/**
 * Creates a new group object with items.
 */
export const createNewGroup = function createNewGroup(
	items: Criteria[],
	conjunctionName: Conjunctions = Conjunctions.And
): CriterionGroup {
	return {
		conjunctionName,
		criteriaGroupId: generateGroupId(),
		items,
	};
};

/**
 * Generates a unique row id.
 */
export const generateRowId = function generateRowId(): string {
	return `${ROW_ID_NAMESPACE}${uuidv4()}`;
};

/**
 * Gets the property name from the propertyLabel string .
 */
export const getPropertyNameFromRaw = function getPropertyNameFromRaw(
	propertyLabel: string = ''
): string {
	const properties = propertyLabel.split('/');

	return properties.length > 1 ? properties[1] : properties[0];
};

export const getPropertyContextFromRaw = function getPropertyContextFromRaw(
	propertyLabel: string = ''
): string | null {
	const properties = propertyLabel.split('/');

	return properties.length > 1 ? properties[0] : null;
};

const _getLimitState = (
	length: number,
	max: number
): 'exceedsLimit' | 'reachedLimit' | null => {
	if (length > max) {
		return 'exceedsLimit';
	}

	if (length === max) {
		return 'reachedLimit';
	}

	return null;
};

/**
 * Returns the current state of the nested OR limit for the given group, or
 * null when the limit does not apply. Callers must skip the root group; this
 * helper assumes the input is nested.
 */
export const getNestedOrLimitState = function getNestedOrLimitState(
	criteria: CriterionGroup | null | undefined
): NestedOrLimitState | null {
	if (!criteria || criteria.conjunctionName !== Conjunctions.Or) {
		return null;
	}

	return _getLimitState(criteria.items?.length ?? 0, MAX_NESTED_OR_CRITERIA);
};

/**
 * Returns the current state of the sequential criteria limit for the root
 * AND group, or null when the limit does not apply. Callers must check that
 * sequential mode is enabled and that the group is the root.
 */
export const getSequentialLimitState = function getSequentialLimitState(
	criteria: CriterionGroup | null | undefined
): SequentialLimitState | null {
	if (!criteria || criteria.conjunctionName !== Conjunctions.And) {
		return null;
	}

	return _getLimitState(criteria.items?.length ?? 0, MAX_SEQUENTIAL_CRITERIA);
};

export const hasRootAndExceeded = function hasRootAndExceeded(
	criteria: CriterionGroup | null | undefined
): boolean {
	return getSequentialLimitState(criteria) === 'exceedsLimit';
};

/**
 * Gets the list of operators for a supported type.
 * Used for displaying the operators available for each criteria row.
 */
export const getSupportedOperatorsFromType =
	function getSupportedOperatorsFromType(type: string = ''): Operator[] {
		return (
			(SUPPORTED_OPERATORS_MAP as Record<string, Operator[]>)[
				type.toLowerCase()
			] || []
		);
	};

/**
 * Checks if value is a CriterionGroup.
 */
export const isCriterionGroup = function isCriterionGroup(
	value: CriterionGroup | Criterion
): value is CriterionGroup {
	return !!value && (value as CriterionGroup).items !== undefined;
};

/**
 * Gets a list of group ids from a criteria object.
 * Used for disallowing groups to be moved into its own deeper nested groups.
 * Example of returned value: ['group_02', 'group_03']
 */
export const getChildGroupIds = function getChildGroupIds(
	criteria: Criteria
): string[] {
	let childGroupIds: string[] = [];

	if (isCriterionGroup(criteria) && criteria.items.length) {
		childGroupIds = criteria.items.reduce(
			(groupIdList: string[], item) =>
				isCriterionGroup(item)
					? [
							...groupIdList,
							item.criteriaGroupId,
							...getChildGroupIds(item),
						]
					: groupIdList,
			[] as string[]
		);
	}

	return childGroupIds;
};

export const hasNestedOrExceeded = function hasNestedOrExceeded(
	criteria: CriterionGroup | Criterion | null | undefined
): boolean {
	return (
		!!criteria &&
		isCriterionGroup(criteria) &&
		criteria.items.some(
			(item) =>
				isCriterionGroup(item) &&
				(getNestedOrLimitState(item) === 'exceedsLimit' ||
					hasNestedOrExceeded(item))
		)
	);
};

/**
 * Checks if value is an ImmutableMap
 */
export const isMap = function isMap(
	value: Map<string, any> | object
): value is Map<string, any> {
	return Map.isMap(value as Map<string, any>);
};

/**
 * Checks if value is either isKnown or isUnknown.
 */
export const isOfKnownType = function isOfKnownType(key: string): boolean {
	return [isKnown, isUnknown].includes(key);
};

/**
 * Converts an object of key value pairs to a form data object for passing
 * into a fetch body.
 */
export const objectToFormData = function objectToFormData(
	dataObject: Record<string, string | Blob>
): FormData {
	const formData = new FormData();

	Object.keys(dataObject).forEach((key) => {
		formData.set(key, dataObject[key]);
	});

	return formData;
};

/**
 * Parse an activityKey string into an object.
 */
export const parseActivityKey = function parseActivityKey(
	activityKey: string = ''
): {
	eventId: string;
	id: string;
	objectType: string;
} {
	const [objectType, eventId, id] = activityKey.split('#');

	return {eventId, id, objectType};
};

/**
 * Returns a YYYY-MM-DD date
 * based on a JS Date object
 *
 * @export
 */
export const jsDatetoYYYYMMDD = function jsDatetoYYYYMMDD(
	dateJsObject: Date
): string {
	const DATE_FORMAT = 'YYYY-MM-DD';

	return dateFns.format(dateJsObject, DATE_FORMAT);
};

/**
 * Finds the matching property based on its Criterion.
 */
export const findPropertyByCriterion = function findPropertyByCriterion(
	criterion: Criterion,
	referencedPropertiesIMap: Map<string, Map<string, Property>>
): Property | undefined {
	const {operatorName, propertyName, type, value} = criterion;

	if (
		[
			CustomFunctionOperators.ActivitiesFilterByCount,
			NotOperators.NotActivitiesFilterByCount,
		].includes(
			operatorName as unknown as CustomFunctionOperators | NotOperators
		)
	) {
		const {eventId = propertyName} = parseActivityKey(
			(value as Map<string, any>).getIn(
				['criterionGroup', 'items', 0, 'value'],
				''
			)
		);

		return WEB_BEHAVIORS.find(
			(property: Property | undefined) => property?.name === eventId
		);
	}
	else if (
		[
			CustomFunctionOperators.EventsFilterByCount,
			NotOperators.NotEventsFilterByCount,
		].includes(
			operatorName as unknown as CustomFunctionOperators | NotOperators
		)
	) {
		const eventId = value.getIn(
			['criterionGroup', 'items', 0, 'value'],
			''
		);

		return referencedPropertiesIMap.getIn(['event', eventId]);
	}
	else if (
		[
			CustomFunctionOperators.AccountsFilter,
			NotOperators.NotAccountsFilter,
		].includes(
			operatorName as unknown as CustomFunctionOperators | NotOperators
		)
	) {
		if (getPropertyContextFromRaw(propertyName) !== FieldContexts.Custom) {
			return ACCOUNT_PROPERTIES.find(
				(property: Property | undefined) =>
					property?.name === propertyName
			);
		}

		return referencedPropertiesIMap.getIn(
			[
				'account',
				getPropertyContextFromRaw(propertyName) ?? '',
				getPropertyNameFromRaw(propertyName),
			],
			''
		);
	}
	else if (
		[
			NotOperators.NotOrganizationsFilter,
			CustomFunctionOperators.OrganizationsFilter,
		].includes(
			operatorName as unknown as CustomFunctionOperators & NotOperators
		)
	) {
		if (getPropertyContextFromRaw(propertyName) !== FieldContexts.Custom) {
			return ORGANIZATION_PROPERTIES.find(
				(property: Property | undefined) =>
					property?.name === propertyName
			);
		}

		return referencedPropertiesIMap.getIn(
			[
				'organization',
				getPropertyContextFromRaw(propertyName) ?? '',
				getPropertyNameFromRaw(propertyName),
			],
			''
		);
	}
	else if (
		[
			CustomFunctionOperators.SessionsFilter,
			NotOperators.NotSessionsFilter,
		].includes(
			operatorName as unknown as CustomFunctionOperators | NotOperators
		) ||
		type === PropertyTypes.SessionDateTime
	) {
		return SESSION_PROPERTIES.find(
			(property: Property | undefined) => property?.name === propertyName
		);
	}
	else if (
		[
			CustomFunctionOperators.VocabulariesFilter,
			NotOperators.NotVocabulariesFilter,
		].includes(
			operatorName as unknown as CustomFunctionOperators | NotOperators
		)
	) {
		return (
			(referencedPropertiesIMap.getIn(['vocabulary', propertyName]) as
				| Property
				| undefined) ??
			createVocabularyProperty({
				id: propertyName ?? '',
				name:
					((value as Map<string, any>)
						?.getIn(['criterionGroup', 'items'])
						?.find(
							(item: Map<string, any>) =>
								item?.get('propertyName') ===
								'vocabularies/name'
						)
						?.get('value') as string | undefined) ??
					propertyName ??
					'',
			})
		);
	}
	else if (
		[
			CustomFunctionOperators.TagsFilter,
			NotOperators.NotTagsFilter,
		].includes(
			operatorName as unknown as CustomFunctionOperators | NotOperators
		)
	) {
		return (
			(referencedPropertiesIMap.getIn(['tag', propertyName]) as
				| Property
				| undefined) ??
			createTagProperty({
				id: propertyName ?? '',
				name:
					((value as Map<string, any>)
						?.getIn(['criterionGroup', 'items'])
						?.find(
							(item: Map<string, any>) =>
								item?.get('propertyName') === 'tags/name'
						)
						?.get('value') as string | undefined) ??
					propertyName ??
					'',
			})
		);
	}
	else if (operatorName === CustomFunctionOperators.InterestsFilter) {
		return createInterestProperty(propertyName ?? '');
	}
	else if (INDIVIDUAL_PROPERTIES.find(({name}) => name === propertyName)) {
		return INDIVIDUAL_PROPERTIES.find(({name}) => name === propertyName);
	}
	else {
		return referencedPropertiesIMap.getIn(
			[
				'individual',
				getPropertyContextFromRaw(propertyName) ?? '',
				getPropertyNameFromRaw(propertyName),
			],
			''
		);
	}
};

export const convertFieldMappingToAccountProperty =
	function convertFieldMappingToAccountProperty(
		fieldMapping:
			| Map<string, any>
			| {
					context: string;
					displayName: string;
					id: string;
					name: string;
					ownerType: string;
					rawType: string;
					type: string;
			  }
	): Property {
		const displayName = isMap(fieldMapping)
			? fieldMapping.get('displayName')
			: fieldMapping.displayName;
		const id = isMap(fieldMapping)
			? fieldMapping.get('id')
			: fieldMapping.id;
		const name = isMap(fieldMapping)
			? fieldMapping.get('name')
			: fieldMapping.name;
		const type = isMap(fieldMapping)
			? fieldMapping.get('rawType')
			: fieldMapping.rawType;

		return new Property({
			entityName: Liferay.Language.get('account'),
			id,
			label: displayName || name,
			name: id,
			propertyKey: FieldOwnerTypes.Account,
			type: `account-${type.toLowerCase()}` as PropertyTypes,
		});
	};

export const convertFieldMappingToIndividualProperty =
	function convertFieldMappingToIndividualProperty(
		fieldMapping:
			| Map<string, any>
			| {
					context: string;
					displayName: string;
					id: string;
					name: string;
					ownerType: string;
					rawType: string;
					type: string;
			  }
	): Property {
		const context = isMap(fieldMapping)
			? fieldMapping.get('context')
			: fieldMapping.context;
		const displayName = isMap(fieldMapping)
			? fieldMapping.get('displayName')
			: fieldMapping.displayName;
		const id = isMap(fieldMapping)
			? fieldMapping.get('id')
			: fieldMapping.id;
		const name = isMap(fieldMapping)
			? fieldMapping.get('name')
			: fieldMapping.name;
		const type = isMap(fieldMapping)
			? fieldMapping.get('rawType')
			: fieldMapping.rawType;

		return new Property({
			entityName: Liferay.Language.get('individual'),
			id,
			label: displayName || name,
			name: context ? `${context}/${id}/value` : id,
			propertyKey: FieldOwnerTypes.Individual,
			type: type.toLowerCase(),
		});
	};

export const convertFieldMappingToOrganizationProperty =
	function convertFieldMappingToOrganizationProperty(
		fieldMapping:
			| Map<string, any>
			| {
					context: string;
					displayName: string;
					id: string;
					name: string;
					ownerType: string;
					rawType: string;
					type: string;
			  }
	): Property {
		const context = isMap(fieldMapping)
			? fieldMapping.get('context')
			: fieldMapping.context;
		const displayName = isMap(fieldMapping)
			? fieldMapping.get('displayName')
			: fieldMapping.displayName;
		const id = isMap(fieldMapping)
			? fieldMapping.get('id')
			: fieldMapping.id;
		const name = isMap(fieldMapping)
			? fieldMapping.get('name')
			: fieldMapping.name;
		const type = isMap(fieldMapping)
			? fieldMapping.get('rawType')
			: fieldMapping.rawType;

		return new Property({
			entityName: Liferay.Language.get('organization'),
			id,
			label: displayName || name,
			name: context ? `${context}/${id}/value` : id,
			propertyKey: FieldOwnerTypes.Organization,
			type: `organization-${type.toLowerCase()}` as PropertyTypes,
		});
	};

export const convertEventToProperty = function convertEventToProperty(
	eventDefinition: Map<string, any> | Event = Map()
): Map<string, Map<string, Property>> {
	const displayName = isMap(eventDefinition)
		? eventDefinition.get('displayName')
		: eventDefinition.displayName;
	const name = isMap(eventDefinition)
		? eventDefinition.get('name')
		: eventDefinition.name;

	const hidden = isMap(eventDefinition)
		? eventDefinition.get('hidden')
		: eventDefinition.hidden;

	return new Property({
		entityName: Liferay.Language.get('event'),
		id: name,
		label: displayName || name,
		name,
		options: [{label: 'hidden', value: hidden}],
		propertyKey: 'event',
		type: PropertyTypes.Event,
	});
};

export const convertFieldMappingsToProperties =
	function convertFieldMappingsToProperties(
		fieldMappingsIMap: Map<
			string,
			Map<string, Map<string, Map<string, any>>>
		> = Map()
	): Map<string, Map<string, Map<string, Property>>> {
		return fieldMappingsIMap.map((ownerTypeGroup, key) => {
			let conversionFn: ((fieldMappingIMap: any) => Property) | undefined;

			if (key === FieldOwnerTypes.Account) {
				conversionFn = convertFieldMappingToAccountProperty;
			}
			else if (key === FieldOwnerTypes.Individual) {
				conversionFn = convertFieldMappingToIndividualProperty;
			}
			else if (key === FieldOwnerTypes.Organization) {
				conversionFn = convertFieldMappingToOrganizationProperty;
			}

			if (conversionFn) {
				const fn = conversionFn;

				return ownerTypeGroup!.map((contextGroup) =>
					contextGroup!.reduce(
						(
							acc?: Map<string, Property>,
							fieldMappingIMap?: Map<string, any>,
							k?: string
						) => (acc ?? Map()).set(k ?? '', fn(fieldMappingIMap)),
						Map() as Map<string, Property>
					)
				);
			}
		}) as Map<string, Map<string, Map<string, Property>>>;
	};

export const convertReferencedObjectsToProperties =
	function convertReferencedObjectsToProperties(
		referencedObjectsIMap: Map<
			string,
			Map<string, Map<string, Map<string, any>>>
		> = Map()
	): Map<string, Map<string, Map<string, Property> | Property>> {
		const fieldMappingProperties = convertFieldMappingsToProperties(
			referencedObjectsIMap.get('fieldMappings')
		);

		const eventProperties = referencedObjectsIMap
			.get('event', Map())
			.merge(referencedObjectsIMap.get('custom-events'))
			.map(convertEventToProperty);

		return fieldMappingProperties.merge(fromJS({event: eventProperties}));
	};

/**
 * Check to see if the value is a valid input value.
 * The input value cannot be an empty string or undefined.
 * @returns {boolean}
 */
export const isValid = function isValid(value: any): boolean {
	return !(isUndefined(value) || (isString(value) && !value.length));
};

/**
 * Recursively check through all criterions and invalidates those
 * that do not have a matching property
 */
export const invalidateCriterionWithMissingProperty =
	function invalidateCriterionWithMissingProperty(
		criteria: Criteria,
		referencedPropertiesIMap: Map<string, Property>
	): Criteria {
		if (isCriterionGroup(criteria)) {
			const {items} = criteria;

			if (items.length) {
				return {
					...criteria,
					items: items.map((criterion) =>
						invalidateCriterionWithMissingProperty(
							criterion,
							referencedPropertiesIMap
						)
					),
				};
			}
		}
		else {
			if (findPropertyByCriterion(criteria, referencedPropertiesIMap)) {
				return criteria;
			}

			return {
				...criteria,
				valid: isBoolean(criteria.valid)
					? false
					: Object.keys(criteria.valid as object).reduce(
							(acc, key) => ({...acc, [key]: false}),
							{}
						),
			};
		}

		return criteria;
	};

export const parseReferencedEntityId = function parseReferencedEntityId(
	id: string,
	referencedEntities: ReferencedEntities,
	type: EntityType
) {
	let parsedId: string | undefined = id;

	if (
		type === EntityType.Assets &&
		parsedId &&
		parsedId.indexOf('_') === -1
	) {
		const keys = Object.keys(
			referencedEntities.getIn([EntityType.Assets]).toObject()
		);

		parsedId = keys.find((key) => key.includes(id));
	}

	return parsedId;
};

/**
 * Recursively check through all criteria to see if they're valid.
 */
export const validateSegmentInputs = function validateSegmentInputs(
	criteria: Criteria
): boolean {
	if (isCriterionGroup(criteria)) {
		const {items} = criteria;

		if (items.length) {
			return items.map(validateSegmentInputs).every(Boolean);
		}
	}
	else if (criteria) {
		if (isBoolean(criteria.valid)) {
			return criteria.valid;
		}

		return every(criteria.valid, Boolean);
	}

	return false;
};
