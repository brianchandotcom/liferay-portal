/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

function pedantic(message) {
	throw message instanceof Error ? message : new Error(message);
}

/* eslint-disable no-console */

const error = console.error;
const warn = console.warn;

export function enable() {
	console.error = pedantic;
	console.warn = pedantic;
}

export function disable() {
	console.error = error;
	console.warn = warn;
}
