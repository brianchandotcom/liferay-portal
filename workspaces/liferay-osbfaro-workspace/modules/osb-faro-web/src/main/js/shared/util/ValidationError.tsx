/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

class ValidationError extends Error {
	field: string;

	constructor(field: string, localizedMessage: string) {
		super(localizedMessage);

		this.field = field;
	}
}

export default ValidationError;
