/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ListTypeDefinition from '../interfaces/listTypeDefinition';
export interface EntryField {
	[key: string]: React.OptionHTMLAttributes<HTMLOptionElement>[];
}

export default function getEntriesByListTypeDefinitions(
	items: ListTypeDefinition[] = []
) {
	return items.reduce<EntryField>((previousValue, currentValue) => {
		return {
			...previousValue,
			[currentValue.name]: currentValue.listTypeEntries.map(
				(listTypeEntry) => ({
					label: listTypeEntry.name,
					value: listTypeEntry.key,
				})
			),
		};
	}, {});
}
