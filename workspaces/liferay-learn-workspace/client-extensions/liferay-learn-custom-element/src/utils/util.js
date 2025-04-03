/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getPersonasLabel(personas) {
	if (!personas || !personas.length) {
		return '';
	}

	return personas.length === 1
		? personas[0].name
		: personas[0].name + ', +' + (personas.length - 1);
}

export function getPersonasTooltip(personas) {
	return personas.map(({name}) => name).join(', ');
}

export function truncateText(text, length = 150) {
	return text.length > length
		? text.substring(0, length).concat('...')
		: text;
}
