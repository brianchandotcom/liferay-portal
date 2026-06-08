/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {RemoteCriterionType} from './RemoteCriterionType';
import {tagCriterionType} from './tagCriterionType';
import {vocabularyCriterionType} from './vocabularyCriterionType';

export const REMOTE_CRITERION_TYPES: ReadonlyArray<RemoteCriterionType> = [
	vocabularyCriterionType,
	tagCriterionType,
];

const BY_PROPERTY_KEY: ReadonlyMap<string, RemoteCriterionType> = new Map(
	REMOTE_CRITERION_TYPES.map((ct) => [ct.propertyKey, ct])
);

const BY_OPERATOR: ReadonlyMap<string, RemoteCriterionType> = new Map(
	REMOTE_CRITERION_TYPES.flatMap((ct) =>
		Array.from(ct.operators, (op) => [op as string, ct] as const)
	)
);

export const getRemoteCriterionTypeByPropertyKey =
	function getRemoteCriterionTypeByPropertyKey(
		propertyKey: string | null | undefined
	): RemoteCriterionType | undefined {
		return propertyKey ? BY_PROPERTY_KEY.get(propertyKey) : undefined;
	};

export const getRemoteCriterionTypeByOperator =
	function getRemoteCriterionTypeByOperator(
		operatorName: string | null | undefined
	): RemoteCriterionType | undefined {
		return operatorName ? BY_OPERATOR.get(operatorName) : undefined;
	};

export const isRemoteCriterionOperator = function isRemoteCriterionOperator(
	operatorName: string | null | undefined
): boolean {
	return !!operatorName && BY_OPERATOR.has(operatorName);
};
