/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function invariant(condition, format, ...args) {
	if (!condition) {
		let error;

		if (format === undefined) {
			error = new Error('Invariant Violation');
		}
		else {
			let argIndex = 0;

			error = new Error(format.replace(/%s/g, () => args[argIndex++]));
			error.name = 'Invariant Violation';
		}

		error.framesToPop = 1;

		throw error;
	}
}

module.exports = {invariant};
