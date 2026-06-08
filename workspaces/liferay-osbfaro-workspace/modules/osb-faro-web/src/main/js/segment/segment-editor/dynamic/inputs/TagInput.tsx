/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Icon, Option, Picker} from '@clayui/core';
import getCN from 'classnames';
import React, {useEffect, useState} from 'react';
import Input from '~/shared/components/Input';
import Form from '~/shared/components/form';
import {CustomValue} from '~/shared/util/records';

import {
	ASSET_TYPE_COMPATIBLE_EVENTS_MAP,
	RelationalOperators,
} from '../utils/constants';
import {getIndexFromPropertyName} from '../utils/custom-inputs';
import {Criterion, ISegmentEditorCustomInputBase} from '../utils/types';
import {isValid} from '../utils/utils';
import DateFilterConjunctionInput from './components/DateFilterConjunctionInput';
import {
	ASSET_TYPE_OPTIONS,
	DEFAULT_CONJUNCTION_CRITERION,
	EVENT_TYPE_OPTIONS,
	OCCURRENCE_OPTIONS,
	buildRemoteFilterValue,
	getAssetTypeFromValue,
	getConjunctionCriterionFromValue,
	getEventTypeFromValue,
	isValidOccurrenceCount,
} from './shared/remote-filter-input-helpers';

export {
	ASSET_TYPE_OPTIONS,
	EVENT_TYPE_OPTIONS,
	getAssetTypeFromValue,
	getConjunctionCriterionFromValue,
	getEventTypeFromValue,
	OCCURRENCE_OPTIONS,
};

const TAG_CONFIG = {
	idProperty: 'tags/id',
	nameProperty: 'tags/name',
	supportsCategories: false,
};

export function buildValue(
	eventType: React.Key,
	assetType: React.Key,
	occurrenceOperator: React.Key,
	occurrenceCount: number | string,
	conjunctionCriterion: Criterion & {touched: boolean; valid: boolean},
	tagId: string,
	tagName: string
): CustomValue {
	return buildRemoteFilterValue(
		{
			assetType,
			conjunctionCriterion,
			entityId: tagId,
			entityName: tagName,
			eventType,
			occurrenceCount,
			occurrenceOperator,
		},
		TAG_CONFIG
	);
}

export default function TagInput({
	displayValue,
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

	useEffect(() => {
		if (!value || getIndexFromPropertyName(value, 'tags/id') < 0) {
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
					displayValue ?? ''
				),
			});
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const isOccurrenceValid = (
		operator: React.Key,
		count: number | string
	): boolean => isValidOccurrenceCount(count);

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
				displayValue ?? ''
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
									displayValue ?? ''
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
					{Liferay.Language.get('on-the-tag').toLowerCase()}
				</Form.GroupItem>

				<Form.GroupItem className="display-value" label shrink>
					<b>{displayValue}</b>
				</Form.GroupItem>
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
									displayValue ?? ''
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
									displayValue ?? ''
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
									displayValue ?? ''
								),
							});
						}}
						onChange={({
							target: {value: inputVal},
						}: React.ChangeEvent<HTMLInputElement>) => {
							let numberVal: string | number = '';

							if (isValid(inputVal)) {
								numberVal = parseInt(inputVal, 10);
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
									displayValue ?? ''
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
