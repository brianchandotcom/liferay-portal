import DateFilterConjunctionInput from './components/DateFilterConjunctionInput';
import Form from 'shared/components/form';
import getCN from 'classnames';
import Input from 'shared/components/Input';
import React, {useEffect, useState} from 'react';
import SelectCategoryFromModal, {
	CategoryItem,
} from './components/SelectCategoryFromModal';
import {
	ASSET_TYPE_COMPATIBLE_EVENTS_MAP,
	RelationalOperators,
} from '../utils/constants';
import {
	ASSET_TYPE_OPTIONS,
	buildRemoteFilterValue,
	DEFAULT_CONJUNCTION_CRITERION,
	EVENT_TYPE_OPTIONS,
	getAssetTypeFromValue,
	getConjunctionCriterionFromValue,
	getEventTypeFromValue,
	isValidOccurrenceCount,
	OCCURRENCE_OPTIONS,
} from './shared/remote-filter-input-helpers';
import {Criterion, ISegmentEditorCustomInputBase} from '../utils/types';
import {CustomValue} from 'shared/util/records';
import {getIndexFromPropertyName} from '../utils/custom-inputs';
import {Icon, Option, Picker} from '@clayui/core';
import {isValid} from '../utils/utils';

export {
	ASSET_TYPE_OPTIONS,
	EVENT_TYPE_OPTIONS,
	getAssetTypeFromValue,
	getConjunctionCriterionFromValue,
	getEventTypeFromValue,
	OCCURRENCE_OPTIONS,
};

const VOCABULARY_CONFIG = {
	idProperty: 'vocabularies/id',
	nameProperty: 'vocabularies/name',
	supportsCategories: true,
};

export function buildValue(
	eventType: React.Key,
	assetType: React.Key,
	occurrenceOperator: React.Key,
	occurrenceCount: number | string,
	conjunctionCriterion: Criterion & {touched: boolean; valid: boolean},
	vocabularyId: string,
	vocabularyName: string,
	categories: CategoryItem[]
): CustomValue {
	return buildRemoteFilterValue(
		{
			assetType,
			categories,
			conjunctionCriterion,
			entityId: vocabularyId,
			entityName: vocabularyName,
			eventType,
			occurrenceCount,
			occurrenceOperator,
		},
		VOCABULARY_CONFIG
	);
}

export function getCategoriesFromValue(
	value: CustomValue | undefined
): CategoryItem[] {
	if (!value) return [];

	const catIndex = getIndexFromPropertyName(value, 'categories');

	if (catIndex >= 0) {
		const catValue = value.getIn([
			'criterionGroup',
			'items',
			catIndex,
			'value',
		]) as any;

		return catValue
			? ((catValue.toJS?.() ?? catValue) as CategoryItem[])
			: [];
	}

	const items = value.getIn(['criterionGroup', 'items']) as any;

	if (!items) return [];

	const orGroup = items.find(
		(item: any) => item.get?.('conjunctionName') === 'or'
	);

	if (orGroup) {
		const categories: CategoryItem[] = [];

		orGroup.get?.('items')?.forEach((andGroup: any) => {
			const andItems = andGroup.get?.('items');

			if (!andItems) return;

			const idItem = andItems.find(
				(i: any) => i.get?.('propertyName') === 'categories/id'
			);
			const nameItem = andItems.find(
				(i: any) => i.get?.('propertyName') === 'categories/name'
			);

			if (idItem && nameItem) {
				categories.push({
					id: (idItem.get?.('value') as string) ?? '',
					name: (nameItem.get?.('value') as string) ?? '',
				});
			}
		});

		return categories;
	}

	const catIdItem = items.find(
		(i: any) => i.get?.('propertyName') === 'categories/id'
	);
	const catNameItem = items.find(
		(i: any) => i.get?.('propertyName') === 'categories/name'
	);

	if (catIdItem && catNameItem) {
		return [
			{
				id: (catIdItem.get?.('value') as string) ?? '',
				name: (catNameItem.get?.('value') as string) ?? '',
			},
		];
	}

	return [];
}

