/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function concatPageSizePagination(
	clearOnFirstPage,
	keyArgs = false
) {
	return {
		keyArgs,
		merge(existing = [], incoming, {variables}) {
			const merged = [...existing];

			if (variables) {
				const {page = 1, pageSize = 20} = variables;
				const offset = (page - 1) * pageSize;

				if (!offset && clearOnFirstPage) {
					return [...incoming];
				}

				for (let i = 0; i < incoming.length; ++i) {
					merged[offset + i] = incoming[i];
				}

				return merged;
			}

			return merged.concat(incoming);
		},
	};
}
