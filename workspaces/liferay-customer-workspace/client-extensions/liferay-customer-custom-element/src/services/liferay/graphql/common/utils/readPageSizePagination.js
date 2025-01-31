/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function readPageSizePagination() {
	return {
		read(existing, {variables}) {
			const {page = 1, pageSize = 20} = variables;

			const offset = (page - 1) * pageSize;
			const limit = page * pageSize;

			return existing && existing.slice(offset, limit);
		},
	};
}
