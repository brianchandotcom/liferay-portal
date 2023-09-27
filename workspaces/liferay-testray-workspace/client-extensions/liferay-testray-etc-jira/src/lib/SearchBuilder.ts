/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type Key = string;
type Value = string | number | boolean;

export interface SearchBuilderConstructor {
	useURIEncode?: boolean;
}

/**
 * @description
 * Based in the following article https://help.liferay.com/hc/pt/articles/360031163631-Filter-Sort-and-Search
 */

export default class SearchBuilder {
	static eq(key: Key, value: Value) {
		return `${key} eq ${typeof value === 'boolean' ? value : `'${value}'`}`;
	}

	static in(key: Key, values: Value[]) {
		if (values) {
			const operator = `${key} in ({values})`;

			return operator
				.replace(
					'{values}',
					values.map((value) => `'${value}'`).join(',')
				)
				.trim();
		}

		return '';
	}
}
