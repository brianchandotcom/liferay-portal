import DateFilterConjunctionDisplay from './DateFilterConjunctionDisplay';
import React from 'react';
import {
	ASSET_TYPE_OPTIONS,
	EVENT_TYPE_OPTIONS,
	getAssetTypeFromValue,
	getConjunctionCriterionFromValue,
	getEventTypeFromValue,
	OCCURRENCE_OPTIONS,
} from 'segment/segment-editor/dynamic/inputs/shared/remote-filter-input-helpers';
import {CustomValue} from 'shared/util/records';
import {getIndexFromPropertyName} from 'segment/segment-editor/dynamic/utils/custom-inputs';
import {getOperatorLabel, maybeFormatToKnownType} from '../utils';
import {IDisplayComponentProps} from '../types';

const VocabularyDisplay: React.FC<IDisplayComponentProps> = ({
	criterion,
	property,
}) => {
	const {operatorName, value} = criterion;
	const {entityName, label, type} = property;

	const valueIMap = value as CustomValue | undefined;

	const operatorKey = maybeFormatToKnownType(operatorName ?? '', value);
	const operatorLabel = getOperatorLabel(operatorKey, type);

	const assetType = getAssetTypeFromValue(valueIMap);
	const eventType = getEventTypeFromValue(valueIMap);

	const occurrenceOperator = valueIMap?.get('operator') as string | null;
	const occurrenceCount = valueIMap?.get('value') as number | null;

	const eventTypeLabel =
		EVENT_TYPE_OPTIONS.find(({value}) => value === eventType)?.label ??
		String(eventType);

	const assetTypeLabel =
		ASSET_TYPE_OPTIONS.find(({value}) => value === assetType)?.label ??
		String(assetType);

	const occurrenceLabel =
		OCCURRENCE_OPTIONS.find(({value}) => value === occurrenceOperator)
			?.label ?? '';

	const conjunctionCriterion = getConjunctionCriterionFromValue(valueIMap);

	const categoryNames: string[] = (() => {
		if (!valueIMap) return [];

		const catIndex = getIndexFromPropertyName(valueIMap, 'categories');

		if (catIndex >= 0) {
			return (
				(
					valueIMap.getIn([
						'criterionGroup',
						'items',
						catIndex,
						'value',
					]) as any
				)
					?.toJS?.()
					?.map((c: {name: string}) => c.name) ?? []
			);
		}

		const items = valueIMap.getIn(['criterionGroup', 'items']) as any;

		if (!items) return [];

		const orGroup = items.find(
			(item: any) => item.get?.('conjunctionName') === 'or'
		);

		if (orGroup) {
			const names: string[] = [];

			orGroup.get?.('items')?.forEach((andGroup: any) => {
				const andItems = andGroup.get?.('items');

				if (!andItems) return;

				const nameItem = andItems.find(
					(i: any) => i.get?.('propertyName') === 'categories/name'
				);

				if (nameItem)
					names.push((nameItem.get?.('value') as string) ?? '');
			});

			return names;
		}

		const catNameItem = items.find(
			(i: any) => i.get?.('propertyName') === 'categories/name'
		);

		return catNameItem
			? [(catNameItem.get?.('value') as string) ?? '']
			: [];
	})();

	const vocNameIndex = valueIMap
		? getIndexFromPropertyName(valueIMap, 'vocabularies/name')
		: -1;

	const vocabularyName =
		vocNameIndex >= 0
			? (valueIMap?.getIn([
					'criterionGroup',
					'items',
					vocNameIndex,
					'value',
				]) as string) ?? label
			: label;

	return (
		<>
			{entityName}

			<span>{operatorLabel}</span>

			<span>{Liferay.Language.get('triggered').toLowerCase()}</span>

			<b>{eventTypeLabel}</b>

			<span>
				{Liferay.Language.get('on-the-vocabulary').toLowerCase()}
			</span>

			<b>{vocabularyName}</b>

			{categoryNames.length > 0 && (
				<>
					<span>
						{Liferay.Language.get(
							'on-the-categories'
						).toLowerCase()}
					</span>

					<b>{categoryNames.join(', ')}</b>
				</>
			)}

			<span>{Liferay.Language.get('for').toLowerCase()}</span>

			<b>{assetTypeLabel}</b>

			<span>{Liferay.Language.get('asset-type').toLowerCase()}</span>

			<span>{occurrenceLabel}</span>

			{occurrenceCount !== null && (
				<>
					<b>{occurrenceCount}</b>

					<span>{Liferay.Language.get('times')}</span>
				</>
			)}

			<DateFilterConjunctionDisplay
				conjunctionCriterion={conjunctionCriterion}
			/>
		</>
	);
};

export default VocabularyDisplay;