export default function VocabularyInput({
	channelId = '',
	displayValue,
	groupId = '',
	onChange,
	operatorRenderer: OperatorDropdown,
	property,
	value,
}: ISegmentEditorCustomInputBase) {
	const rawOperator = value?.get('operator');

	const [eventType, setEventType] = useState<React.Key>(
		getEventTypeFromValue(value)
	);
	const [occurrenceOperator, setOccurrenceOperator] = useState<React.Key>(
		rawOperator ?? RelationalOperators.GE
	);
	const [occurrenceCount, setOccurrenceCount] = useState<number | string>(
		value?.get('value') ?? 1
	);
	const [occurrenceTouched, setOccurrenceTouched] = useState(false);
	const [occurrenceValid, setOccurrenceValid] = useState(true);
	const [assetType, setAssetType] = useState<React.Key>(
		getAssetTypeFromValue(value)
	);
	const [conjunctionCriterion, setConjunctionCriterion] = useState<
		Criterion & {touched: boolean; valid: boolean}
	>(getConjunctionCriterionFromValue(value));
	const [categories, setCategories] = useState<CategoryItem[]>(
		getCategoriesFromValue(value)
	);

	useEffect(() => {
		if (!value || getIndexFromPropertyName(value, 'vocabularies/id') < 0) {
			onChange({
				touched: false,
				valid: true,
				value: buildValue(
					eventType,
					assetType,
					occurrenceOperator,
					occurrenceCount,
					conjunctionCriterion,
					property.name,
					displayValue ?? '',
					categories
				),
			});
		}
	}, []);

	const isOccurrenceValid = (
		operator: React.Key,
		count: number | string
	): boolean => isValidOccurrenceCount(count);

	const handleCategoriesChange = (newCategories: CategoryItem[]) => {
		setCategories(newCategories);

		onChange({
			touched: occurrenceTouched,
			valid: isOccurrenceValid(occurrenceOperator, occurrenceCount),
			value: buildValue(
				eventType,
				assetType,
				occurrenceOperator,
				occurrenceCount,
				conjunctionCriterion,
				property.name,
				displayValue ?? '',
				newCategories
			),
		});
	};

	const handleDateFilterChange = (criterion: Criterion | null) => {
		const newCriterion = criterion
			? (criterion as Criterion & {touched: boolean; valid: boolean})
			: DEFAULT_CONJUNCTION_CRITERION;

		setConjunctionCriterion(newCriterion);

		onChange({
			touched: occurrenceTouched,
			valid: isOccurrenceValid(occurrenceOperator, occurrenceCount),
			value: buildValue(
				eventType,
				assetType,
				occurrenceOperator,
				occurrenceCount,
				newCriterion,
				property.name,
				displayValue ?? '',
				categories
			),
		});
	};

	return (
		<div className="criteria-statement">
			<Form.Group autoFit>
				<Form.GroupItem className="entity-name" label shrink>
					{Liferay.Language.get('individual')}
				</Form.GroupItem>

				<OperatorDropdown />

				<Form.GroupItem className="entity-name" label shrink>
					{Liferay.Language.get('triggered').toLowerCase()}
				</Form.GroupItem>

				<Form.GroupItem shrink>
					<Picker
						className="criterion-input"
						items={EVENT_TYPE_OPTIONS.filter(({value}) =>
							(
								ASSET_TYPE_COMPATIBLE_EVENTS_MAP[
									assetType as string
								] ?? ['all']
							).includes(value)
						)}
						onSelectionChange={(newEventType: React.Key) => {
							setEventType(newEventType);

							onChange({
								touched: occurrenceTouched,
								valid: isOccurrenceValid(
									occurrenceOperator,
									occurrenceCount
								),
								value: buildValue(
									newEventType,
									assetType,
									occurrenceOperator,
									occurrenceCount,
									conjunctionCriterion,
									property.name,
									displayValue ?? '',
									categories
								),
							});
						}}
						searchable
						selectedKey={eventType}
					>
						{({label, value}: {label: string; value: string}) => (
							<Option key={value}>{label}</Option>
						)}
					</Picker>
				</Form.GroupItem>

				<Form.GroupItem className="entity-name" label shrink>
					{Liferay.Language.get('on-the-vocabulary').toLowerCase()}
				</Form.GroupItem>

				<Form.GroupItem className="display-value" label shrink>
					<b>{displayValue}</b>
				</Form.GroupItem>
			</Form.Group>

			<Form.Group autoFit>
				<Form.GroupItem className="entity-name" label shrink>
					{Liferay.Language.get('on-the-categories').toLowerCase()}
				</Form.GroupItem>

				<SelectCategoryFromModal
					channelId={channelId}
					groupId={groupId}
					onCategoriesChange={handleCategoriesChange}
					selectedCategories={categories}
					vocabularyId={property.name}
				/>
			</Form.Group>

			<Form.Group autoFit>
				<Form.GroupItem className="entity-name" label shrink>
					{Liferay.Language.get('for').toLowerCase()}
				</Form.GroupItem>

				<Form.GroupItem shrink>
					<Picker
						className="criterion-input"
						items={ASSET_TYPE_OPTIONS}
						onSelectionChange={(newAssetType: React.Key) => {
							setAssetType(newAssetType);

							const compatibleEvents =
								ASSET_TYPE_COMPATIBLE_EVENTS_MAP[
									newAssetType as string
								] ?? ['all'];

							const newEventType = compatibleEvents.includes(
								eventType as string
							)
								? eventType
								: 'all';

							if (newEventType !== eventType) {
								setEventType(newEventType);
							}

							onChange({
								touched: occurrenceTouched,
								valid: isOccurrenceValid(
									occurrenceOperator,
									occurrenceCount
								),
								value: buildValue(
									newEventType,
									newAssetType,
									occurrenceOperator,
									occurrenceCount,
									conjunctionCriterion,
									property.name,
									displayValue ?? '',
									categories
								),
							});
						}}
						selectedKey={assetType}
					>
						{({label, value}: {label: string; value: string}) => (
							<Option key={value}>{label}</Option>
						)}
					</Picker>
				</Form.GroupItem>

				<Form.GroupItem className="entity-name" label shrink>
					{Liferay.Language.get('asset-type').toLowerCase()}
				</Form.GroupItem>

				<Form.GroupItem shrink>
					<Picker
						className="operator-input"
						items={OCCURRENCE_OPTIONS}
						onSelectionChange={(newOperator: React.Key) => {
							setOccurrenceOperator(newOperator);

							const valid = isOccurrenceValid(
								newOperator,
								occurrenceCount
							);

							onChange({
								touched: occurrenceTouched,
								valid,
								value: buildValue(
									eventType,
									assetType,
									newOperator,
									occurrenceCount,
									conjunctionCriterion,
									property.name,
									displayValue ?? '',
									categories
								),
							});
						}}
						selectedKey={occurrenceOperator}
					>
						{({label, value}: {label: string; value: string}) => (
							<Option key={value}>{label}</Option>
						)}
					</Picker>
				</Form.GroupItem>

				<Form.GroupItem
					className={getCN({
						'has-error': !occurrenceValid && occurrenceTouched,
					})}
					shrink
				>
					<Input
						className="number-input"
						min="0"
						onBlur={({
							target: {value: inputVal},
						}: React.FocusEvent<HTMLInputElement>) => {
							const valid = isValidOccurrenceCount(inputVal);

							setOccurrenceTouched(true);
							setOccurrenceValid(valid);

							onChange({
								touched: true,
								valid,
								value: buildValue(
									eventType,
									assetType,
									occurrenceOperator,
									inputVal,
									conjunctionCriterion,
									property.name,
									displayValue ?? '',
									categories
								),
							});
						}}
						onChange={({
							target: {value: inputVal},
						}: React.ChangeEvent<HTMLInputElement>) => {
							let numberVal: string | number = '';

							if (isValid(inputVal)) {
								numberVal = parseInt(inputVal);
							}

							const valid = isValidOccurrenceCount(numberVal);

							setOccurrenceCount(numberVal);
							setOccurrenceTouched(true);
							setOccurrenceValid(valid);

							onChange({
								touched: true,
								valid,
								value: buildValue(
									eventType,
									assetType,
									occurrenceOperator,
									numberVal,
									conjunctionCriterion,
									property.name,
									displayValue ?? '',
									categories
								),
							});
						}}
						type="number"
						value={occurrenceCount}
					/>

					{occurrenceTouched && occurrenceCount === '' && (
						<div className="form-feedback-group">
							<div className="form-feedback-item">
								<Icon
									className="mr-1"
									symbol="exclamation-full"
								/>
								{Liferay.Language.get('required')}
							</div>
						</div>
					)}
				</Form.GroupItem>

				<Form.GroupItem className="unit" label shrink>
					{Liferay.Language.get('times')}
				</Form.GroupItem>

				<DateFilterConjunctionInput
					conjunctionCriterion={conjunctionCriterion}
					onChange={handleDateFilterChange}
				/>
			</Form.Group>
		</div>
	);
}
