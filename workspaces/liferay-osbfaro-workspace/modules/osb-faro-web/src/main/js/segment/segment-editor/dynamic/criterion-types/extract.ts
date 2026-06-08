/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {RemoteCriterionType} from './RemoteCriterionType';
import {getRemoteCriterionTypeByOperator} from './registry';

export interface ExtractedRemoteCriterionEntry {
	criterionType: RemoteCriterionType;
	id: string;
	name: string;
}

/**
 * Walks a criteria object recursively and returns the entries that match
 * registered remote criterion types (vocabulary, tag, …). Each entry pairs
 * the matching `RemoteCriterionType` with the id and human-readable name
 * pulled from the criterion's inner items.
 */
export const extractRemoteCriterionEntries =
	function extractRemoteCriterionEntries(
		criteria: any
	): ExtractedRemoteCriterionEntry[] {
		if (!criteria) {
			return [];
		}

		if (criteria.items) {
			return criteria.items.flatMap(extractRemoteCriterionEntries);
		}

		const criterionType = getRemoteCriterionTypeByOperator(
			criteria.operatorName
		);

		if (!criterionType || !criteria.propertyName) {
			return [];
		}

		const id = criteria.propertyName as string;
		const items = criteria.value?.getIn?.(['criterionGroup', 'items']);
		const nameItem = items?.find?.(
			(item: any) =>
				item.get?.('propertyName') === criterionType.nameProperty
		);
		const name = (nameItem?.get?.('value') as string) ?? id;

		return [{criterionType, id, name}];
	};
