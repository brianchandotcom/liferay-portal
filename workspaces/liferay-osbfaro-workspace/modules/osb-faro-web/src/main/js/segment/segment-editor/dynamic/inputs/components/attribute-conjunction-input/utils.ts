/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isNumber} from 'lodash';
import {DataTypes, Operators} from '~/event-analysis/utils/types';
import {BOOLEAN_LABELS_MAP} from '~/event-analysis/utils/utils';
import {DateRange} from '~/shared/components/DateRangeInput';

import {
	FunctionalOperators,
	NotOperators,
	RelationalOperators,
	STRING_OPTIONS,
} from '../../../utils/constants';
import {isValid} from '../../../utils/utils';
import {BetweenNumber} from '../BetweenNumberInput';

const ATTRIBUTES_DATE_AND_DURATION_OPERATORS_LONGHAND_LABELS_MAP = {
	[Operators.EQ]: Liferay.Language.get('is').toLowerCase(),
	[Operators.GT]: Liferay.Language.get('is-after').toLowerCase(),
	[Operators.LT]: Liferay.Language.get('is-before').toLowerCase(),
};

const ATTRIBUTES_DATE_AND_DURATION_OPTIONS = [
	Operators.LT,
	Operators.EQ,
	Operators.GT,
];

export const ATTRIBUTES_NUMBER_OPERATOR_LONGHAND_LABELS_MAP = {
	[Operators.EQ]: Liferay.Language.get('is-equal-to').toLowerCase(),
	[Operators.GT]: Liferay.Language.get('greater-than').toLowerCase(),
	[Operators.LT]: Liferay.Language.get('less-than').toLowerCase(),
	[Operators.NE]: Liferay.Language.get('is-not-equal-to').toLowerCase(),
};

const ATTRIBUTE_NUMBER_OPTIONS = [
	Operators.EQ,
	Operators.GT,
	Operators.LT,
	Operators.NE,
];

const ATTRIBUTES_STRING_OPERATOR_LABELS_MAP = {
	[FunctionalOperators.Contains]:
		Liferay.Language.get('contains').toLowerCase(),
	[NotOperators.NotContains]:
		Liferay.Language.get('does-not-contain').toLowerCase(),
	[RelationalOperators.EQ]: Liferay.Language.get('is').toLowerCase(),
	[RelationalOperators.NE]: Liferay.Language.get('is-not').toLowerCase(),
};

export const createOption = function createOption(
	option: string,
	dataType: DataTypes
) {
	const LABELS_MAP: Record<string, Record<string, string>> = {
		[DataTypes.Boolean]: BOOLEAN_LABELS_MAP,
		[DataTypes.Date]:
			ATTRIBUTES_DATE_AND_DURATION_OPERATORS_LONGHAND_LABELS_MAP,
		[DataTypes.Duration]:
			ATTRIBUTES_DATE_AND_DURATION_OPERATORS_LONGHAND_LABELS_MAP,
		[DataTypes.Number]: ATTRIBUTES_NUMBER_OPERATOR_LONGHAND_LABELS_MAP,
		[DataTypes.String]: ATTRIBUTES_STRING_OPERATOR_LABELS_MAP, // "NotContains" differs from segment-editor and event-analysis. We should be able to use the evente-analysis version once we move away from odata.
	};

	return {
		label: LABELS_MAP[dataType][option],
		value: option,
	};
};

export const getOperatorOptions = function getOperatorOptions(
	dataType: DataTypes
) {
	const OPERATOR_OPTIONS: Record<string, string[]> = {
		[DataTypes.Date]: ATTRIBUTES_DATE_AND_DURATION_OPTIONS,
		[DataTypes.Duration]: ATTRIBUTES_DATE_AND_DURATION_OPTIONS,
		[DataTypes.Number]: ATTRIBUTE_NUMBER_OPTIONS,
		[DataTypes.String]: STRING_OPTIONS, // STRING_OPTIONS is provided from the segment-editor utils as "NotContains" differs from segment-editor and event-analysis. We should be able to use the evente-analysis version once we move away from odata.
	};

	return OPERATOR_OPTIONS[dataType]?.map((option: string) =>
		createOption(option, dataType)
	);
};

export const getDefaultAttributeOperator = function getDefaultAttributeOperator(
	dataType: DataTypes
): RelationalOperators | FunctionalOperators {
	switch (dataType) {
		case DataTypes.Boolean:
		case DataTypes.Date:
			return RelationalOperators.EQ;
		case DataTypes.Duration:
		case DataTypes.Number:
			return RelationalOperators.GT;
		case DataTypes.String:
		default:
			return FunctionalOperators.Contains;
	}
};

export const getDefaultAttributeValue = function getDefaultAttributeValue(
	dataType: DataTypes,
	operatorName: RelationalOperators | FunctionalOperators
): string | {end: number | string; start: number | string} {
	if (
		operatorName === FunctionalOperators.Between &&
		[DataTypes.Number, DataTypes.Date].filter((type) => type === dataType)
	) {
		return {end: '', start: ''};
	}

	switch (dataType) {
		case DataTypes.Boolean:
			return 'true';
		case DataTypes.Date:
		case DataTypes.Number:
		case DataTypes.Duration:
		case DataTypes.String:
		default:
			return '';
	}
};

export const validateAttributeValue = function validateAttributeValue(
	value: string | number | BetweenNumber | DateRange,
	dataType: DataTypes,
	operatorName?: FunctionalOperators | RelationalOperators
): boolean {
	if (
		operatorName === FunctionalOperators.Between &&
		[DataTypes.Number, DataTypes.Date].filter((type) => type === dataType)
	) {
		const {end, start} = value as BetweenNumber;

		return isValid(end) && isValid(start);
	}

	switch (dataType) {
		case DataTypes.Boolean:
			return value === 'true' || value === 'false';
		case DataTypes.Duration:
		case DataTypes.Number:
			return isNumber(value);
		case DataTypes.Date:
		case DataTypes.String:
		default:
			return isValid(value);
	}
};
